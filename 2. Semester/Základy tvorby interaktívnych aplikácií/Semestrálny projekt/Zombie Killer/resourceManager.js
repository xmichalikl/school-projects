class ResourceManager {
    loadedImages = new Map();
    loadedSounds = new Map();

    async init() {
        await this.loadImages();
        await this.loadSounds();
    }

    async loadSounds() {
        
        await Promise.all(
            SOUNDS.map(sound => this.loadSound(sound)),
        )
    }

    async loadImages() {
        await Promise.all(
            IMAGES.map(image => this.loadImage(image)),
        )
    }

    async loadImage(imgResource) {
        return new Promise((resolve, reject) => {
            const img = new Image();
            img.src = imgResource.src;
            img.onload = () => {
                this.loadedImages.set(imgResource.name, img);
                resolve(img);
            }
            img.onerror = (err) => {
                reject(err);
            }
        });
    }

    async loadSound(imgResource) {
        return new Promise((resolve, reject) => {
            const sound = new Audio(imgResource.src);
            sound.oncanplaythrough = () => {
                this.loadedSounds.set(imgResource.name, sound);
                resolve(sound);
            }
            sound.onerror = (err) => {
                reject(err);
            }
        });
    }

    getImageSource(imageName) {
        const image = this.loadedImages.get(imageName);
        if (image == null) {
            throw new Error(`Image '${imageName}' not foundd`);
        }
        return image;
    }


    getSoundSource(soundName) {
        const sound = this.loadedSounds.get(soundName);
        if (sound == null) {
            throw new Error(`Sound '${soundName}' not found`);
        }
        return sound;
    }
}

const resourceManager = new ResourceManager();