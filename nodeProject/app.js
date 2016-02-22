//Lets require/import the HTTP module
//var path       = require('path');
var express    = require('express');
var http       = require('http');
var bodyParser = require('body-parser');
var app        = express();
var path = require('path');


app.use( bodyParser.json() );
app.use(bodyParser.urlencoded({
  extended: true
}));


//Router Definition
var authRoute = require("./app/routes/product.route.js");


//launch Server
var server = http.createServer(app);
server.listen(8888, function(){
  console.log('web server listening on port 8888');
});


//ROUTES
app.use("/", express.static(path.join(__dirname, "/angular")));
app.use("/product", authRoute);
app.use("/mainPage",express.static(path.join(__dirname, "angular/main.html"))); 

