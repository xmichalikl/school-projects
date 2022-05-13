const battleship = artifacts.require("Battleship");

let player1_address = null; 
let player2_address = null;

(async function init_accounts() {
    let accounts = await web3.eth.getAccounts();
    player1_address = accounts[0];
    player2_address = accounts[1];
})();

contract('Battleship', () => {
    it('Contract successfully deployed', async () => {
        await battleship.deployed();
        assert(battleship.address !== '');
    });
    it('Default game state is set', async () => {
        const Battleship = await battleship.deployed();
        assert.equal(await Battleship.is_game_over.call()
        .then((res) => {
            return res;
        }).catch((err) => {
            return false;
        }), true);
    });
    it('Game is running', async () => {
        const Battleship = await battleship.deployed();
        await Battleship.store_bid({ from: player1_address, value: 100000 })
        await Battleship.store_bid({ from: player2_address, value: 100000 })
        assert.equal(await Battleship.is_game_over.call()
        .then((res) => {
            return res;
        }).catch((err) => {
            return true;
        }), false);
    });
    it('Timer is running', async () => {
        const Battleship = await battleship.deployed();
        await Battleship.claim_opponent_left(player1_address, {from: player2_address});
        assert.equal(await Battleship.is_timer_set.call(player1_address)
        .then((res) => {
            return res;
        }).catch((err) => {
            return false;
        }), true);
    });

});