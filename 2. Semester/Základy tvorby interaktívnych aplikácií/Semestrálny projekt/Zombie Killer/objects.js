class Player {
    constructor(x, y, width, height) {
        this.canvas = document.getElementById("canvas");
        this.image = resourceManager.getImageSource('player');

        this.x = 400;
        this.y = 400;
        this.width = 60;
        this.height = 100;
        this.speed = 20;
        this.pressedKeys = [];

        this.center = [ this.x + this.width/2, this.y + this.height/2 ];
        this.mousePos = [];
        this.angle;

        this.hp = 100;
        this.physical = true;
    }

    handleEvent(ev) {
        if (ev.type == "keydown")
            this.pressedKeys[ev.key] = true;

        else if (ev.type == "keyup") 
            this.pressedKeys[ev.key] = false;

        else if (ev.type == "mousemove")
            this.mousePos = [ev.clientX, ev.clientY];
    }

    move(dt) {
        this.center = [ this.x + this.width/2, this.y + this.height/2 ];
        this.angle = Math.atan2(this.mousePos[0] - this.center[0], -(this.mousePos[1] - this.center[1]));

        if (this.pressedKeys['w'] && this.y >= 0) {
            this.y -= this.speed*dt;}

        if (this.pressedKeys['s'] && this.y <= canvas.height - this.height) {
            this.y += this.speed*dt;}

        if (this.pressedKeys['a'] && this.x >= 0) {
            this.x -= this.speed*dt;}

        if (this.pressedKeys['d'] && this.x <= canvas.width - this.width) {
            this.x += this.speed*dt;}
    }

    render(ctx) {
        ctx.save();

        ctx.translate(this.x+this.width/2, this.y+this.height/2);
        ctx.rotate(this.angle);
        ctx.translate(-this.x-this.width/2, -this.y-this.height/2);

        ctx.drawImage(this.image, this.x, this.y, this.width, this.height);

        ctx.restore();
    }  
}


class Zombie {
    constructor(id, x, y) {
        this.canvas = document.getElementById("canvas");
        this.image = resourceManager.getImageSource('zombie_animation');
    
        this.x = x;
        this.y = y;
        this.width = 311/3;
        this.height = 288/3;
        this.hp = 100;
        this.physical = true;
        this.id = id;
        this.frame = 0;

        this.center = [ this.x + this.width/2, this.y + this.height/2 ];
        this.angle;
        this.targetPos = [];

        this.speed = 0;
        setTimeout(function(obj){ 
            obj.speed = 2;

            var sound = resourceManager.getSoundSource('zombie');
            sound.volume = 0.2;
            sound.play();
        }, 2000, this);

    }

    handleEvent(ev) {
    }

    move(dt) {
        this.center = [ this.x + this.width/2, this.y + this.height/2 ];
        this.angle = Math.atan2(this.targetPos[0] - this.center[0], -(this.targetPos[1] - this.center[1]));

        if ( (this.x > 0 && this.x < 900) && (this.y > 0 && this.y < 700) ){
            var sound = resourceManager.getSoundSource('zombie_move');
            sound.volume = 0.2;
            sound.play();
        }

        var trX = this.targetPos[0] - this.center[0];
        var trY = this.targetPos[1] - this.center[1];

        var distance = Math.sqrt(trX * trX + trY * trY);

        if (distance >= this.speed){
            this.x += (trX / distance) * this.speed;
            this.y += (trY / distance) * this.speed;
        }
    }

    render(ctx) {
        ctx.save();

        ctx.translate(this.x+this.width/2, this.y+this.height/2);
        ctx.rotate(this.angle);
        ctx.translate(-this.x-this.width/2, -this.y-this.height/2);

        this.frame++;

        if (this.frame > 16)
            this.frame = 0;

        ctx.drawImage(this.image, this.width*3*this.frame, 0, this.width*3, this.height*3, this.x, this.y, this.width, this.height)

        ctx.restore();
    }
}

class hpBar {
    constructor() {
        this.canvas = document.getElementById("canvas");
        this.image = resourceManager.getImageSource('hp');
    
        this.x = 20;
        this.y = 20;
        this.width = 300;
        this.height = 80;
        this.physical = false;

        this.hp = 100;
    }

    render(ctx) {
        ctx.save()
        ctx.drawImage(this.image, 
            0, 0, 
            this.width * this.hp / 100, this.height,
            this.x, this.y, 
            this.width * this.hp / 100, this.height
        );
        ctx.restore()
    }

    move(dt) {}

    handleEvent() {}
}



class Bullet {
    constructor(id, x, y, bulletX, bulletY) {
        this.canvas = document.getElementById("canvas");
        this.image = resourceManager.getImageSource('bullet');
    
        this.x = x + bulletX;
        this.y = y - bulletY;
        this.width = 5;
        this.height = 5;
        this.physical = true;

        this.speed = 10;
        this.center = [ this.x + this.width/2, this.y + this.height/2 ];
        this.targetPos = [];

        this.id = id;
    }

    render(ctx) {
        ctx.save()
        ctx.drawImage(this.image, this.x, this.y, this.width, this.height);
        ctx.restore()
    }

    move(dt) {
        var trX = this.targetPos[0] - this.center[0];
        var trY = this.targetPos[1] - this.center[1];

        var distance = Math.sqrt(trX * trX + trY * trY);

        if (distance >= this.speed){
            this.x += (trX / distance) * this.speed;
            this.y += (trY / distance) * this.speed;
        }
    }

    handleEvent() {}
}