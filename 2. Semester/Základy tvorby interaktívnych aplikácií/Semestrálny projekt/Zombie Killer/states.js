const STATES = {
    GAME: 'gameState',
    MAIN_MENU: 'mainMenu',
    INFO: 'info',
    SETTINGS: 'settings',
    GAME_OVER: 'gameOver',
    PAUSE: 'pause'
}

class StateManager {
    states = {};
    currentState = null;

    constructor(resourceManager, ctx) {
        this.resourceManager = resourceManager;
        this.ctx = ctx;
    }

    init() {
        const ctx = this.ctx;
        this.states = {
            gameState: new GameState(this, ctx),
            mainMenu: new MainMenu(this, ctx),
            info: new InfoState(this, ctx),
            settings: new SettingsState(this, ctx),
            gameOver: new GameOverState(this, ctx),
            pause: new PauseState(this, ctx)
        };
        this.currentState = this.states.mainMenu;
    }

    changeState(state) {
        const newState = this.states[state];
        if (!newState) {
            throw new Error(`State '${state}' not found`)
        }
        this.currentState = newState;
    }

    update(dt) {
        this.currentState.update(dt);
    }

    handleEvent(ev) {
        this.currentState.handleEvent(ev);
    }

    render() {
        this.currentState.render(this.ctx);
    }
}

class BaseState {

    objects = [];

    constructor(stateManager, ctx) {
        this.stateManager = stateManager;
        this.ctx = ctx;
    }

    render() {
        this.objects.forEach(object => object.render(this.ctx));
    }

    update(dt) {
    }

    handleEvent(ev) {
    }
}

class GameState extends BaseState {
    constructor(manager, ctx) {
        super(manager, ctx);

        this.bgImage = resourceManager.getImageSource('background');
        this.ui = [];

        this.wave = {
            number: 1,
            delay: 5,
            zombies: 0,
            zpw: 3
        };

        this.player = new Player();
        this.hpBar = new hpBar();

        this.objects.push( this.player );
        this.ui.push( this.hpBar );

        this.bullets = 0;
        this.zombies = 0;
        this.killedZombies = 0;
        this.killsMultiplier = 15;
    }

    reset(){
        this.objects = [];

        this.wave = {
            number: 1,
            delay: 5,
            zombies: 0,
            zpw: 3
        };

        this.player = new Player();
        this.hpBar = new hpBar();

        this.objects.push( this.player );
        this.ui.push( this.hpBar );

        this.bullets = 0;
        this.zombies = 0;
        this.killedZombies = 0;
        this.killsMultiplier = 15;
    }

    spawnWave(){

        for (var i = 0; i < this.wave.number * this.wave.zpw; i++){
            var randX = Math.floor((Math.random() * 1700) - 400);

            if (randX < -100 || randX > 1000){
                var randY = Math.floor((Math.random() * 1400) - 400);
            } 
            else {
                var rand = Math.floor((Math.random() * 2));

                if (rand) 
                    var randY = Math.floor((Math.random() * 400) + 900);
                else 
                    var randY = Math.floor(-(Math.random() * 100) - 400);
            } 

            this.wave.zombies++;

            var id = ++this.zombies;
            var zombie = new Zombie("z"+id ,randX, randY);

            this.objects.push(zombie);
        }

        this.wave.number++; 

    }


    checkCollision(scene, object) {
        for (var i in scene) {
            var obj = scene[i];   

            if (obj == object || !obj.physical) continue;
            
            var test =
            object.x >= obj.x + obj.width ||
            object.x + object.width <= obj.x ||
            object.y >= obj.y + obj.height ||
            object.y + object.height <= obj.y;
            
            if (!test) {
                return obj;
            }
        }
        return false;
    }

