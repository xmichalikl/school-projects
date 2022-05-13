// BattleshipPlayer.js
//    Defines a Battleship player and the functions that the player calls to
//    interact with the solidity contract
// =============================================================================
//                                EDIT THIS FILE
// =============================================================================
//      written by: Lukáš Michalík
// #############################################################################

// sets up web3.js
let web3 = new Web3(Web3.givenProvider || "ws://localhost:7545");

// This is the ABI for your contract (get it from Truffle, from 'bin' folder, or from Remix, in the 'Compile' tab )
// ============================================================
var abi = [
	{
		"inputs": [
			{
				"internalType": "bytes",
				"name": "opening_nonce",
				"type": "bytes"
			},
			{
				"internalType": "bytes32[]",
				"name": "proof",
				"type": "bytes32[]"
			},
			{
				"internalType": "uint256",
				"name": "guess_leaf_index",
				"type": "uint256"
			},
			{
				"internalType": "address",
				"name": "owner",
				"type": "address"
			}
		],
		"name": "accuse_cheating",
		"outputs": [
			{
				"internalType": "bool",
				"name": "result",
				"type": "bool"
			}
		],
		"stateMutability": "nonpayable",
		"type": "function"
	},
	{
		"anonymous": false,
		"inputs": [
			{
				"indexed": false,
				"internalType": "address",
				"name": "sender",
				"type": "address"
			},
			{
				"indexed": false,
				"internalType": "address",
				"name": "oponent",
				"type": "address"
			},
			{
				"indexed": false,
				"internalType": "uint256",
				"name": "timer_start",
				"type": "uint256"
			}
		],
		"name": "AFK",
		"type": "event"
	},
	{
		"inputs": [
			{
				"internalType": "address",
				"name": "opponent",
				"type": "address"
			}
		],
		"name": "claim_opponent_left",
		"outputs": [],
		"stateMutability": "nonpayable",
		"type": "function"
	},
	{
		"inputs": [
			{
				"internalType": "address",
				"name": "opponent",
				"type": "address"
			}
		],
		"name": "claim_timeout_winnings",
		"outputs": [],
		"stateMutability": "nonpayable",
		"type": "function"
	},
	{
		"inputs": [],
		"name": "claim_win",
		"outputs": [],
		"stateMutability": "nonpayable",
		"type": "function"
	},
	{
		"inputs": [
			{
				"internalType": "address payable",
				"name": "opponent",
				"type": "address"
			}
		],
		"name": "forfeit",
		"outputs": [],
		"stateMutability": "nonpayable",
		"type": "function"
	},
	{
		"inputs": [
			{
				"internalType": "address payable",
				"name": "opponent",
				"type": "address"
			}
		],
		"name": "handle_timeout",
		"outputs": [],
		"stateMutability": "nonpayable",
		"type": "function"
	},
	{
		"inputs": [
			{
				"internalType": "bytes",
				"name": "opening_nonce",
				"type": "bytes"
			},
			{
				"internalType": "bytes32[]",
				"name": "proof",
				"type": "bytes32[]"
			},
			{
				"internalType": "uint256",
				"name": "guess_leaf_index",
				"type": "uint256"
			},
			{
				"internalType": "address",
				"name": "owner",
				"type": "address"
			}
		],
		"name": "check_one_ship",
		"outputs": [
			{
				"internalType": "bool",
				"name": "result",
				"type": "bool"
			}
		],
		"stateMutability": "nonpayable",
		"type": "function"
	},
	{
		"inputs": [],
		"name": "is_game_over",
		"outputs": [
			{
				"internalType": "bool",
				"name": "",
				"type": "bool"
			}
		],
		"stateMutability": "nonpayable",
		"type": "function"
	},
	{
		"inputs": [
			{
				"internalType": "address",
				"name": "player_address",
				"type": "address"
			}
		],
		"name": "start_timer",
		"outputs": [],
		"stateMutability": "nonpayable",
		"type": "function"
	},
	{
		"inputs": [
			{
				"internalType": "address",
				"name": "player_address",
				"type": "address"
			}
		],
		"name": "stop_timer",
		"outputs": [],
		"stateMutability": "nonpayable",
		"type": "function"
	},
	{
		"inputs": [],
		"name": "store_bid",
		"outputs": [],
		"stateMutability": "payable",
		"type": "function"
	},
	{
		"inputs": [
			{
				"internalType": "bytes32",
				"name": "merkle_root",
				"type": "bytes32"
			}
		],
		"name": "store_board_commitment",
		"outputs": [],
		"stateMutability": "nonpayable",
		"type": "function"
	},
	{
		"inputs": [
			{
				"internalType": "address",
				"name": "sender_address",
				"type": "address"
			}
		],
		"name": "address_is_valid",
		"outputs": [
			{
				"internalType": "bool",
				"name": "",
				"type": "bool"
			}
		],
		"stateMutability": "view",
		"type": "function"
	},
	{
		"inputs": [
			{
				"internalType": "string",
				"name": "a",
				"type": "string"
			},
			{
				"internalType": "string",
				"name": "b",
				"type": "string"
			}
		],
		"name": "compareStrings",
		"outputs": [
			{
				"internalType": "bool",
				"name": "",
				"type": "bool"
			}
		],
		"stateMutability": "pure",
		"type": "function"
	},
	{
		"inputs": [
			{
				"internalType": "uint256[]",
				"name": "boats",
				"type": "uint256[]"
			},
			{
				"internalType": "uint256",
				"name": "index",
				"type": "uint256"
			}
		],
		"name": "contains_boat",
		"outputs": [
			{
				"internalType": "bool",
				"name": "",
				"type": "bool"
			}
		],
		"stateMutability": "pure",
		"type": "function"
	},
	{
		"inputs": [
			{
				"internalType": "address",
				"name": "player_address",
				"type": "address"
			}
		],
		"name": "get_player_hits",
		"outputs": [
			{
				"internalType": "uint256",
				"name": "",
				"type": "uint256"
			}
		],
		"stateMutability": "view",
		"type": "function"
	},
	{
		"inputs": [
			{
				"internalType": "address",
				"name": "player_address",
				"type": "address"
			}
		],
		"name": "is_afk",
		"outputs": [
			{
				"internalType": "bool",
				"name": "",
				"type": "bool"
			}
		],
		"stateMutability": "view",
		"type": "function"
	},
	{
		"inputs": [
			{
				"internalType": "bytes",
				"name": "str_bytes",
				"type": "bytes"
			}
		],
		"name": "is_equal_to_true",
		"outputs": [
			{
				"internalType": "bool",
				"name": "",
				"type": "bool"
			}
		],
		"stateMutability": "pure",
		"type": "function"
	},
	{
		"inputs": [
			{
				"internalType": "address",
				"name": "player_address",
				"type": "address"
			}
		],
		"name": "is_timer_set",
		"outputs": [
			{
				"internalType": "bool",
				"name": "",
				"type": "bool"
			}
		],
		"stateMutability": "view",
		"type": "function"
	},
	{
		"inputs": [
			{
				"internalType": "bytes32",
				"name": "a",
				"type": "bytes32"
			},
			{
				"internalType": "bytes32",
				"name": "b",
				"type": "bytes32"
			}
		],
		"name": "merge_bytes32",
		"outputs": [
			{
				"internalType": "bytes",
				"name": "",
				"type": "bytes"
			}
		],
		"stateMutability": "pure",
		"type": "function"
	},
	{
		"inputs": [
			{
				"internalType": "address",
				"name": "player_address",
				"type": "address"
			}
		],
		"name": "response_in_time",
		"outputs": [
			{
				"internalType": "bool",
				"name": "",
				"type": "bool"
			}
		],
		"stateMutability": "view",
		"type": "function"
	},
	{
		"inputs": [
			{
				"internalType": "bytes",
				"name": "opening_nonce",
				"type": "bytes"
			},
			{
				"internalType": "bytes32[]",
				"name": "proof",
				"type": "bytes32[]"
			},
			{
				"internalType": "uint256",
				"name": "guess_leaf_index",
				"type": "uint256"
			},
			{
				"internalType": "bytes32",
				"name": "commit",
				"type": "bytes32"
			}
		],
		"name": "verify_opening",
		"outputs": [
			{
				"internalType": "bool",
				"name": "result",
				"type": "bool"
			}
		],
		"stateMutability": "view",
		"type": "function"
	}
] // TODO: replace this with your contract's ABI
// ============================================================
abiDecoder.addABI(abi);

