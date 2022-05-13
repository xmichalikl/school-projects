console.log("Resource Manager Loaded");
class ResourceManager {
    loadedImages = new Map();
    loadedSounds = new Map();

    async init(IMAGES, SOUNDS) {
        console.log('IDEM INIT RES');
        await this.loadImages(IMAGES);
        await this.loadSounds(SOUNDS);
        console.log("ALL Complente");
    }

    async loadSounds(SOUNDS) {
        await Promise.all(
            SOUNDS.map(sound => this.loadSound(sound)),
        )
    }

    async loadImages(IMAGES) {
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
            throw new Error(`Image '${imageName}' not found`);
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
//const resourceManager = new ResourceManager();
export default ResourceManager;