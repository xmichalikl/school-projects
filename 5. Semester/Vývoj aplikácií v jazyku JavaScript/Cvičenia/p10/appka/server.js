// toto bude light web server
const http = require('http');
const fs = require('fs');
const mysql = require('mysql');
const port = 8080;

let connectionMade = false;
let connection = null;
function connectDB() {
    if(connectionMade===false) {
        connection = mysql.createConnection({
            host     : 'mydb',
            user     : 'root',
            password : 'root',
            database : 'obrazky'
        });
        connection.connect(err =>{
            if(!err) {
                connectionMade = true;
            }
        });
    }
}

const server = http.createServer(function(req,res){
    console.log(req.method+' request received for '+req.url);
    if(req.url.indexOf('/index.html')===0) {
        res.statusCode = 200; // HTTP OK
        res.setHeader('Content-Type', 'text/html');
        res.setHeader('Access-Control-Allow-Origin','*');
        fs.readFile('static/index.html','utf-8',(err,data)=>{
            if(err) {
                res.statusCode = 500;
                res.end('<html><body><h1>FS ERROR</h1><p>'+error+'</p></body></html>');
            }
            else {
                res.end(data);
            }
        });
        
    }
    else if(req.url.indexOf('/bundle.js')===0) {
        res.statusCode = 200; // HTTP OK
        res.setHeader('Content-Type', 'application/javascript');
        res.setHeader('Access-Control-Allow-Origin','*');
        fs.readFile('static/bundle.js','utf-8',(err,data)=>{
            if(err) {
                res.statusCode = 500;
                res.end('// error');
            }
            else {
                res.end(data);
            }
        });
    }
    else if(req.url.indexOf('/data')===0) {
        if(req.method==='POST') {
            req.on('data', data => {
                let change = JSON.parse(data);
                const id = change.id;
                const score = change.score;
                connectDB();
                connection.query('UPDATE obrazky SET score='+score+' WHERE id='+id+';', (error, results, fields) => {
                    if (error) {
                        res.statusCode = 500;
                        res.end('{"status": "error", "error": "'+error+'"}');
                    }
                    res.end('{"status": "ok"}');
                });
            });
        }
        else if(req.method==='GET') {
            connectDB();
            res.statusCode = 200; // HTTP OK
            res.setHeader('Content-Type', 'application/json');
            res.setHeader('Access-Control-Allow-Origin','*');
            connection.query('SELECT * FROM obrazky', (error, results, fields) => {
                if (error) {
                    res.statusCode = 500;
                    res.end('{"status": "error", "error": "'+error+'"}');
                }
                res.end(JSON.stringify(results));
            });
        }
        else {
            res.end('error');
        }
    }
});

server.listen(port,function(){
    console.log('Server running v6');
});