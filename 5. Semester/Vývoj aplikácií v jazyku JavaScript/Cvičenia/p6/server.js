// toto bude light web server
const http = require('http');
//const hostname = 'localhost';
const port = 8080;  // do 1080
const fs = require('fs');
const mysql = require('mysql');

const server = http.createServer(function(req,res){
    console.log('request received');
    if(req.url.indexOf('/index.html')===0) {
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
    else if(req.url.indexOf('/data')===0) {
        var connection = mysql.createConnection({
            host     : 'mydb',
            user     : 'root',
            password : 'root',
            database : 'produkty'
        });
        connection.connect();
        console.log('request /data');
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
});

server.listen(port,function(){
    console.log('Server running v6');
});