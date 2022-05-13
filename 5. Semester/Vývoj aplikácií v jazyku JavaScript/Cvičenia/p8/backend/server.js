// toto bude light web server
const http = require('http');
const port = 8080;  // do 1080
const fs = require('fs');
const mysql = require('mysql');

let connectionMade = false;
let connection = null;
function connectDB() {
    if(connectionMade===false) {
        connection = mysql.createConnection({
            host     : 'mydb',
            user     : 'root',
            password : 'root',
            database : 'produkty'
        });
        connection.connect(err =>{
            if(!err) {
                connectionMade = true;
            }
        });
    }
}

const server = http.createServer(function(req,res){
    console.log(req.method+' request received');
    if(req.method==='GET') {
        if(req.url === '/' || req.url.indexOf('/index.html') >= 0) {
            console.log('request /index.html');
            res.statusCode = 200; // HTTP OK
            res.setHeader('Content-Type', 'text/html');
            res.setHeader('Access-Control-Allow-Origin','*');
            fs.readFile('static/index.html','utf-8',(err,data)=>{
                if(err) {
                    res.statusCode = 500;
                    res.end('<html><body><h1>FS ERROR</h1></body></html>');
                }
                else {
                    res.end(data);
                }
            });
        }
        else if(req.url.indexOf('/bundle.js') >= 0) {
            console.log('request /bundle.js');
            res.statusCode = 200; // HTTP OK
            res.setHeader('Content-Type', 'text/html');
            res.setHeader('Access-Control-Allow-Origin','*');
            fs.readFile('static/bundle.js','utf-8',(err,data)=>{
                if(err) {
                    res.statusCode = 500;
                    res.end('<html><body><h1>FS ERROR</h1></body></html>');
                }
                else {
                    res.end(data);
                }
            });
        }
        else if(req.url.indexOf('/data')===0) {
            console.log('request /data');
            connectDB();
            res.statusCode = 200; // HTTP OK
            res.setHeader('Content-Type', 'application/json');
            res.setHeader('Access-Control-Allow-Origin','*');
            connection.query('SELECT * FROM tprodukty', function (error, results, fields) {
                if (error) {
                    res.statusCode = 500;
                    res.end('<html><body><h1>DB ERROR</h1></body></html>');
                }
                console.log(results);
                res.end(JSON.stringify(results));
            });
        }
        else {
            console.log('request /error');
            res.statusCode = 400;
            res.end();
        }
    }
    else if(req.method==='POST') {
        console.log('POST received');
        connectDB();
        if(req.url.indexOf('/data')===0) {
            req.on('data', function(data){
                console.log('data: '+data);
                let input = JSON.parse(data).input;
                console.log('input: '+input);
                res.setHeader('Content-Type', 'application/json');
                connection.query('INSERT INTO tprodukty (nazov) VALUES (\''+input+'\');', function (error, results) {
                    if (error) {
                        res.statusCode = 500;
                        res.end(JSON.stringify({'status': 'error'}));
                    }
                    console.log('ending: ');
                    console.log(res);
                    res.statusCode = 200;
                    res.end(JSON.stringify({'status': 'ok'}));
                });
            });
        }
    }
    else if(req.method==='OPTIONS') {
        res.statusCode = 200;
        res.setHeader('Access-Control-Allow-Origin','*');
        res.end();
    }
});

server.listen(port,function(){
    console.log('Server running v6');
});