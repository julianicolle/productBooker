angular.module('comService', []).factory('com',comFnc);

function comFnc($http,$q) {
     var com = {
         loadImages:       loadImages,
         savePres:      savePres
         
     };
   
	function loadImages() { 
		var deferred = $q.defer(); 
		$http.get('/slidRouter/slids').
			success(function(data, status, headers, config) {
			console.log(data);
			deferred.resolve(data); 
		}).
		error(function(data, status, headers, config) { 
			deferred.reject(status);
	        $log.error('failure loading content', errorPayload);
		});
		return deferred.promise; 
	};


	function savePres(currentPres){
		var deferred = $q.defer(); 
		console.dir(currentPres);
		$http.post('/savePres',currentPres).
			success(function(data, status, headers, config) { 
			deferred.resolve(data);
		}).
		error(function(data, status, headers, config) {
			deferred.reject(status);
	        // or server returns response with an error status.
		});
		return deferred.promise; 
	};



	return com;
};