    update(dt) {
        var i = 0;
        
        if (this.wave.zombies == 0)
            this.spawnWave(this.wave);

        this.objects.forEach((object) => {
            var col = this.checkCollision(this.objects, object);


            if (object.constructor.name == 'Zombie'){
                object.targetPos = [ this.player.center[0], this.player.center[1] ];

                if (object.hp <= 0){
                    this.objects = this.objects.filter((obj) => obj.id != object.id);
                    this.wave.zombies--;
                    this.killedZombies++;

                    //doplnanie HP
                    if (this.killedZombies % this.killsMultiplier == 0) {
                        console.log(this.killsMultiplier);
                        this.killsMultiplier += 15; 

                        if (this.player.hp <= 80)
                            this.player.hp += 20;
                        else 
                            this.player.hp = 100;

                        //console.log(this.player.hp);
                    }

                    var sound = resourceManager.getSoundSource('zombie_death');
                    sound.volume = 0.2;
                    sound.load();
                    sound.play();
                }
            }

            if (object.constructor.name == 'Bullet'){
                i++;

                if (object.x > canvas.width || object.x < 0 || object.y > canvas.height || object.y < 0)
                    this.objects = this.objects.filter((obj) => obj.id != object.id);

                if (col.constructor.name == 'Zombie'){
                    var sound = resourceManager.getSoundSource('zombie_damage');
                    sound.volume = 0.2;
                    sound.play();

                    this.objects = this.objects.filter((obj) => obj.id != object.id);
                    col.hp -= 50;
                }
            }

            if (object.constructor.name == 'Player'){
                if (col.constructor.name == 'Zombie'){
                    object.hp -= 1/3;

                    var sound = resourceManager.getSoundSource('zombie_bite');
                    sound.volume = 0.2;
                    sound.play();

                    var sound = resourceManager.getSoundSource('damage');
                    sound.volume = 0.3;
                    sound.play();
                }

                if (object.hp <= 0){

                    var sound = resourceManager.getSoundSource('death');
                    sound.volume = 0.3;
                    sound.play();

                    var sound = resourceManager.getSoundSource('death_music');
                    sound.volume = 0.3;
                    sound.play();
                    this.stateManager.changeState(STATES.GAME_OVER);
                }
            }

            object.move(dt);
        });
    }

    render(ctx) {
        this.ctx.drawImage(this.bgImage,0,0,canvas.width,canvas.height);
        this.objects.forEach(object => object.render(this.ctx));

        this.ui.forEach(object => object.render(this.ctx));
        this.ctx.fillStyle = "black";
        this.ctx.fillText("KILLS: " + this.killedZombies, 700, 75);


        this.hpBar.hp = this.player.hp;
    }

    handleEvent(ev) {
        this.objects.forEach((object) => {
            object.handleEvent(ev);
        });

        if (isKeyPressEvent(ev) && ev.key === 'p') {
            this.stateManager.changeState(STATES.PAUSE);
        }

        if (ev.type == 'click'){
            var id = ++this.bullets;

            var a = 13, b = 50;
            var c = Math.sqrt( Math.pow(a,2) + Math.pow(b,2));

            var subAngle = Math.asin(a/c);
            var angle = this.player.angle + subAngle;

            var bulletX = 50 * Math.sin(angle);
            var bulletY = 50 * Math.cos(angle);

            var bullet = new Bullet("b"+id, this.player.center[0], this.player.center[1], bulletX, bulletY);

          
            bullet.targetPos = [ev.clientX, ev.clientY];
            this.objects.push(bullet);

            var sound = resourceManager.getSoundSource('shot');
            sound.volume = 0.05; 
            sound.load();
            sound.play();
        }
    }
}

class MainMenu extends BaseState {
    constructor(manager, ctx) {
        super(manager, ctx);

        var music = resourceManager.getSoundSource('music');
        music.volume = 0.05;

        const mainMenuBackground = new MenuBackground(0, 0, canvas.width, canvas.height, resourceManager.getImageSource('menu_screen'));
        
        const startGameButton = new ImageButton(canvas.width/2-300/2, 250, 300, 70, resourceManager.getImageSource('play_button'));
        startGameButton.onClick((ev) => {
            music.play();
            this.stateManager.changeState(STATES.GAME);
        });


        const infoButton = new ImageButton(canvas.width/2-300/2, 350, 300, 70, resourceManager.getImageSource('instructions_button'));
        infoButton.onClick((ev) => {
            this.stateManager.changeState(STATES.INFO);
        });

        const settingsButton = new ImageButton(canvas.width/2-300/2, 450, 300, 70, resourceManager.getImageSource('settings_button'));
        settingsButton.onClick((ev) => {
            this.stateManager.changeState(STATES.SETTINGS);
        });

        this.objects = [
            mainMenuBackground,
            startGameButton,
            infoButton,
            settingsButton,
        ];
    }

    handleEvent(ev) {
        this.objects.forEach((object) => {
            object.handleEvent(ev);
        });

        if (isKeyPressEvent(ev) && ev.key === 'g') {
            this.stateManager.changeState(STATES.GAME);
        }
    }
}


