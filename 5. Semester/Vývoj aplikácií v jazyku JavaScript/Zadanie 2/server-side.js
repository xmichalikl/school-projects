class Game {
    constructor(pin){
        this.data = {

            a: 0,
            pin: pin,
            level: 1,
            topLevel: 1,
            speed: 512,
            win: false,
            reset: false,
            running: false,

            score: 0,
            topScore: 0,
            aliensToKill: 0,

            aliens: [1,3,5,7,9,23,25,27,29,31], 
            ship: [104,114,115,116],
            direction: 1,
            missiles: []
        }
    }

    //--------------------------- MOVE FUNCTIONS ---------------------------
    moveAliens() { //posuvanie alienov v riadku
        var i=0;
        for(i=0;i<this.data.aliens.length;i++) {
            this.data.aliens[i]=this.data.aliens[i]+this.data.direction;
        }
        this.data.direction *= -1;
    }
    lowerAliens() { //posuvanie alienov o riadok nizsie
        var i=0;
        for(i=0;i<this.data.aliens.length;i++) {
            this.data.aliens[i]+=11;
        }
    }
    moveMissiles() {
        var i=0;
        for(i=0;i<this.data.missiles.length;i++) {
            this.data.missiles[i]-=11;
            if(this.data.missiles[i] < 0) 
                this.data.missiles.splice(i,1); //odstranuje raketu za haranicou hracej plochy
        }
    }
    //--------------------------- COLLISION CHECK ---------------------------
    checkCollisionsMA() { //Kolizia aliena a rakety
        for(var i=0;i<this.data.missiles.length;i++) {
            if(this.data.aliens.includes(this.data.missiles[i])) {
                var alienIndex = this.data.aliens.indexOf(this.data.missiles[i]);
                this.data.aliens.splice(alienIndex, 1); //odstran aliena
                this.data.missiles.splice(i, 1); //odstran raketu
                this.data.score += 10;
            }
        }
    }
    RaketaKolidujeSVotrelcom() { //kolizia s hracom
        for(var i=0;i<this.data.aliens.length;i++) {
            if(this.data.aliens[i]>98) {
                return true;
            }
        }
        return false;
    }
    
    levelSwitch(){
        this.data.level++;

        this.direction = 1;
        this.data.missiles = [];
        this.data.running = false;
        this.data.win = true;
    
        let tempLevel = this.data.level % 5;
        if (tempLevel == 0)
            tempLevel = 5;
 
        if (tempLevel==1) this.data.aliens = [1,3,5,7,9,23,25,27,29,31];
        else if (tempLevel==2) this.data.aliens = [1,3,5,7,9,13,15,17,19,23,25,27,29,31];
        else if (tempLevel==3) this.data.aliens = [1,5,9,23,27,31];
        else if (tempLevel==4) this.data.aliens = [45,53];
        else if (tempLevel > 4) {
            this.data.aliens = [1,3,5,7,9,23,25,27,29,31];
            this.data.speed = this.data.speed / 2;
        }
    }
    resetGame(){
        console.log('reset game');

        if (this.data.score > this.data.topScore)
            this.data.topScore = this.data.score;

        if (this.data.level > this.data.topLevel)
            this.data.topLevel = this.data.level;

        this.data.a = 0;
        //this.data.pin = pin;
        this.data.level = 1;
        this.data.speed = 512;
        this.data.win = false;
        this.data.running = false;

        this.data.score = 0;
        this.data.aliensToKill = 0;

        this.data.aliens = [1,3,5,7,9,23,25,27,29,31];
        this.data.ship = [104,114,115,116];
        this.data.direction = 1;
        this.data.missiles = [];
    }
}
module.exports = Game;

