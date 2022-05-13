import ResourceManager from './resourceManager.js'
var storage = window.sessionStorage;
const socket = new WebSocket('ws://localhost:8082');
const resourceManager = new ResourceManager();
var gameData = {};
var loaded = false;
var pin = false;

var jquery = document.createElement('script');
jquery.src = 'https://code.jquery.com/jquery-3.4.1.min.js';
jquery.type = 'text/javascript';
document.getElementsByTagName('head')[0].appendChild(jquery);

//---------------------------------- WS MESAGES ----------------------------------

socket.addEventListener('open', (ev)=>{
    initSpace();
    renderForm();
});

/*socket.addEventListener('close', (ev)=>{
    console.log('disconected');
    socket.close();
});*/

socket.addEventListener('message',(msg)=>{
    var data = JSON.parse(msg.data);

    if (data.type === 'setToken') {
        if (!storage.getItem('token')) {
            storage.setItem('token', data.token);
            console.log('set-token')
            renderForm();
        }
    }
    else if (data.type === 'drawGame') {
        gameData = data.gameData;
        console.log('IDEM DRAW ON PIN');
        drawAll(gameData);
    }
    else if (data.type === 'setPin') {
        pin = data.pin;
        console.log(pin);
    }
    else if (data.type === 'statusGame') {
        gameData = data.gameData;
        if (data.status === 'lose') {
            lose();
        }
        else if (data.status === 'win') {
            win();
        }
        else if (data.status === 'reset') {
            socket.send(JSON.stringify({'type': 'loadGame', 'pin': pin, 'token': storage.getItem('token')}));
        }
    }
});

//----------------------------------- LOGIN & REGISTER -----------------------------------

function urlParser(url) {
    var queryParams = null;
    var queryParamsParts = [];
    var queryObject = {};

    if (url.indexOf('?') >= 0) {
        queryParams = url.split('?')[1];

        if (queryParams.indexOf('&') >= 0) {
            queryParamsParts = queryParams.split('&');
        }
        else {
            queryParamsParts = [queryParams];
        }

        queryParamsParts.forEach(qp => {
            if (qp.indexOf('=') >= 0) {
                let qpParts = qp.split('=');
                queryObject[qpParts[0]] = qpParts[1];
            }
            else
                queryObject[qp] = true;
        });
    }
    return queryObject;
}

function createFormInput(type, name, placeholder = '') {
    var input = document.createElement('input');
    input.setAttribute('type', type);
    input.setAttribute('name', name);

    if (placeholder)
        input.setAttribute('placeholder', placeholder);
    
    return input;
}

function createLoginForm() {
    var form = document.createElement('form');
    form.setAttribute('method', 'post');
    form.setAttribute('action', '/login');

    var usernameInput = createFormInput('text', 'username', 'username');
    var passwordInput = createFormInput('password', 'password', 'password');

    // token
    var tokenInput = createFormInput('hidden', 'token');
    tokenInput.setAttribute('value', storage.getItem('token'));

    var loginButton = document.createElement('button');
    loginButton.innerHTML = 'Login';

    form.appendChild(usernameInput);
    form.appendChild(passwordInput);
    form.appendChild(tokenInput);
    form.appendChild(loginButton);

    return form;
}

function createRegisterForm() {
    var form = document.createElement('form');
    form.setAttribute('method', 'post');
    form.setAttribute('action', '/register');

    var nameInput = createFormInput('text', 'name', 'name');
    var emailInput = createFormInput('email', 'email', 'email');

    var usernameInput = createFormInput('text', 'username', 'username');
    var passwordInput = createFormInput('password', 'password', 'password');
    var passwordInput2 = createFormInput('password', 'password2', 'repeat password');

    var tokenInput = createFormInput('hidden', 'token');
    tokenInput.setAttribute('value', storage.getItem('token'));
    
    var registerButton = document.createElement('button');
    registerButton.innerHTML = 'Register';
    registerButton.addEventListener('click', function(event){
        console.log('idem');
    });


    form.appendChild(nameInput);
    form.appendChild(emailInput);

    form.appendChild(usernameInput);
    form.appendChild(passwordInput);
    form.appendChild(passwordInput2);
    form.appendChild(tokenInput);

    form.appendChild(registerButton);

    return form;
}

var urlData = urlParser(window.location.href);


