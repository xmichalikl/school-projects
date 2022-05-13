// toto bude light web server
const http = require('http');
const hostname = '127.0.0.1';
const port = 3000;  // do 1080
const fs = require('fs');

const WebSocket = require('ws');
const wsServer = new WebSocket.Server({port: 3001});

const debug = process.argv[2] === 'debug';
console.log('debug is: '+debug);

function conlog(msg) {
    if(debug) console.log(msg);
}

let db = [
    {
        meno: 'Ferko',
        priezvisko: 'Mrkvicka',
        login: 'fmrkvicka',
        counter: 0
    },
    {
        meno: 'Janko',
        priezvisko: 'Jablcko',
        login: 'jjablcko',
        counter: 0
    },
    {
        meno: 'Marienka',
        priezvisko: 'Jablckova',
        login: 'mmarienka',
        counter: 0
    }
];

// poorMansQueryParser
// parses url with ?, & and key=value pairs
// @param url string after /
// @returns object, key is object[key] = value
function poorMansQueryParser(url) {
    let queryParams = null;
    let queryParamsParts = [];
    let queryObject = {};

    if(url.indexOf('?') >=0 ) {
        queryParams = url.split('?')[1];
        conlog(queryParams);
        if(queryParams.indexOf('&')>=0) {
            queryParamsParts = queryParams.split('&');
        }
        else queryParamsParts = [queryParams];
        conlog(queryParamsParts);
        queryParamsParts.forEach(qp => {
            if(qp.indexOf('=') >= 0) {
                let qpParts = qp.split('=');
                queryObject[qpParts[0]] = qpParts[1];
            }
            else queryObject[qp] = true;
        });
        conlog(queryObject);
    }
    return queryObject;
}

// getUserIndex
// @param login string, user login name
// @returns null or integer representing address in array of db
const getUserIndex = (login) => {
    for(let i=0;i<db.length;i++) {
        if(db[i].login===login) return i;
    }
    return null;
};

const server = http.createServer(function(req,res){
    let queryObject = poorMansQueryParser(req.url);
    // index.html
    if(req.url.indexOf('/index.html')===0) {
        res.statusCode = 200; // HTTP OK
        res.setHeader('Content-Type', 'text/html');
        res.setHeader('Access-Control-Allow-Origin','*');
        fs.readFile('static/index.html','utf-8',(err,data)=>{
            if(err) {
                res.statusCode = 500;
                res.end('<html><body><h1>FS ERROR</h1></body></html>');
            }
            else {
                res.end(data.replace('{qp}',queryObject['data']));
            }
        });
        
    }
    // data interface
    else if(req.url.indexOf('/data')===0) {
        let login = null;
        if(typeof queryObject['login'] !== 'undefined') login = queryObject['login'];
        conlog(req.headers.cookie);
        const userIndex = getUserIndex(login);
        if(userIndex !== null) {
            if(db[userIndex].counter === 0) {
                conlog('counter 0, incrementing')
                setInterval(()=>{
                    db[userIndex].counter++;
                },2000);
            }
            else {
                conlog('counter is set');
            }
        }
        else {
            conlog('userIndex is null');
        }
        res.statusCode = 200; // HTTP OK
        res.setHeader('Content-Type', 'application/json');
        res.setHeader('Access-Control-Allow-Origin','*');
        res.end('{"counter": '+db[userIndex].counter+'}');
    }
    // error
    else {
        res.statusCode = 400;
        res.end();
    }
});

server.listen(port,hostname,function(){
    conlog('Server running v3');
});

let counterStatus = 0;
/*wsServer.on('connection', (ws)=>{
    (function(counterStatus) {
        setInterval(()=>{
            if(typeof counter[counterStatus] === 'undefined') {
                counter[counterStatus] = 0;
            }
            else {
                counter[counterStatus]++;
            }
            ws.send(JSON.stringify({'counter':counter[counterStatus]}));
        },2000);
    })(counterStatus);
    counterStatus++;
});*/