class SettingsState extends BaseState {
    constructor(manager, ctx) {
        super(manager, ctx);
        const canvas = document.getElementById("canvas");

        var musicPlay = 0;
        var music = resourceManager.getSoundSource('music');
        music.volume = 0.05;

        const settingsMenuBackground = new MenuBackground(0, 0, canvas.width, canvas.height, resourceManager.getImageSource('settings_screen'));

        const muteButton = new ImageButton(canvas.width/2-325/2, 250, 325, 70, resourceManager.getImageSource('music_button'));
        muteButton.onClick((ev) => {
            if (musicPlay == 0){
                music.play();
                musicPlay = 1;
            }
            else {
                music.pause();
                musicPlay = 0;
            }
        });

        const backButton = new ImageButton(canvas.width/2-315/2, 350, 300, 70, resourceManager.getImageSource('back_button'));
        backButton.onClick((ev) => {
            this.stateManager.changeState(STATES.MAIN_MENU);
        });

        this.objects = [
            settingsMenuBackground,
            muteButton,
            backButton,
        ];

    }

    handleEvent(ev) {
        this.objects.forEach((object) => {
            object.handleEvent(ev);
        });
    }
}



class InfoState extends BaseState {
    constructor(manager, ctx) {
        super(manager, ctx);
        const canvas = document.getElementById("canvas");

        const instructionsBackground = new MenuBackground(0, 0, canvas.width, canvas.height, resourceManager.getImageSource('instructions_screen'));

        const backButton = new ImageButton(canvas.width/2-300/2, 500, 300, 70, resourceManager.getImageSource('back_button'));
        backButton.onClick((ev) => {
            this.stateManager.changeState(STATES.MAIN_MENU);
        });

        this.objects = [
            instructionsBackground,
            backButton,
        ];

    }

    handleEvent(ev) {
        this.objects.forEach((object) => {
            object.handleEvent(ev);
        });
    }
}

class GameOverState extends BaseState {
    constructor(manager, ctx) {
        super(manager, ctx);
        const canvas = document.getElementById("canvas");

        var music = resourceManager.getSoundSource('music');
        music.volume = 0.05;

        const gameOverBackground = new MenuBackground(0, 0, canvas.width, canvas.height, resourceManager.getImageSource('game_over_screen'));

        const tryAgainButton = new ImageButton(canvas.width/2-270, 355, 300, 70, resourceManager.getImageSource('try_again_button'));
        tryAgainButton.onClick((ev) => {
            this.stateManager.states.gameState.reset();
            this.stateManager.changeState(STATES.GAME);
        });

        const mainMenuButton = new ImageButton(canvas.width/2-15, 355, 300, 70, resourceManager.getImageSource('main_menu_button'));
        mainMenuButton.onClick((ev) => {
            music.pause();
            music.currentTime = 0;
            this.stateManager.states.gameState.reset();
            this.stateManager.changeState(STATES.MAIN_MENU);
        });

        this.objects = [
            gameOverBackground,
            tryAgainButton,
            mainMenuButton,
        ];
    }

    handleEvent(ev) {
        this.objects.forEach((object) => {
            object.handleEvent(ev);
        });
    }

    render(ctx) {
        this.objects.forEach(object => object.render(this.ctx));
        this.ctx.fillStyle = "#980002";
        this.ctx.fillText("TOTAL KILLS: " + this.stateManager.states.gameState.killedZombies, 450, 75);
    }
}

class PauseState extends BaseState {
    constructor(manager, ctx) {
        super(manager, ctx);
        const canvas = document.getElementById("canvas");

        var musicPlay //= 0;
        var music = resourceManager.getSoundSource('music');
        music.volume = 0.05;

        const pauseBackground = new MenuBackground(0, 0, canvas.width, canvas.height, resourceManager.getImageSource('pause_screen'));
        
        const continueButton = new ImageButton(canvas.width/2-300/2, 250, 300, 70, resourceManager.getImageSource('continue_button'));
        continueButton.onClick((ev) => {
            this.stateManager.changeState(STATES.GAME);
        });

        const muteButton = new ImageButton(canvas.width/2-325/2, 350, 325, 70, resourceManager.getImageSource('music_button'));
        muteButton.onClick((ev) => {
            if (musicPlay == 0){
                music.play();
                musicPlay = 1;
            }
            else {
                music.pause();
                musicPlay = 0;
            }
        });

        const quitButton = new ImageButton(canvas.width/2-300/2, 450, 300, 70, resourceManager.getImageSource('quit_button'));
        quitButton.onClick((ev) => {
            music.pause();
            music.currentTime = 0;
            this.stateManager.states.gameState.reset();
            this.stateManager.changeState(STATES.MAIN_MENU);
        });

        this.objects = [
            pauseBackground,
            continueButton,
            muteButton,
            quitButton,
        ];
    }

    handleEvent(ev) {
        this.objects.forEach((object) => {
            object.handleEvent(ev);
        });
    }
}