// This is the address of the contract you want to connect to; copy this from Remix
// TODO: fill this in with your contract's address/hash
let contractAddress = "0xefEE87236c768842fC90BddAB30d3f78462C2F17"; //"0x3Ca....";

// Reads in the ABI
var Battleship = new web3.eth.Contract(abi, contractAddress); 

// Timeout event listener
Battleship.events.AFK({}).on('data', (event) => {
	let oponent_address = event.returnValues.oponent;
	let player = players.find(pl => {
		return pl.my_addr === oponent_address;
	});

	if (player) {
		player.afk_status = true;
		let player_title = $(`#${player.my_name} .title`).text().split(':')[0];
		$(`#${player.my_name} .title`).html(`${player_title}: AFK!`).css('color', 'red');
	}
});

class BattleshipPlayer {
  /* constructor
    \brief
      constructor for both battleship players is called after the "start game" button is pressed
      both players are initialized with the following parameters
    \params:
      name          - string - either 'player1' or 'player2', for jquery only for the most part
      my_addr       - string - this player's address in hex
      opponent_addr - string - this player's opponent's address in hex for this game of battleship
      ante          - int    - amount of ether being wagered
  */
  constructor(name, my_addr, opponent_addr, ante) {
    this.my_name = name;
    this.my_addr = my_addr;
    this.opp_addr = opponent_addr;
    this.guesses = Array(BOARD_LEN).fill(Array(BOARD_LEN).fill(false));
    this.my_board = null;
    // ##############################################################################
    //    TODO initialize a battleship game on receiving solidity contract
		//		- Save anything you'd like to track locally
		//	  - Ideas: hits, opponent's last guess/response, whether timer is running, etc.
		//		- Register an event handler for any events you emit in solidity.
		//			(see the spec for an example of how to do this)
    // ##############################################################################
		// Your code here
	console.log("Not implemented");
    this.my_ships = [];
	this.my_hits = [];

	this.afk_status = false;
	this.cheater_status = false;

	this.last_proof = null;
	this.last_opening_nonce = null;
	this.last_guess_leaf_index = null;
  }

