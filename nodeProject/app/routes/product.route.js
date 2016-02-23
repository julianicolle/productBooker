var mysql      = require('mysql');
var express    = require('express');
var router = express.Router();
var bodyParser = require('body-parser');
module.exports = router;

router.use( bodyParser.json() );
router.use(bodyParser.urlencoded({
  extended: true
}));


var connection = mysql.createConnection({
  host     : 'localhost',
  port     : '8889',
  user     : 'root',
  password : 'root',
  database : 'testJulia'
});


connection.connect(function(err){
if(!err) {
    console.log("Database is connected ... nn");    
} else {
    console.log("Error connecting database ... nn");    
}
});


router.route("/getId")
	.post(function(request,res) {
 	console.log(request.body.info);
	 	connection.query('SELECT * FROM `tag` WHERE `id` = ?', [request.body.info] , function(err, rows, fields) {
			if (!err){
				console.log('The solution is: ', rows);
				if (rows.length > 0) {
					res.status(200).send(true);
				}
				else {
					res.status(200).send(false);	
				}
			}
			else{
				console.log('Error while performing Query.');
				res.status(500).send(err);
			}
	    
		});
	});

router.route("/getTagList")
	.get(function(request,res) {
	 	connection.query('SELECT `id`, `available` FROM `tag`', function(err, rows, fields) {
			if (!err){
				console.log('The solution is: ', rows);
				if (rows.length > 0) {
					res.status(200).send(rows);
				}
				else {
					res.status(500).send(false);	
				}
			}
			else{
				console.log('Error while performing Query.');
				res.status(500).send(err);
			}
	    
		});
	});



router.route("/addTagToDb")
.post(function(request,res) {
	console.log(request.body);
 	connection.query('INSERT INTO tag SET ?', [request.body] , function(err, rows) {
		if (!err){
			console.log('inserted', rows);
			res.status(200).send(true);	
		}
		else{
			console.log('Error while performing Query.');
			res.status(500).send(err);
		}
    
	});
});

router.route("/deleteTagFromDb")
.post(function(request,res) {
	console.log("             _____________                 ");
	console.log(request.body);
 	connection.query('DELETE FROM `tag` WHERE `id` = ?', [request.body.url] , function(err, rows) {
		if (!err){
			console.log('deleted', rows);
			res.status(200).send(true);	
		}
		else{
			console.log('Error while performing Query.');
			res.status(500).send(err);
		}
    
	});
});

 
