var score = 0;
var aliensToKill = 0;
var activeIntervals = [];
var originalSetInterval = window.setInterval;

window.localStorage.setItem('debug', false);

window.setInterval = function(funct, delay) {
    var id = originalSetInterval(funct, delay);
    activeIntervals.push(id);
    return id;
}

function initSpace() {

    var table = document.getElementById('space').querySelector('table');
    table.parentNode.removeChild(table);
    
    var canvas = document.createElement('canvas');
    canvas.id = "canvas";
    canvas.width = 528;
    canvas.height = 528;

    var space = document.getElementById('space');
    space.appendChild(canvas);

    var level = document.createElement('h2');
    level.id = "level";
    space.before(level);

    var score = document.createElement('h2');
    score.id = "score";
    level.after(score);

    var start = document.getElementById('start');

    var reset = document.createElement('button');
    reset.id = "reset";
    reset.innerHTML = "Reset";
    start.after(reset);

    var audio = document.createElement('button');
    audio.id = "audio";
    audio.innerHTML = "Audio";
    reset.after(audio);

    var debug = document.createElement('button');
    debug.id = "debug";
    debug.innerHTML = "Debug start";
    audio.after(debug);

    drawSpace();
}
initSpace();


function drawSpace() {

    var canvas = document.getElementById('canvas');
    var ctx = canvas.getContext("2d");
    var image = new Image();  

    image.onload = function() { 
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
    image.src = "assets/space.png";
    checkScore();
}


function drawAliens() {

    var canvas = document.getElementById('canvas');
    var ctx = canvas.getContext("2d");
    var image = new Image();  
    
    image.onload = function() {   
        for (var i = 0; i < aliens.length; i++) {
            var posX = ( 48 * (aliens[i] % 11) ); 
            var posY = ( 48 * ((aliens[i] - (aliens[i] % 11)) / 11) );
            ctx.drawImage(image, posX, posY, 48, 48);
        }
    }
    image.src = "assets/alien.png"; 
}

function drawShip() {

    var canvas = document.getElementById('canvas');
    var ctx = canvas.getContext("2d");
    var image = new Image();  
    
    image.onload = function() {   
        var posX = ( 48 * ((ship[0]-1) % 11) ); 
        var posY = ( 48 * (((ship[0]-1) - ((ship[0]-1) % 11)) / 11) );
        ctx.drawImage(image, posX, posY, 144, 96);
    } 
    image.src = "assets/ufo.png";
}

function drawMissiles() {

    var canvas = document.getElementById('canvas');
    var ctx = canvas.getContext("2d");
    var image = new Image();  
    
    image.onload = function() {
        for (var i = 0; i < missiles.length; i++) {
            var posX = ( 48 * (missiles[i] % 11) ); 
            var posY = ( 48 * ((missiles[i] - (missiles[i] % 11)) / 11) );
            ctx.drawImage(image, posX, posY, 48, 48);
        }
    }
    image.src = "assets/missile.png";
}

function win() {

    var canvas = document.getElementById('canvas');
    var ctx = canvas.getContext("2d");
    var image = new Image();  

    image.onload = function() { 
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
    image.src = "assets/win.png";

    document.removeEventListener('keydown', checkKeyMod);
    if (window.localStorage.getItem('debug') === 'true')
        console.log("Vyhral si");
}

function loose() { 

    var canvas = document.getElementById('canvas');
    var ctx = canvas.getContext("2d");
    var image = new Image();  

    image.onload = function() { 
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
    image.src = "assets/lose.png";

    running = false;
    document.removeEventListener('keydown', checkKeyMod);
    if (window.localStorage.getItem('debug') === 'true')
        console.log("Prehral si");
}

function checkScore() {

    if (aliensToKill == 0) {
        aliensToKill = aliens.length
    }

    while (aliensToKill > aliens.length) {
        score += 10;
        aliensToKill--;

        if (window.localStorage.getItem('debug') === 'true')
        console.log("Trafil si");
    }

    document.getElementById('level').innerHTML = "Level = " + level; 
    document.getElementById('score').innerHTML = "Score = " + score;        
}

function checkKeyMod(e) {
    e = e || window.event;
    if (e.key == 'g' && ship[0] > 100) {
        var i = 0;
        for(i = 0; i < ship.length; i++) {
            ship[i]--;
        }
    }
    else if (e.key == 'j' && ship[0] < 108) {
        var i = 0;
        for(i = 0; i < ship.length; i++) {
            ship[i]++;
        }
    }
}

document.getElementById('start').addEventListener('click', function() {
    document.addEventListener('keydown', checkKeyMod);
});

document.getElementById('reset').addEventListener('click', function() {
    for (var i in activeIntervals) 
        clearInterval(activeIntervals[i]);
        
    activeIntervals = [];
    missiles = [];
    aliens = [];

    level = 0;
    score = 0;
    aliensToKill = 0;

    speed = 512;
    direction = 1;

    running = false;

    nextLevel();
    
    document.addEventListener('keydown', checkKeyMod);
    if (window.localStorage.getItem('debug') === 'true')
        console.log("RESET");
});


var audioPlaying = false;
var myAudio = new Audio('assets/audio.wav'); 
myAudio.loop = true;
myAudio.volume = 0.1;

document.getElementById('audio').addEventListener('click', function() {
    
    if (!audioPlaying) {
        myAudio.play();
        audioPlaying = true;
    }
    else {
        myAudio.pause();
        myAudio.currentTime = 0;
        audioPlaying = false;
    }
});

document.getElementById('debug').addEventListener('click', function() {

    if (window.localStorage.getItem('debug') === 'true') {
        window.localStorage.setItem('debug', false);
        document.getElementById('debug').innerHTML = "Debug start";
    }
    else {
        window.localStorage.setItem('debug', true);
        document.getElementById('debug').innerHTML = "Debug stop";
    }
});
