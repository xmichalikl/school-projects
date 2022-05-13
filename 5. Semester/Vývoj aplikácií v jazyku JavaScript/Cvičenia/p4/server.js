// toto bude light web server
const http = require('http');
const hostname = '127.0.0.1';
const port = 3000;  // do 1080

let counter = 0;
const server = http.createServer(function(req,res){
    if(req.url.indexOf('/') === 0) {
        res.statusCode = 200; // HTTP OK
        res.setHeader('Content-Type', 'application/json');
        res.setHeader('Access-Control-Allow-Origin','*');
        res.end('{"counter": '+counter+'}');
        counter++;
    }
    else {
        res.statusCode = 400;
        res.end();
    }
});

server.listen(port,hostname,function(){
    console.log('Server running v3');
});