// SPDX-License-Identifier: MIT

pragma solidity >=0.4.22 <0.7.0;

//import "https://github.com/OpenZeppelin/openzeppelin-contracts/blob/release-v3.0.0/contracts/cryptography/ECDSA.sol";
import "../ECDSA.sol";

contract Battleship {
    using ECDSA for bytes32;
    uint32 constant BOARD_LEN = 6;
    uint TIME_TO_RES = 60;

    // Declare state variables here.
    // Consider keeping state for:

    // - game states
    enum GameStates { RUNNING, FINISHED }
    GameStates game_state = GameStates.FINISHED;

    // - player addresses
    address player_1_address;
    address player_2_address;

    // - player bids
    uint player_1_bid;
    uint player_2_bid;
    bool first_bid = true;

    // - player boards
    bytes32 player_1_board;
    bytes32 player_2_board;

    // - player timers
    bool player_1_afk = false;
    bool player_1_timer_set = false;
    uint player_1_timer_start = 0;
    uint player_1_timer_end = 0;

    bool player_2_afk = false;
    bool player_2_timer_set = false;
    uint player_2_timer_start = 0;
    uint player_2_timer_end = 0;

    // - whether a player has proven 10 winning moves
    uint player_1_hits;
    uint[] player_1_destroyed_boats;

    uint player_2_hits;
    uint[] player_2_destroyed_boats; 

    // - whether a player has proven their own board had 10 ships

    // Declare events here.
    // Consider triggering an event upon accusing another player of having left.
    event AFK(
        address sender,
        address oponent,
        uint timer_start
    );

    // Store the bids of each player
    // Start the game when both bids are received
    // The first player to call the function determines the bid amount.
    // Refund excess bids to the second player if they bid too much.
    function store_bid() public payable {
        address sender_address = msg.sender;
        uint bid_value = msg.value;

        if (bid_value == 0) return;

        // First bid
        if (first_bid) {
            first_bid = false;
            player_1_address = sender_address;
            player_1_bid = bid_value;
            return;
        }
        
        // Second bid
        if (sender_address == player_1_address) return;
        if (bid_value < player_1_bid) return;
        if (bid_value > player_1_bid) {
            uint bid_refund = (bid_value - player_1_bid);
            payable (sender_address).transfer(bid_refund);
            bid_value = player_1_bid;
        }

        player_2_address = sender_address;
        player_2_bid = bid_value; 
        game_state = GameStates.RUNNING;
        return;
    }

    // Clear state - make sure to set that the game is not in session
    function clear_state() internal {
        player_1_address = address(0);
        player_2_address = address(0);

        player_1_bid = 0; 
        player_2_bid = 0;
        first_bid = true;

        player_1_board = "";
        player_2_board = "";

        player_1_afk = false;
        player_1_timer_set = false;
        player_1_timer_start = 0;
        player_1_timer_end = 0;

        player_2_afk = false;
        player_2_timer_set = false;
        player_2_timer_start = 0;
        player_2_timer_end = 0;

        player_1_hits = 0;
        delete player_1_destroyed_boats;

        player_2_hits = 0;
        delete player_2_destroyed_boats; 

        game_state = GameStates.FINISHED;
    }

    // Store the initial board commitments of each player
    // Note that merkle_root is the hash of the topmost value of the merkle tree
    function store_board_commitment(bytes32 merkle_root) public {
        address sender_address = msg.sender;

        if (!address_is_valid(sender_address)) return;
        
        if (sender_address == player_1_address) {
            player_1_board = merkle_root;
            return;
        }
        if (sender_address == player_2_address) {
            player_2_board = merkle_root;
            return;
        }
    }

    // Verify the placement of one ship on a board
    // opening_nonce - corresponds to web3.utils.fromAscii(JSON.stringify(opening) + JSON.stringify(nonce)) in JS
    // proof - a list of sha256 hashes you can get from get_proof_for_board_guess
    // guess_leaf_index - the index of the guess as a leaf in the merkle tree
    // owner - the address of the owner of the board on which this ship lives
    function check_one_ship(bytes memory opening_nonce, bytes32[] memory proof,
        uint256 guess_leaf_index, address owner) public returns (bool result) {
        address sender_address = msg.sender;
        bool cheater;

        if (!address_is_valid(sender_address)) return false;
        if (!address_is_valid(owner)) return false;
        //if (sender_address == owner) return false;

        if (owner == player_1_address) cheater = !verify_opening(opening_nonce, proof, guess_leaf_index, player_1_board);
        if (owner == player_2_address) cheater = !verify_opening(opening_nonce, proof, guess_leaf_index, player_2_board);

        bool hit = is_equal_to_true(opening_nonce);

        // player 1 guess
        if (owner == player_2_address && hit && !cheater) {
            if (sender_address == owner) return true;
            if (!contains_boat(player_2_destroyed_boats, guess_leaf_index)) {
                player_2_destroyed_boats.push(guess_leaf_index);
                player_1_hits++;
            }
            return true;
        }

        // player 2 guess
        if (owner == player_1_address && hit && !cheater) {
            if (sender_address == owner) return true;
            if (!contains_boat(player_1_destroyed_boats, guess_leaf_index)) {
                player_1_destroyed_boats.push(guess_leaf_index);
                player_2_hits++;
            }
            return true;
        }
        return false;
    }

    // Claim you won the game
    // If you have checked 10 winning moves (hits) AND you have checked
    // 10 of your own ship placements with the contract, then this function
    // should transfer winning funds to you and end the game.
    function claim_win() public {
        address sender_address = msg.sender;
        uint win_price = player_1_bid + player_2_bid;

        if (!address_is_valid(sender_address)) return;

        if (sender_address == player_1_address) {
            if (player_1_hits != 10) return;
            if (player_2_destroyed_boats.length != 10) return;
        }
        if (sender_address == player_2_address) {
            if (player_2_hits != 10) return;
            if (player_1_destroyed_boats.length != 10) return;
        }

        payable (sender_address).transfer(win_price);
        player_1_bid = 0; player_2_bid = 0;
        game_state = GameStates.FINISHED;
        clear_state();
        return;
    }

    // Forfeit the game
    // Regardless of cheating, board state, or any other conditions, this function
    // results in all funds being sent to the opponent and the game being over.
    function forfeit(address payable opponent) public {
        address sender_address = msg.sender;
        uint win_price = player_1_bid + player_2_bid;

        if (!address_is_valid(sender_address)) return;
        if (!address_is_valid(opponent)) return;
        if (sender_address == opponent) return;
        
        opponent.transfer(win_price);
        player_1_bid = 0; player_2_bid = 0;
        game_state = GameStates.FINISHED;
        clear_state();    
    }

    // Claim the opponent cheated - if true, you win.
    // opening_nonce - corresponds to web3.utils.fromAscii(JSON.stringify(opening) + JSON.stringify(nonce)) in JS
    // proof - a list of sha256 hashes you can get from get_proof_for_board_guess (this is what the sender believes to be a lie)
    // guess_leaf_index - the index of the guess as a leaf in the merkle tree
    // owner - the address of the owner of the board on which this ship lives
    function accuse_cheating(bytes memory opening_nonce, bytes32[] memory proof,
        uint256 guess_leaf_index, address owner) public returns (bool result) {
        address sender_address = msg.sender;
        bool cheater;

        if (!address_is_valid(sender_address)) return false;
        if (!address_is_valid(owner)) return false;
        if (sender_address == owner) return false;

        if (owner == player_1_address) cheater = !verify_opening(opening_nonce, proof, guess_leaf_index, player_1_board);
        if (owner == player_2_address) cheater = !verify_opening(opening_nonce, proof, guess_leaf_index, player_2_board);

        if (!cheater) return false;
        
        uint win_price = player_1_bid + player_2_bid;
        payable (sender_address).transfer(win_price);
        player_1_bid = 0; player_2_bid = 0;
        game_state = GameStates.FINISHED;
        clear_state();
        return true;
    }

    // Claim the opponent of taking too long/leaving
    // Trigger an event that both players should listen for.
    function claim_opponent_left(address opponent) public {
        address sender_address = msg.sender;

        if (!address_is_valid(sender_address)) return;
        if (!address_is_valid(opponent)) return;
        if (sender_address == opponent) return;
        start_timer(opponent);
        emit AFK(sender_address, opponent, now);
    }

    // Handle a timeout accusation - msg.sender is the accused party.
    // If less than 1 minute has passed, then set state appropriately to prevent distribution of winnings.
    // Otherwise, do nothing.
    function handle_timeout(address payable opponent) public {
        address sender_address = msg.sender;

        if (!address_is_valid(sender_address)) return;
        if (!address_is_valid(opponent)) return;
        if (sender_address == opponent) return;
        if (!is_timer_set(sender_address)) return;

        if (response_in_time(sender_address)) {
            stop_timer(sender_address);
            return;
        }

        stop_timer(sender_address);
        if (sender_address == player_1_address) player_1_afk = true;
        if (sender_address == player_2_address) player_2_afk = true;
    }

    // Claim winnings if opponent took too long/stopped responding after claim_opponent_left
    // The player MUST claim winnings. The opponent failing to handle the timeout on their end should not
    // result in the game being over. If the timer has not run out, do nothing.
    function claim_timeout_winnings(address opponent) public {
        address sender_address = msg.sender;
        uint win_price = player_1_bid + player_2_bid;

        if (!address_is_valid(sender_address)) return;
        if (!address_is_valid(opponent)) return;
        if (sender_address == opponent) return;
        
        if (!is_timer_set(opponent) && !is_afk(opponent)) return;
        if (is_timer_set(opponent) && response_in_time(opponent)) return;

        payable (sender_address).transfer(win_price);
        player_1_bid = 0; player_2_bid = 0;
        game_state = GameStates.FINISHED;
        clear_state();
    }

    // Check if game is over
    // Hint - use a state variable for this, so you can call it from JS.
    // Note - you cannot use the return values of functions that change state in JS.
    function is_game_over() public view returns (bool) {
        if (game_state == GameStates.FINISHED) return true;
        return false;
    }

    /**** Helper Functions below this point. Do not modify. ****/
    /***********************************************************/
    function get_player_hits(address player_address) public view returns (uint) {
        if (player_address == player_1_address) return player_1_hits;
        if (player_address == player_2_address) return player_2_hits;
    }

    function contains_boat(uint[] memory boats, uint index) public pure returns (bool) {
        for (uint i=0; i<boats.length; i++) if (boats[i] == index) return true;
        return false;
    }

    function is_equal_to_true(bytes memory str_bytes) public pure returns (bool){
        bytes memory sliced = new bytes(4);
        for (uint i=0; i<4; i++) sliced[i] = str_bytes[i];
        return (compareStrings("true", string(sliced)));
    }

    function compareStrings(string memory a, string memory b) public pure returns (bool) {
        return (keccak256(abi.encodePacked((a))) == keccak256(abi.encodePacked((b))));
    }

    function is_afk(address player_address) public view returns (bool) {
        if (player_address == player_1_address) return player_1_afk;
        if (player_address == player_2_address) return player_2_afk;
        return false;
    }

    function response_in_time(address player_address) public view returns (bool) {
        uint res_time = 0;

        if (player_address == player_1_address) res_time = player_1_timer_end - now;
        if (player_address == player_2_address) res_time = player_2_timer_end - now;
        if (res_time > 0 && res_time < TIME_TO_RES) return true;
        return false;
    }

    function is_timer_set(address player_address) public view returns (bool) {
        if (!address_is_valid(player_address)) return false;
        if (player_address == player_1_address) return player_1_timer_set;
        if (player_address == player_2_address) return player_2_timer_set;
        return false;
    }

    function start_timer(address player_address) public {
        if (player_address == player_1_address) {
            player_1_timer_set = true;
            player_1_timer_start = now;
            player_1_timer_end = now + TIME_TO_RES;
            return;
        }
        if (player_address == player_2_address) {
            player_2_timer_set = true;
            player_2_timer_start = now;
            player_2_timer_end = now + TIME_TO_RES;
            return;
        }
    }

    function stop_timer(address player_address) public {
        if (player_address == player_1_address) {
            player_1_timer_set = false;
            player_1_timer_start = 0;
            player_1_timer_end = 0;
            return;
        }
        if (player_address == player_2_address) {
            player_2_timer_set = false;
            player_2_timer_start = 0;
            player_2_timer_end = 0;
            return;
        }
    }

    function address_is_valid(address sender_address) public view returns (bool) {
        if (sender_address == player_1_address) return true;
        if (sender_address == player_2_address) return true;
        return false;
    }

    function merge_bytes32(bytes32 a, bytes32 b) pure public returns (bytes memory) {
        bytes memory result = new bytes(64);
        assembly {
            mstore(add(result, 32), a)
            mstore(add(result, 64), b)
        }
        return result;
    }

    // Verify the proof of a single spot on a single board
    // \args:
    //      opening_nonce - corresponds to web3.utils.fromAscii(JSON.stringify(opening) + JSON.stringify(nonce)));
    //      proof - list of sha256 hashes that correspond to output from get_proof_for_board_guess()
    //      guess - [i, j] - guess that opening corresponds to
    //      commit - merkle root of the board
    function verify_opening(bytes memory opening_nonce, bytes32[] memory proof, uint guess_leaf_index, bytes32 commit) public view returns (bool result) {
        bytes32 curr_commit = keccak256(opening_nonce); // see if this changes hash
        uint index_in_leaves = guess_leaf_index;

        uint curr_proof_index = 0;
        uint i = 0;

        while (curr_proof_index < proof.length) {
            // index of which group the guess is in for the current level of Merkle tree
            // (equivalent to index of parent in next level of Merkle tree)
            uint group_in_level_of_merkle = index_in_leaves / (2**i);
            // index in Merkle group in (0, 1)
            uint index_in_group = group_in_level_of_merkle % 2;
            // max node index for currrent Merkle level
            uint max_node_index = ((BOARD_LEN * BOARD_LEN + (2**i) - 1) / (2**i)) - 1;
            // index of sibling of curr_commit
            uint sibling = group_in_level_of_merkle - index_in_group + (index_in_group + 1) % 2;
            i++;
            if (sibling > max_node_index) continue;
            if (index_in_group % 2 == 0) {
                curr_commit = keccak256(merge_bytes32(curr_commit, proof[curr_proof_index]));
                curr_proof_index++;
            } else {
                curr_commit = keccak256(merge_bytes32(proof[curr_proof_index], curr_commit));
                curr_proof_index++;
            }
        }
        return (curr_commit == commit);

    }
}