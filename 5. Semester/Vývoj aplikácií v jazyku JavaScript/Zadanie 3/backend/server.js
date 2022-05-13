const express = require('express');
const app = express();
const port = 8080;

const mysql = require('mysql');
const path = require("path");
const fs = require('fs');

let dbConnected = false;
let dbConnection = null;

//------------------- DATABASE CONECTION -------------------
function connectToDatabase() {
    if (dbConnected === false) {
        dbConnection = mysql.createConnection({
            host     : 'mydb',
            user     : 'root',
            password : 'root',
            database : 'eshop'
        });
        dbConnection.connect(err =>{
            if (!err) {
                dbConnected = true;
                console.log("Database connected!");
            }
        });
    }
}

// Dekoduje JSON string order info, na citatelne info
// napr. vstup [{"id":1,"count":3}] na Hrable: 3x
function getProductsNames(orderStr){
    return new Promise((resolve ,reject)=>{
        let order = JSON.parse(orderStr);
        let idStr = '';

        order.forEach(function(item, idx, array) {
            idStr = idStr.concat(item.id);
            if (idx < array.length - 1){ 
                idStr = idStr.concat(', ');
            }
        });

        let select = 'SELECT * FROM products WHERE id IN ('+idStr+');'
        dbConnection.query(select, function (error, results, fields) {
            if (error) {
                reject('ERR');
            }
            else {
                let productsStr = '';
                results.forEach(function(item, idx, array) {
                    productsStr = productsStr.concat(item.name+': '+order[idx].count+'x');
                    if (idx < array.length - 1){ 
                        productsStr = productsStr.concat(', ');
                    }
                });
                resolve(productsStr);
            } 
        });
    }); 
}