	async place_bet(ante) {
		// ##############################################################################
    //    TODO make a call to the contract to store the bid
		//	  Hint: When you send value with a contract transaction, it must be in WEI.
		//					wei = eth*10**18
    // ##############################################################################
		// Your code here
		console.log(`place_bet(${ante})`);
		let wei = ante * Math.pow(10, 18);
		await Battleship.methods.store_bid()
			.send({from: this.my_addr, value: wei, gas:3000000});
	}

  /* initialize_board
    \brief
      sets class variable my_board and creates a commitment to the board, which is returned
      and sent to the opponent
    \params:
      initialize_board - [[bool]] - array of arrays where true represents a ship's presense
      callback - callback to call with commitment as argument
  */
  async initialize_board(initial_board) {
    this.my_board = initial_board;

		// Store the positions of your ten ships locally, so you can prove it if you win
		for (var i = 0; i < BOARD_LEN; i++) {
			for (var j = 0; j < BOARD_LEN; j++) {
				if (this.my_board[i][j]) {
					this.my_ships.push([i,j]);
				}
			}
		}

    // set nonces to build our commitment with
    this.nonces = get_nonces(); // get_nonces defined in util.js
    // build commitment to our board
    const commit = build_board_commitment(this.my_board, this.nonces); // build_board_commitment defined in util.js
    // sign this commitment
    const sig = sign_msg(commit, this.my_addr);

		// ##############################################################################
    //    TODO store the board commitment in the contract
    // ##############################################################################
		// Your code here
	await Battleship.methods.store_board_commitment(commit)
		.send({from: this.my_addr, gas:3000000})
	return [commit, sig];
  }

  /* receive_initial_board_commit
    \brief
      called with the returned commitment from initialize_board() as argument
    \params:
      commitment - a commitment to an initial board state received from opponent
      signature - opponent signature on commitment
  */
  receive_initial_board_commit(commitment, signature) {
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    //    DONE this function has been completed for you.
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    if (!check_signature(commitment, signature, this.opp_addr)) {
      throw "received an invalid signature from opponent as initial board commit";
    }
    this.opponent_commit = commitment;
    this.opponent_commit_sig = signature;
  }

