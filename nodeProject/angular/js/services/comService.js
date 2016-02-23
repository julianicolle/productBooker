angular.module('comService', []).factory('com',comFnc);

function comFnc($http,$q) {
     var com = {
         loadTagList:       loadTagList,
         addTagToDb:      addTagToDb,
         deleteTagFromDb: deleteTagFromDb
         
     };
   
	function loadTagList() { 
		var deferred = $q.defer(); 
		$http.get('/product/getTagList').
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


	function addTagToDb(tag){
		var deferred = $q.defer(); 
		console.dir(tag);
		$http.post('/product/addTagToDb',tag).
			success(function(data, status, headers, config) { 
			deferred.resolve(data);
		}).
		error(function(data, status, headers, config) {
			deferred.reject(status);
	        // or server returns response with an error status.
		});
		return deferred.promise; 
	};

	function deleteTagFromDb(id){
		var deferred = $q.defer(); 
		var message = {'url' : id}
		console.dir(id);
		$http.post('/product/deleteTagFromDb',message).
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