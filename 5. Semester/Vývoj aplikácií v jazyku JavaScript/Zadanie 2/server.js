const fs = require('fs');
const path = require("path");
const bcrypt = require('bcrypt');
const express = require('express');
const Game = require('./server-side.js');

const app = express();
app.use(express.urlencoded());

const hostname = '127.0.0.1';
const port = 8080;

const WebSocket = require('ws');
const wsServer = new WebSocket.Server({port: 8082});

let pins = [];
let tokens = [];
let loggedUsers = new Map();
let connectionsCount = 0;

let db = {
    users: [
        {
            username: 'admin',
            password: '$2b$10$YmlqzSWscVklbX2hDtypCuOtVspMIZAxQyBxa95YAWe3RSTzAWXVi',
            email: 'admin@admin.com',
            name: 'Admin Admin',
            topScore: 0,
            gameLevel: 1
        }
    ],
    games: []
}

function createToken(length = 8){
    let chars = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
    let str = '';
    for (let i = 0; i < length; i++) {
        str += chars.charAt(Math.floor(Math.random() * chars.length));
    }
    return str;
};

function registerUser(data){
    if (data.password === data.password2){
        for (let i in db.users){
            if (db.users[i].username === data.username) {
                return false;
            }
        }
        let newUser = {
            username: data.username,
            password: bcrypt.hashSync(data.password, 10),
            email: data.email,
            name: data.name,
            topScore: 0,
            gameLevel: 1
        }
        db.users.push(newUser);
        return loginUser(data);
    }
    return false;
}

function loginUser(data){
    for (let i in db.users){
        if (db.users[i].username === data.username) {
            if (bcrypt.compareSync(data.password, db.users[i].password)) {
                loggedUsers.set(data.token, data.username);
                return true;
            }
        }
    }
    return false;
}

function getGame(pin){
    for (let i in db.games){
        if (db.games[i].data.pin == pin) {
            return db.games[i];
        }
    }
}

function updateDb(data){
    if (data.token){
        if (loggedUsers.has(data.token)) {
            let username = loggedUsers.get(data.token);
            let game = getGame(data.pin);

            for (let i in db.users) {
                if (db.users[i].username == username) {
                    if (game.data.topScore > db.users[i].topScore){
                        db.users[i].topScore = game.data.topScore;
                    }
                    if (game.data.topLevel > db.users[i].gameLevel){
                        db.users[i].gameLevel = game.data.topLevel;
                    }
                    break;
                }
            }
        }
    }
}

function createPin(num){
    if (num < 10)
        return ('000'+num);
    else if (num < 100)
        return ('00'+num);
    else if (num < 1000)
        return ('0'+num);
    else   
        return (''+num);
}

//----------------------- LOAD PAGE ------------------------
app.get('/', function (req, res){
    res.sendFile(path.resolve('static/index.html'));
});
app.get('/client-side.js', function (req, res){
    res.sendFile(path.resolve('client-side.js'));
});
//--------------------- LOGIN & LOGOUT ----------------------
app.post('/login', function (req, res){
    res.redirect('/?login='+loginUser(req.body));
});
app.get('/logout/:token', function (req, res){
    loggedUsers.delete(req.params.token);
    res.redirect('/');
});
//------------------------ REGISER --------------------------
app.post('/register', function (req, res){
    res.redirect('/?register='+registerUser(req.body));
});
//--------------------- LOAD RESOURCES ----------------------
app.get('/assets/:asset', function (req, res) {
    res.sendFile(path.resolve('assets/'+req.params.asset));
});
app.get('/resourceManager.js', function (req, res) {
    res.sendFile(path.resolve('resourceManager.js'));
});
//---------------------- SERVER START -----------------------
app.listen(port);
console.log('Server running');


wsServer.on('connection', (ws)=>{
   
    let token = createToken();
    tokens.push(token);
    ws.send(JSON.stringify({'type': 'setToken', 'token': token}))

    ws.on("close", (ws) => {
        console.log('disconnected server');
        console.log(ws);
      });

    ws.on('message', function(msg){
        var data = JSON.parse(msg);
        
        if (data.type === 'loadGame') {
            if (data.pin === false) {
                let pin = createPin(connectionsCount);
                console.log(pin);
                pins.push(pin);
                db.games.push(new Game(pin));
                ws.send(JSON.stringify({'type': 'setPin', 'pin': pin}));
                data.pin = pin;
                console.log('load game');

                if (data.token){
                    if (loggedUsers.has(data.token)) {
                        let username = loggedUsers.get(data.token);
                        let game = getGame(pin);

                        for (let i in db.users) {
                            if (db.users[i].username == username) {
                                game.data.level = db.users[i].gameLevel-1;
                                game.data.topLevel = db.users[i].gameLevel;
                                game.data.topScore = db.users[i].topScore;
                                game.levelSwitch();
                                break;
                            }
                        }
                    }
                }
            }

            getGame(data.pin).data.running = true;
            getGame(data.pin).data.win = false;
            getGame(data.pin).data.reset = false;

            let loop = setInterval(function() {
                let game = getGame(data.pin);
                gameLoop(game);

                if (game.data.running) {
                    ws.send(JSON.stringify({'type': 'drawGame', 'gameData': game.data}));
                }
                else {
                    clearInterval(loop);
                    if (game.data.win) {
                        ws.send(JSON.stringify({'type': 'statusGame', 'status': 'win', 'gameData': game.data}));
                    }
                    else if (!game.data.win && !game.data.reset){
                        ws.send(JSON.stringify({'type': 'statusGame', 'status': 'lose', 'gameData': game.data}));
                    }
                    else if (game.data.reset) {
                        ws.send(JSON.stringify({'type': 'statusGame', 'status': 'reset', 'gameData': game.data}));
                    }
                }
            }, getGame(data.pin).data.speed);
        }
        
        if (data.type === 'resetGame') {
            if (data.pin) {
                let game = getGame(data.pin);

                if (game.data.running) {
                    game.resetGame();
                    updateDb(data);
                    game.data.reset = true;
                    game.data.running = false;
                }
                else {
                    game.resetGame();
                    updateDb(data);
                    game.data.reset = true;
                    game.data.running = false;
                    ws.send(JSON.stringify({'type': 'statusGame', 'status': 'reset', 'gameData': game.data}));
                }
            }
        }

        if (data.type === 'keyPress' && data.pin) {   
            let game = getGame(data.pin);
            let ship = game.data.ship;

            if (data.key === 'space') {
                game.data.missiles.push(ship[0]-11);
            }
            else if (data.key === 'left') {
                if(ship[0] > 100) {
                    var i=0;
                    for(i=0;i<ship.length;i++) {
                        ship[i]--;
                    }
                }
            }
            else if (data.key === 'right') {
                if (ship[0] < 108) {
                    var i=0;
                    for(i=0;i<ship.length;i++) {
                        ship[i]++;
                    }
                }
            }
        }  
    });
    connectionsCount++;
    console.log(connectionsCount);
});

//--------------------------- MAIN GAME LOOP ---------------------------
function gameLoop(game) {

    game.moveAliens();
    game.moveMissiles();
    game.checkCollisionsMA();

    if (game.data.a%4==3) {
        game.lowerAliens();
    }
    if (game.RaketaKolidujeSVotrelcom()) { // lose 
        console.log('server lose');
        game.resetGame();
        console.log('gameloop - '+game.data.running)
    }

    if (game.data.aliens.length === 0) { // win
        console.log('server win');
        game.levelSwitch();
    }
    game.data.a++;
}