function renderForm() {
    if (storage.getItem('token')) { 
        console.log('MAM TOKEN');
        
        var loggedUser = document.createElement('h3');
        loggedUser.id = 'loggedUser';
        if (urlData.login === 'true' || urlData.register === 'true') {
            loggedUser.innerHTML = 'Logged: SUCESS';
        }
        else {
            loggedUser.innerHTML = 'Logged: [N/A]';
        }
        document.body.appendChild(loggedUser);

        var loginForm = createLoginForm();
        var registerForm = createRegisterForm();

        var loginLink = document.createElement('a');
        loginLink.setAttribute('href', '#');
        loginLink.innerHTML = 'Login';

        var registerLink = document.createElement('a');
        registerLink.setAttribute('href', '#');
        registerLink.innerHTML = 'Register';

        registerLink.addEventListener('click', (ev) => {
            ev.preventDefault();
            loginForm.remove();
            registerLink.remove();
            document.body.appendChild(registerForm);
            document.body.appendChild(loginLink);
        });
        
        loginLink.addEventListener('click', (ev) => {
            ev.preventDefault();
            registerForm.remove();
            loginLink.remove();
            document.body.appendChild(loginForm);
            document.body.appendChild(registerLink);
        });

        if ((!urlData.login || urlData.login === 'false') && (!urlData.register || urlData.register === 'false')) {
            document.body.appendChild(loginForm);
            document.body.appendChild(registerLink);
        }
        else if (urlData.login === 'true' || urlData.register === 'true') {
            var logoutLink = document.createElement('a');
            logoutLink.setAttribute('href', '/logout/'+storage.getItem('token'));
            logoutLink.innerHTML = 'Logout';
            document.body.appendChild(logoutLink);
        }  
    }
}

// ------------------------------------- RESOURCES ---------------------------------------------
const IMAGES = [
    {name: 'alien', src: 'assets/alien.png'},
    {name: 'ship', src: 'assets/ship.png'},
    {name: 'missile', src: 'assets/missile.png'},
    {name: 'space', src: 'assets/space.png'},
    {name: 'win', src: 'assets/win.png'},
    {name: 'lose', src: 'assets/lose.png'}
];
const SOUNDS = [
    {name: 'audio', src: 'assets/audio.wav'}
];

// ------------------------------------- GAME ---------------------------------------------

var myAudio = null;
window.localStorage.setItem('debug', false);

// load create and load all elements
async function initSpace() { 
    //----------------- Base -----------------
    var title = document.createElement('h1');
    title.innerHTML = "Vesmirna hra";
    document.body.appendChild(title);

    var space = document.createElement('div');
    space.id = 'space';
    document.body.appendChild(space);    

    var start = document.createElement('button');
    start.id = "start";
    start.innerHTML = "Start";
    document.body.appendChild(start);

    start.addEventListener('click',function(){
        socket.send(JSON.stringify({'type': 'loadGame', 'pin': pin, 'token': storage.getItem('token')}));
    });

    //----------------- Mod -----------------
    var canvas = document.createElement('canvas');
    canvas.id = "canvas";
    canvas.width = 528;
    canvas.height = 528;
    space.appendChild(canvas);

    var level = document.createElement('h2');
    level.id = "level";
    level.innerHTML = "Level = N/A" ;
    space.before(level);
    

    var score = document.createElement('h2');
    score.id = "score";
    score.innerHTML = "Score = N/A" ;
    level.after(score);

    var topLevel = document.createElement('h2');
    topLevel.id = "topLevel";
    topLevel.innerHTML = "Top Level = N/A" ;
    score.after(topLevel);

    var topScore = document.createElement('h2');
    topScore.id = "topScore";
    topScore.innerHTML = "Top Score = N/A" ;
    topLevel.after(topScore);

    //----------------- Buttons -----------------
    var reset = document.createElement('button');
    reset.id = "reset";
    reset.innerHTML = "Reset";
    reset.addEventListener('click',function(){
        socket.send(JSON.stringify({'type': 'resetGame', 'pin': pin, 'token': storage.getItem('token')}));
    });
    start.after(reset);

    var audio = document.createElement('button');
    audio.id = "audio";
    audio.innerHTML = "Audio";
    reset.after(audio);

    var debug = document.createElement('button');
    debug.id = "debug";
    debug.innerHTML = "Debug start";
    audio.after(debug);

    await resourceManager.init(IMAGES, SOUNDS);

    myAudio = resourceManager.getSoundSource('audio');
    myAudio.loop = true;
    myAudio.volume = 0.1;

    drawSpace();
    loaded = true;
}

//--------------------------- DRAW FUNCTIONS ---------------------------
async function drawAll(gameData) {
    console.log('IDEM DRAW ALL');
    drawSpace();
    drawAliens(gameData.aliens);
    drawMissiles(gameData.missiles);
    drawShip(gameData.ship);
    drawScore(gameData);
}