app.use(express.urlencoded());
app.use(express.json());
//----------------------- GET PRODUCTS -----------------------
app.get('/get-products', function (req, res){
    console.log('GET REQUEST -> get-products');
    connectToDatabase();
    res.status(200);
    res.setHeader('Content-Type', 'application/json');
    res.setHeader('Access-Control-Allow-Origin','*');
    dbConnection.query('SELECT * FROM products', function (error, results, fields) {
        if (error) {
            console.log('Dtatabase error');
            res.status(500);
            res.send('<html><body><h1>DB ERROR</h1></body></html>');
        }
        res.send(JSON.stringify(results));
    });
});
//----------------------- GET ORDERS -----------------------
app.get('/get-orders', function (req, res){
    console.log('GET REQUEST -> get-orders');
    connectToDatabase();
    res.status(200);
    res.setHeader('Content-Type', 'application/json');
    res.setHeader('Access-Control-Allow-Origin','*');
    dbConnection.query('SELECT * FROM orders', function (error, results, fields) {
        if (error) {
            console.log('Dtatabase error');
            res.status(500);
            res.send('<html><body><h1>DB ERROR</h1></body></html>');
        }
        res.send(JSON.stringify(results));
    });
});
//----------------------- GET COUNTER -----------------------
app.get('/get-counter', function (req, res){
    console.log('GET REQUEST -> get-counter');
    connectToDatabase();
    res.status(200);
    res.setHeader('Content-Type', 'application/json');
    res.setHeader('Access-Control-Allow-Origin','*');
    dbConnection.query('SELECT counter FROM advertisement WHERE `id` = \'1\';', function (error, results, fields) {
        if (error) {
            console.log('Dtatabase error');
            res.status(500);
            res.send('<html><body><h1>DB ERROR</h1></body></html>');
        }
        res.send(JSON.stringify(results));
    });
});
//----------------------- GET AD -----------------------
app.get('/get-advertisement', function (req, res){
    console.log('GET REQUEST -> get-advertisement');
    connectToDatabase();
    res.status(200);
    res.setHeader('Content-Type', 'application/json');
    res.setHeader('Access-Control-Allow-Origin','*');
    dbConnection.query('SELECT * FROM advertisement', function (error, results, fields) {
        if (error) {
            console.log('Dtatabase error');
            res.status(500);
            res.send('<html><body><h1>DB ERROR</h1></body></html>');
        }
        res.send(JSON.stringify(results));
    });
});
//----------------------- UPDATE COUNTER -----------------------
app.get('/ad-clicked', function (req, res){
    console.log('GET REQUEST -> ad-clicked');
    connectToDatabase();
    res.status(200);
    res.setHeader('Content-Type', 'application/json');
    res.setHeader('Access-Control-Allow-Origin','*');
    let update = 'UPDATE `advertisement` SET `counter` = counter + \'1\' WHERE `id` = \'1\' LIMIT 1;';
    dbConnection.query(update, function (error, results) {
        if (error) {
            console.log('Dtatabase error');
            res.status(500);
            res.send('<html><body><h1>DB ERROR</h1></body></html>');
        }
    });
});
//----------------------- CREATE ORDER -----------------------
app.post('/create-order', function (req, res){
    console.log('POST REQUEST -> create-order');
    connectToDatabase();
    res.status(200);
    res.setHeader('Content-Type', 'application/json');
    res.setHeader('Access-Control-Allow-Origin','*');

    let orderData = req.body;
    let insert = 'INSERT INTO `customers` (`name`, `surname`, `email`, `number`, `address`, `city`, `postcode`) ';
    let values = 'VALUES (\''+orderData.name+'\', \''+orderData.surname+'\', \''+orderData.email+'\', \''+orderData.number+'\', \''+orderData.address+'\', \''+orderData.city+'\', \''+orderData.postcode+'\');'
    dbConnection.query(insert+values, function (error, results) {   
        if (error) {
            console.log('Dtatabase error');
            res.status(500);
            res.send('<html><body><h1>DB ERROR</h1></body></html>');
        }
        else {
            let customer_id = results.insertId;
            let state = 'Nevybavená';
            
            getProductsNames(orderData.order).then((products)=>{
                let insert2 = 'INSERT INTO `orders` (`products`, `price`, `customer_id`, `state`) ';
                let values2 = 'VALUES (\''+products+'\', \''+orderData.price+'\', \''+customer_id+'\', \''+state+'\');'
                dbConnection.query(insert2+values2, function (error, results) {
                    if (error) {
                        console.log('Dtatabase error');
                        res.status(500);
                        res.send('<html><body><h1>DB ERROR</h1></body></html>');
                    }
                    else {
                        res.redirect('http://localhost:8080/ad');
                    }
                });
            }).catch((err)=>{
                let insert2 = 'INSERT INTO `orders` (`products`, `price`, `customer_id`, `state`) ';
                let values2 = 'VALUES (\''+orderData.order+'\', \''+orderData.price+'\', \''+customer_id+'\', \''+state+'\');'
                dbConnection.query(insert2+values2, function (error, results) {
                    if (error) {
                        console.log('Dtatabase error');
                        res.status(500);
                        res.send('<html><body><h1>DB ERROR</h1></body></html>');
                    }
                    else {
                        res.redirect('http://localhost:8080/ad');
                    }
                });
            });
        }
    });
});
//----------------------- UPDATE ORDER -----------------------
app.post('/update-order', function (req, res){
    console.log('POST REQUEST -> update-order');
    connectToDatabase();
    res.status(200);
    res.setHeader('Content-Type', 'application/json');
    res.setHeader('Access-Control-Allow-Origin','*');
    
    req.on('data', function(data){
        let orderId = JSON.parse(data).id;
        let update = 'UPDATE `orders` SET `state` = \'Zaplatená\' WHERE `id` = \''+orderId+'\' LIMIT 1;';
        dbConnection.query(update, function (error, results) {   
            if (error) {
                console.log('Dtatabase error');
                res.status(500);
                res.send('<html><body><h1>DB ERROR</h1></body></html>');
            }
            res.send(JSON.stringify({'status': 'ok'}));
        });
    });
});
//----------------------- UPDATE AD -----------------------
app.post('/update-ad', function (req, res){
    console.log('POST REQUEST -> update-ad');
    connectToDatabase();
    res.status(200);
    res.setHeader('Content-Type', 'application/json');
    res.setHeader('Access-Control-Allow-Origin','*');
    
    let adImg = req.body.img;
    let adLink = req.body.link;
    
    let updateImg = 'UPDATE `advertisement` SET `img` = \''+adImg+'\' WHERE `id` = \'1\' LIMIT 1; ';
    let updateLink = 'UPDATE `advertisement` SET `link` = \''+adLink+'\' WHERE `id` = \'1\' LIMIT 1; ';    

    if (adImg.length > 0) {
        dbConnection.query(updateImg, function (error, results) {   
            if (error) {
                console.log('Dtatabase error');
                res.status(500);
                res.send('<html><body><h1>DB ERROR</h1></body></html>');
            }
        });
    }
    if (adLink.length > 0) {
        dbConnection.query(updateLink, function (error, results) {   
            if (error) {
                console.log('Dtatabase error');
                res.status(500);
                res.send('<html><body><h1>DB ERROR</h1></body></html>');
            }
        });
    }
    res.redirect('http://localhost:8080/ad');
});
//----------------------- GET FILES -----------------------
app.get('/bundle.js', function (req, res) {
    console.log('GET REQUEST -> bundle.js');
    res.status(200);
    res.setHeader('Content-Type', 'text/javascript');
    res.setHeader('Access-Control-Allow-Origin','*');
    res.sendFile(path.resolve('static/bundle.js'));
});
app.get('/*', function (req, res) {
    console.log('GET REQUEST -> index.html');
    res.status(200);
    res.setHeader('Content-Type', 'text/html');
    res.setHeader('Access-Control-Allow-Origin','*');
    res.sendFile(path.resolve('static/index.html'));
});
//---------------------- SERVER START ---------------------
app.listen(port);
console.log('Server running');