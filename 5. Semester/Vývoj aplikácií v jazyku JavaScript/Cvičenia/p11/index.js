const { app, BrowserWindow, ipcMain, dialog } = require('electron');
const path = require('path');

const createWindow = () => {
    const win = new BrowserWindow({
        width: 800,
        height: 600,
        webPreferences: {
            preload: path.join(__dirname, 'preload.js'),
            nodeIntegration: true,
            contextIsolation: false
        }
    });
    win.webContents.openDevTools();
    win.loadFile('index.html');
    win.setMenu(null);
}

app.whenReady().then(() => {
    createWindow();
    app.on('activate', () => {
        if (BrowserWindow.getAllWindows().length === 0) createWindow()
    });
});

ipcMain.on('confirm',(event,arg)=>{
    console.log('confirmed: ');
    console.log(arg);
    const res = dialog.showMessageBoxSync({message: "Confirm?", buttons: ["confirm","no confirm"]});
    if(res === 0) dialog.showMessageBox({message: "Confirming your confirmation", buttons: ["get me out of here"]});
    else dialog.showMessageBox({message: "Confirming your are unhappy", buttons: ["leave me alone"]});
});

app.on('window-all-closed', () => {
    if (process.platform !== 'darwin') app.quit()
});