function drawScore(gameData) {
    document.getElementById('level').innerHTML = "Level = " + gameData.level; 
    document.getElementById('score').innerHTML = "Score = " + gameData.score;  
    document.getElementById('topLevel').innerHTML = "Top Level = " + gameData.topLevel;  
    document.getElementById('topScore').innerHTML = "Top Score = " + gameData.topScore;      
}

function drawSpace() {
    console.log('IDEM DRAW SPACE');
    var canvas = document.getElementById('canvas');
    var ctx = canvas.getContext("2d"); 
    var image = resourceManager.getImageSource('space');  

    ctx.clearRect(0, 0, canvas.width, canvas.height);
    ctx.drawImage(image, 0, 0, canvas.width, canvas.height);

    ctx.lineWidth = "1";
    ctx.strokeStyle = "black";

    for (var y = 0; y < 11; y++) {
        for (var x = 0; x < 11; x++) {
            ctx.strokeRect((x*48), (y*48), 48, 48);
        }
    }
}

function drawAliens(aliens) {
    var canvas = document.getElementById('canvas');
    var ctx = canvas.getContext("2d");
    var image = resourceManager.getImageSource('alien');
     
    for (var i = 0; i < aliens.length; i++) {
        var posX = ( 48 * (aliens[i] % 11) ); 
        var posY = ( 48 * ((aliens[i] - (aliens[i] % 11)) / 11) );
        ctx.drawImage(image, posX, posY, 48, 48);
    }
}
function drawMissiles(missiles) {
    var canvas = document.getElementById('canvas');
    var ctx = canvas.getContext("2d");
    var image = resourceManager.getImageSource('missile');
    
    for (var i = 0; i < missiles.length; i++) {
        var posX = ( 48 * (missiles[i] % 11) ); 
        var posY = ( 48 * ((missiles[i] - (missiles[i] % 11)) / 11) );
        ctx.drawImage(image, posX, posY, 48, 48);
    }
}
function drawShip(ship) {
    var canvas = document.getElementById('canvas');
    var ctx = canvas.getContext("2d");
    var image = resourceManager.getImageSource('ship');  
     
    var posX = ( 48 * ((ship[0]-1) % 11) ); 
    var posY = ( 48 * (((ship[0]-1) - ((ship[0]-1) % 11)) / 11) );
    ctx.drawImage(image, posX, posY, 144, 96);
}
function lose() { 
    var canvas = document.getElementById('canvas');
    var ctx = canvas.getContext("2d");
    var image = resourceManager.getImageSource('lose');

    ctx.clearRect(0, 0, canvas.width, canvas.height);
    ctx.drawImage(image, 0, 0, canvas.width, canvas.height);

    ctx.lineWidth = "1";
    ctx.strokeStyle = "black";

    for (var y = 0; y < 11; y++) {
        for (var x = 0; x < 11; x++) {
            ctx.strokeRect((x*48), (y*48), 48, 48);
        }
    }
}
function win() {
    var canvas = document.getElementById('canvas');
    var ctx = canvas.getContext("2d");
    var image = resourceManager.getImageSource('win');

    ctx.clearRect(0, 0, canvas.width, canvas.height);
    ctx.drawImage(image, 0, 0, canvas.width, canvas.height);

    ctx.lineWidth = "1";
    ctx.strokeStyle = "black";

    for (var y = 0; y < 11; y++) {
        for (var x = 0; x < 11; x++) {
            ctx.strokeRect((x*48), (y*48), 48, 48);
        }
    }

    setTimeout(()=>{
        socket.send(JSON.stringify({'type': 'loadGame', 'pin': pin}));
    },2000);
}

function checkKey(e) {
    e = e || window.event;
    if (e.keyCode == '37') { 
        socket.send(JSON.stringify({'type': 'keyPress', 'pin': pin, 'key': 'left'}));
    }
    else if (e.keyCode == '39') {
        socket.send(JSON.stringify({'type': 'keyPress', 'pin': pin, 'key': 'right'}));
    }
    else if (e.keyCode == '32') {
        socket.send(JSON.stringify({'type': 'keyPress', 'pin': pin, 'key': 'space'}));
    }
    else if (e.key == 'g') {
        socket.send(JSON.stringify({'type': 'keyPress', 'pin': pin, 'key': 'left'})); 
    }
    else if (e.key == 'j') {
        socket.send(JSON.stringify({'type': 'keyPress', 'pin': pin, 'key': 'right'}));
    }


}
document.addEventListener('keydown',checkKey);


/*document.getElementById('audio').addEventListener('click', function() {
    
    if (!audioPlaying) {
        myAudio.play();
        audioPlaying = true;
    }
    else {
        myAudio.pause();
        myAudio.currentTime = 0;
        audioPlaying = false;
    }
});*/