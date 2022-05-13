const IMAGES = [
    //objects
    {name: 'player', src: 'assets/player.png'},
    {name: 'zombie', src: 'assets/zombie.png'},
    {name: 'zombie_animation', src: 'assets/zombie_animation.png'},
    {name: 'hp', src: 'assets/hp.png'},
    {name: 'bullet', src: 'assets/bullet.png'},
    {name: 'crosshair', src: 'assets/crosshair-small.png'},

    //backgrounds
    {name: 'background', src: 'assets/background.png'},
    {name: 'menu_screen', src: 'assets/screens/main_menu.png'},
    {name: 'instructions_screen', src: 'assets/screens/instructions_menu.png'},
    {name: 'settings_screen', src: 'assets/screens/settings_menu.png'},
    {name: 'game_over_screen', src: 'assets/screens/game_over_screen.png'},
    {name: 'pause_screen', src: 'assets/screens/pause_menu.png'},

    //buttons
    {name: 'play_button', src: 'assets/buttons/play_button.png'},
    {name: 'instructions_button', src: 'assets/buttons/instructions_button.png'},
    {name: 'settings_button', src: 'assets/buttons/settings_button.png'},
    {name: 'back_button', src: 'assets/buttons/back_button.png'},
    {name: 'music_button', src: 'assets/buttons/music_button.png'},
    {name: 'main_menu_button', src: 'assets/buttons/main_menu_button.png'},
    {name: 'try_again_button', src: 'assets/buttons/try_again_button.png'},
    {name: 'continue_button', src: 'assets/buttons/continue_button.png'},
    {name: 'quit_button', src: 'assets/buttons/quit_button.png'},
];

const SOUNDS = [
    {name: 'music', src: 'audio/music.mp3'},
    {name: 'shot', src: 'audio/shot.mp3'},
    {name: 'damage', src: 'audio/damage.mp3'},
    {name: 'death', src: 'audio/death.mp3'},
    {name: 'death_music', src: 'audio/death_music.mp3'},
    {name: 'zombie', src: 'audio/zombie.mp3'},
    {name: 'zombie_move', src: 'audio/zombie_move.mp3'},    
    {name: 'zombie_bite', src: 'audio/zombie_bite.mp3'},
    {name: 'zombie_damage', src: 'audio/zombie_damage.mp3'},
    {name: 'zombie_death', src: 'audio/zombie_death.mp3'},
 
];


const KEY_EVENT_TYPES = {};
const MOUSE_EVENT_TYPES = {};

class Game {
    constructor() {
        // Set up canvas for 2D rendering
        this.canvas = document.getElementById("canvas");
        this.ctx = canvas.getContext("2d");
        this.time = Date.now();

        this.ctx.font = "50px Road Rage";
        this.ctx.textAlign = "center";

        this.stateManager = new StateManager(resourceManager, this.ctx);
    }

    async start() {
        console.log('starting game');
        await resourceManager.init();
        console.log('resouces loaded');
        this.stateManager.init();
        this.initEventSystem();
        this.startLoop();
    }

    initEventSystem() {
        this.canvas.addEventListener('click', (ev) => {
            this.handleEvent(ev);
        });
        this.canvas.addEventListener('keypress', (ev) => {
            this.handleEvent(ev);
        });
        this.canvas.addEventListener('keydown', (ev) => {
            this.handleEvent(ev);
        });
        this.canvas.addEventListener('keyup', (ev) => {
            this.handleEvent(ev);
        });
        this.canvas.addEventListener('mousemove', (ev) => {
            this.handleEvent(ev);
        });
    }

    handleEvent(ev) {
        this.stateManager.handleEvent(ev);
    }

    startLoop() {
        this.time = Date.now();
        this.step();
    }

    step() {
        const now = Date.now();
        const dt = (now - this.time) / 100; // dt nadobuda hodnoty <0.15;az 0.33> 
        this.time = now;
      
        this.update(dt);
        this.render(dt);
      
        requestAnimationFrame(() => this.step());
    }

    update(dt) {
        this.stateManager.update(dt);
    }

    render(dt) {
        this.clearCtx();
        this.stateManager.render(dt);
    }

    clearCtx() {
        this.ctx.fillStyle = "white";
        this.ctx.fillRect(0, 0, this.canvas.width, this.canvas.height);
    }
}