  /* build_guess
    \brief:
      build a guess to be sent to the opponent
    \params
      i - int - the row of the guessed board square
      j - int - the column of the guessed board square
    \return:
      signature - Promise - a signature on [i, j]
  */
  build_guess(i, j) {
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    //    DONE this function has been completed for you.
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // Sends signed guess to opponent off-chain
    return sign_msg(JSON.stringify([i, j]), this.my_addr); // sign_msg defined in util.js
  }


  /* respond_to_guess
    \brief:
      called when the opponent guesses a board square (i, j)
    \params:
      i - int - the row of the guessed board square
      j - int - the column of the guessed board square
      signature - signature that proves the opponent is guessing (i, j)
    \return:
      hit (opening)   - bool   	- did the guess hit one of your ships?
      nonce 					- bytes32 - nonce for square [i, j]
      proof 					- object 	- proof that the guess hit or missed a ship
  */
  respond_to_guess(i, j, signature) {
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    //    DONE this function has been completed for you.
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // Check first that the guess is signed, if not, we don't respond
    if (!check_signature(JSON.stringify([i, j]), signature, this.opp_addr)) { //check_signature defined in util.js
      throw "received an invalid signature from opponent as initial board commit";
    }
    // get truth value for this cell along with the associated nonce
    const opening = this.my_board[i][j], nonce = this.nonces[i][j];
    // write proof for this opening
    const proof = get_proof_for_board_guess(this.my_board, this.nonces, [i, j]);
    // return to opponent
    return [opening, nonce, proof];
  }

  /* receive_response_to_guess
    \brief:
      called with the response from respond_to_guess()
    \params:
      response - [hit, proof] - the object returned from respond_to_guess()
  */
  async receive_response_to_guess(i, j, response) {
    // unpack response
    let [opening, nonce, proof] = response;
    // verify that opponent responded to the query
    if (!verify_opening(opening, nonce, proof, this.opponent_commit, [i, j])) {
      throw "opponent's response is not an opening of the square I asked for";
    }

		// ##############################################################################
    //    TODO store local state as needed to prove your winning moves in claim_win
		//	  Hint: What arguments do you need to pass to the contract to check a ship?
    // ##############################################################################
		// Your code here
	let opening_nonce = web3.utils.fromAscii(JSON.stringify(opening) + JSON.stringify(nonce));
	let guess_leaf_index = i * BOARD_LEN + j;

	this.last_opening_nonce = opening_nonce;
    this.last_proof = proof;
    this.last_guess_leaf_index = guess_leaf_index;

	let opponent = players.find((pl) => {
		return pl.my_addr === this.opp_addr;
	});
	opponent.cheater_status = !await Battleship.methods.check_one_ship(opening_nonce, proof, guess_leaf_index, this.opp_addr)
		.send({from: this.my_addr, gas:3000000});
	if (opening) this.my_hits.push({i: i, j: j});
	
	console.log(`[i][j]: [${i}][${j}]`);
	console.log("cheater status: " + opponent.cheater_status);
	console.log("hits: " + await Battleship.methods.get_player_hits(this.my_addr).call());
  }


  async accuse_timeout() {
    // ##############################################################################
    //    TODO implement accusing the opponent of a timeout
		//	  - Called when you press "Accuse Timeout"
    // ##############################################################################
		// Your code here
	console.log("accuse_timeout()");
	await Battleship.methods.claim_opponent_left(this.opp_addr)
		.send({from: this.my_addr, gas:3000000});
  }

	async handle_timeout_accusation() {
    // ##############################################################################
    //    TODO implement addressing of a timeout accusation by the opponent
		//		- Called when you press "Respond to Accusation"
		// 		- Returns true if the game is over
    // ##############################################################################
		// Your code here
	console.log("handle_timeout_accusation()");
	if (!this.afk_status) return false;
	await Battleship.methods.handle_timeout(this.opp_addr)
		.send({from: this.my_addr, gas:3000000});
	
	if (await Battleship.methods.is_afk(this.my_addr).call()) return true;
	this.afk_status = false;
	let player_title = $(`#${this.my_name} .title`).text().split(':')[0];
	$(`#${this.my_name} .title`).html(`${player_title}:`).css('color', 'black');
	return false;
  }

