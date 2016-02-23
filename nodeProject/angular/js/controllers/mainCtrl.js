angular.module('dbApp').controller('mainCtrl',mainCtrlFnct);

mainCtrlFnct.$inject=['$scope','$log','com'];

function mainCtrlFnct($scope,$log,com) {


	$scope.isAddingTag = false;
	$scope.tagList = [];
	$scope.addTag = {};
	$scope.loadList = function() {
		var future = com.loadTagList();
		future.then(
          function(payload) { 
              $scope.tagList = payload;
          },
          function(errorPayload) {
              $log.error('failure loading', errorPayload);
          });
	}
	$scope.loadList();

	

	$scope.deleteTag = function(id) {

		var future3 = com.deleteTagFromDb(id);
		future3.then(
          function(payload) { 
          	alert("deleted");
          },
          function(errorPayload) {
          	alert("error");
              $log.error('failure loading', errorPayload);
          });
		$scope.loadList();
	} 

	$scope.addTagDB = function() {
		console.log($scope.addTag);
		$scope.isAddingTag = false;
		var future2 = com.addTagToDb($scope.addTag);
		future2.then(
          function(payload) { 
          	alert("inserted");
          },
          function(errorPayload) {
          	alert("error");
              $log.error('failure loading', errorPayload);
          });
		$scope.loadList();
	}

	$scope.openAddTagMenu = function() {
		$scope.isAddingTag = true;
	}
}

