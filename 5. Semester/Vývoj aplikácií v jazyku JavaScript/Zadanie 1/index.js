function initSpace() { //inicializacia hracej plochy, resp. vytvorenie tabulky
    var space = document.getElementById('space').querySelector('table');
    space.innerHTML = '';
    var p = 0;
    for(var i=0;i<11;i++) {
        var tr = document.createElement('tr');
        for(var j=0;j<11;j++) {
            var td = document.createElement('td');
            td.id = 'p'+p;
            td.innerHTML = p;
            tr.appendChild(td);
            p++;
        }
        space.appendChild(tr);
    }
}
initSpace();

function drawSpace() { //vyfarbenie plochy pre alienov na cierno
    var i=0;
    for(i=0;i<99;i++) {
        document.getElementById('p'+i).style.background = '#202020';
    }
}


var aliens = [1,3,5,7,9,23,25,27,29,31]; //fyfarbovanie alienov na zeleno
function drawAliens() {
    var i=0;
    for(i=0;i<aliens.length;i++) {
        document.querySelector('#p'+aliens[i]).style.background = 'green';
    }
}

var direction = 1;
function moveAliens() { //posuvanie alienov v riadku
    var i=0;
    for(i=0;i<aliens.length;i++) {
        aliens[i]=aliens[i]+direction;
    }
    direction *= -1;
}
function lowerAliens() { //posuvanie alienov o riadok nizsie
    var i=0;
    for(i=0;i<aliens.length;i++) {
        aliens[i]+=11;
    }
}

var ship = [104,114,115,116]; //reprezentacia lode
function drawShip() { //vykreslovanie lode
    var i=0;
    for(i=99;i<121;i++) {
        document.getElementById('p'+i).style.background = '#202020';
    }
    for(i=0;i<ship.length;i++) {
        document.getElementById('p'+ship[i]).style.background = 'white';
    }
}

var missiles = []; //pravdepodobne cisla
function drawMissiles() {
    var i=0;
    var list = [];
    for(i=0;i<missiles.length;i++) {
        list.push('#p'+missiles[i]);
    }
    document.getElementById('cssmissile').innerHTML = list.join(',')+'{background: red !important;}';
}

function moveMissiles() {
    var i=0;
    for(i=0;i<missiles.length;i++) {
        missiles[i]-=11 ;
        if(missiles[i] < 0) missiles.splice(i,1); //odstranuje raketu za haranicou hracej plochy
    }                       //startovacia pozicia, pocet prvkov na vymazanie
}


function checkKey(e) {
    e = e || window.event;
    if (e.keyCode == '37') { //posun dolava
        if(ship[0] > 100) {
            var i=0;
            for(i=0;i<ship.length;i++) {
                ship[i]--;
            }
        }
    }
    else if (e.keyCode == '39' && ship[0] < 108) { //posun doprava
        var i=0;
        for(i=0;i<ship.length;i++) {
            ship[i]++;
        }
    }
    else if (e.keyCode == '32') { //strielanie rakiet
        missiles.push(ship[0]-11); //startovacia pozicia
    }
}

function checkCollisionsMA() { //Kolizia aliena a rakety
    for(var i=0;i<missiles.length;i++) {
        if(aliens.includes(missiles[i])) {
            var alienIndex = aliens.indexOf(missiles[i]);
            aliens.splice(alienIndex, 1); //odstran aliena
            missiles.splice(i, 1); //odstran raketu
        }
    }
}

function RaketaKolidujeSVotrelcom() { //kolizia s hracom
    for(var i=0;i<aliens.length;i++) {
        if(aliens[i]>98) {
            return true;
        }
    }
    return false;
}

function loose() { //vyfarbenie policok na cerveno
    var i=0;
    for(i=0;i<121;i++) {
        document.getElementById('p'+i).style.background = 'red';
    }
    running = false;
}

function win() { //vyfarbenie policok na zeleno
    var i=0;
    for(i=0;i<121;i++) {
        document.getElementById('p'+i).style.background = 'green';
    }
}

var level = 1;
var speed = 512;
function nextLevel() {
    level++;
    console.log('level: '+level);
    if(level==1) aliens = [1,3,5,7,9,23,25,27,29,31];
    if(level==2) aliens = [1,3,5,7,9,13,15,17,19,23,25,27,29,31];
    if(level==3) aliens = [1,5,9,23,27,31];
    if(level==4) aliens = [45,53];
    if(level > 4) {
        level = 1;
        aliens = [1,3,5,7,9,23,25,27,29,31];
        speed = speed / 2;
    }
    gameLoop();
}
var running = false;


function gameLoop() {
    console.log('gameloop');

    running = true;
    document.addEventListener('keydown',checkKey);

    var a = 0;
    var loop1 = setInterval(function(){
        moveAliens();
        moveMissiles();
        checkCollisionsMA();
        if(a%4==3) lowerAliens();
        if(RaketaKolidujeSVotrelcom()) {
            clearInterval(loop2);
            clearInterval(loop1);
            document.removeEventListener('keydown',checkKey);
            missiles = [];
            drawMissiles();
            loose();
        }
        a++;
    },speed);
    var loop2 = setInterval(function(){
        drawSpace();
        drawAliens();
        drawMissiles();
        drawShip();
        if(aliens.length === 0) {
            clearInterval(loop2);
            clearInterval(loop1);
            document.removeEventListener('keydown',checkKey);
            missiles = [];
            drawMissiles();
            win();
            setTimeout(function(){
                nextLevel();
            },1000);
        }
    },speed/2);
}

document.getElementById('start').addEventListener('keydown',function(e){
    e.preventDefault();
    e.stopPropagation();
});
document.getElementById('start').addEventListener('click',function(){
    /*
    */
    if(!running) gameLoop();
});