	/* claim_timeout
    \brief:
      called when BattleshipPlayer believes the opponent has timed-out
      BattleshipPlayer should touch the solidity contract to enforce a
      timelimit for the move
  */
	async claim_timout_winnings() {
		// ##############################################################################
    //    TODO implement claim of rewards if opponent times out
		//		- Called when you press "Claim Timeout Winnings"
		// 		- Returns true if game is over
    // ##############################################################################
		// Your code here 
		console.log("claim_timout_winnings()");
		// check afk status !!!
		let opponent = players.find((pl) => {
			return pl.my_addr === this.opp_addr;
		});
		if (!opponent.afk_status) return false;

		await Battleship.methods.claim_timeout_winnings(this.opp_addr)
			.send({from: this.my_addr, gas:3000000});
		return await Battleship.methods.is_game_over().call();
	}

	/*
	accuse_cheating
	*/
	async accuse_cheating() {
		// ##############################################################################
    //    TODO implement claim of a cheat (the opponent lied about a guess)
		//		- Called when you press "Accuse Cheating"
		//		- This function checks if the last response from the opponent was a lie
		//		- Note that this is already checked in receive_response_to_guess
		//		- This function should verify the proof using the contract instead.
		//		- For this project, the proof should always verify (the opponent will never lie).
    // ##############################################################################
		// Your code here
		console.log("accuse_cheating()");
		/*let cheating_status = */await Battleship.methods.accuse_cheating(this.last_opening_nonce, this.last_proof, this.last_guess_leaf_index, this.opp_addr)
			.send({from: this.my_addr, gas:3000000});
		//console.log("cheating_status: " + cheating_status);

		console.log("cheating_status: " + await Battleship.methods.is_game_over().call());
		if (!await Battleship.methods.is_game_over().call()) return false;
		let opponent = players.find((pl) => {
			return pl.my_addr === this.opp_addr;
		});

		let player_title = $(`#${opponent.my_name} .title`).text().split(':')[0];
		$(`#${opponent.my_name} .title`).html(`${player_title}: CHEATER!`).css('color', 'red');
		return true;
	}

	/*
	 	\brief:
			Claim that you won the game - submit proofs to the contract to get the reward.
	*/
	async claim_win() {
		// ##############################################################################
    //    TODO implement claim of a win
		//		- Check the placements of 10 hits you have made on the opponent's board.
		//		- Check (verify with contract) that your board has 10 ships.
		//		- Claim the win to end the game.
		//		Hint: you can convert an opening and a nonce into a bytes memory like this:
		//			web3.utils.fromAscii(JSON.stringify(opening) + JSON.stringify(nonce))
    // ##############################################################################
		// Your code here
		console.log("claim_win()");
		// check win (boats, positions, hits, ...)

		if (this.my_hits.length != 10) return false;
		if (this.my_ships.length != 10) return false;
		
		// check player hits 
		let opponent = players.find((pl) => {
			return pl.my_addr === this.opp_addr;
		});
		this.my_hits.forEach((hit) => {
			if (!opponent.my_board[hit.i][hit.j]) return false;
		});

		for (const ship of this.my_ships) {
			let [i, j] = ship;
			let opening = this.my_board[i][j];
			let proof = get_proof_for_board_guess(this.my_board, this.nonces, [i, j]);
			let index = i * BOARD_LEN + j;
			let nonce = this.nonces[i][j];
			let opening_nonce = web3.utils.fromAscii(JSON.stringify(opening) + JSON.stringify(nonce));
			
			let valid_ship = await Battleship.methods.check_one_ship(opening_nonce, proof, index, this.my_addr)
				.send({from: this.my_addr, gas:3000000});
			if (!valid_ship) return false;
		}

		await Battleship.methods.claim_win()
			.send({from: this.my_addr, gas:3000000});

		const res = await Battleship.methods.is_game_over().call();
		return res;
	}

	/*
		\brief:
			Forfeit the game - sends the opponent the entire reward.
	*/
	async forfeit_game() {
		// ##############################################################################
		//    TODO forfeit the battleship game
		//		- Call solidity to give up your bid to the other player and end the game.
		// ##############################################################################
		// Your code here
		console.log("forfeit_game()");
		await Battleship.methods.forfeit(this.opp_addr)
			.send({from: this.my_addr, gas:3000000});
	}
}
