// preload.js
const { fstat } = require("original-fs");
const fs = require('fs');
const readline = require('readline');

// All of the Node.js APIs are available in the preload process.
// It has the same sandbox as a Chrome extension.
window.addEventListener('DOMContentLoaded', () => {
    const replaceText = (selector, text) => {
        const element = document.getElementById(selector)
        if (element) element.innerText = text
    }
  
    for (const dependency of ['chrome', 'node', 'electron']) {
        replaceText(`${dependency}-version`, process.versions[dependency])
    }

    const readInterface = readline.createInterface({
        input: fs.createReadStream('lorem.txt'),
        output: process.stdout,
        console: false
    });
    
    const loremContainer = document.querySelector('.lorem .col');
    readInterface.on('line', function(line) {
        loremContainer.innerHTML += '<p>'+line+'</p>';
    });
    /*fs.readFile('lorem.txt',(err,data)=>{
        if(err) console.log(err);
        else {
            const loremContainer = document.querySelector('.lorem');
            data = data.replaceAll('\n','</p><p>');
            data = '<p>'+data+'</p>';
            loremContainer.innerHTML = data;
        }
    });
    */
});