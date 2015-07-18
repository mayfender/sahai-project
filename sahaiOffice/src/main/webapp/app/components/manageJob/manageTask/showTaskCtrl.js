backOffice.controller('showTaskCtrl', function($rootScope, $scope, $http, $filter, $log, $state, $stateParams, tasks, urlContext) {	
	var userName = 'admin';
	$scope.tasks = tasks.data.tasks;
	$scope.docTypeDesc = [{
		id: 1,
	  	label: 'ใบสั่งซื้อ'
    }, {
    	id: 2,
    	label: 'ใบเสนอราคา'
    }, {
    	id: 3,
    	label: 'ใบสอบถามราคา'
    }];
	
	$scope.searchTask = function() {
		$rootScope.startSpin();
		$http.get(urlContext+'/taskAction/showTasks?jobId='+$stateParams.jobId)
		.success(function(response) {
    		if(response.status != 0) {
				alert("Have some Error");
				return;	
	    	}
    		
    		$scope.tasks = response.tasks;
    		$rootScope.stopSpin();
	    });  
	};
	
	$scope.addTask = function() {
		$state.go('junctJob.junctTask.addTask', {jobId : $stateParams.jobId});
	};
	
	$scope.editTask = function(id) {
		$state.go('junctJob.junctTask.addTask', {taskId : id});
	};
	
	$scope.viewTask = function(id) {
		$state.go('junctJob.junctTask.addTask', {taskId : id, mode : 'v'});
	};
	
	$scope.copyTask = function(id) {
		$rootScope.startSpin();
		$http.get(urlContext+'/taskAction/copyTask?taskId='+id)
		.success(function(response) {
    		if(response.status != 0) {
				alert("Have some Error");
				return;	
	    	}
    		
    		$scope.searchTask();
	    });  
	};
	
	$scope.deleteTask = function(id) {
		var r = confirm("ยืนยันการลบ!");
		if(r != true) return;
		
		$rootScope.startSpin();
		$http.get(urlContext+'/taskAction/deleteTask?taskId='+id)
		.success(function(response) {
    		if(response.status != 0) {
    			alert('Have some Error!!');
    			return;
    		}
    		
    		$scope.searchTask();
	    });  
	};
	
	$scope.goBack = function() {
		$state.go('junctJob.searchJob');
	};
	
});