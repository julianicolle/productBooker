angular.module('dbApp').controller('mainCtrl',mainCtrlFnct);

mainCtrlFnct.$inject=['$scope','$log','com'];

function mainCtrlFnct($scope,$log,com) {

	$scope.tagList = [];
	var taga = {
		url : "www.www.com",
		available : true
	}
	var tagb = {
		url : "www.eee.com",
		available : false
	}

	$scope.tagList.push(taga);
	$scope.tagList.push(tagb);

	$scope.deleteTag = function(tag) {
		console.log(tag);
	} 
}

