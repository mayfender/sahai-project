
backOffice.controller('searchJobCtrl', function($rootScope, $scope, $http, $filter, $log, $state, searchObj, urlContext, usSpinnerService) {
    $scope.items = searchObj.data.searchLst;
	$scope.totalItems = searchObj.data.totalItems;
	$scope.itemsPerPage = 5;
	$scope.maxSize = 5;
	$scope.format = "dd-MM-yyyy";
	$scope.formData = {currentPage : 1};
	var userName = 'admin';
	
    $scope.search = function(){
    	$rootScope.startSpin();
    	$http.post(urlContext+'/rest/jobAction/searchJob', 
    	    {
    		companyName       : $scope.formData.companyName,
    		jobName           : $scope.formData.jobName,
	    	userName          : $scope.formData.userName,
	    	dateTimeStart     : $filter('date')($scope.formData.dateTimeStart, 'dd-MM-yyyy'),
	    	dateTimeEnd       : $filter('date')($scope.formData.dateTimeEnd, 'dd-MM-yyyy'),
	    	currentPage       : $scope.formData.currentPage,
	    	itemsPerPage      : $scope.itemsPerPage
	    	}
    	).success(function(response) {
    		if(response.status != 0) {
				alert("Have some Error");
				return;	
	    	}
	    	$log.log(response);
	    	$scope.items      = response.searchLst;
	    	$scope.totalItems = response.totalItems;
	    	$rootScope.stopSpin();
	    });  
	};
	
	$scope.deleteJob = function(id) {
		
		var r = confirm("ยืนยันการลบ!");
		if(r != true) return;
		
		$rootScope.startSpin();
		$http.get(urlContext+'/rest/jobAction/deleteJob?jobId='+id+'&userName='+userName)
		.success(function(response) {
	    		if(response.status != 0) {
	    			alert('Have some Error!!');
	    			return;
	    		}
	    		
	    		$scope.search();
		    });  
	};
	
	$scope.addJob = function(id) {
		$state.go('junctJob.addJob', {jobId : id});
	};
	
	$scope.showTask = function(id, compName) {
		$state.go('junctJob.junctTask.showTask', {jobId : id, companyName : compName});
	};
	
	$scope.clear = function() {
		$scope.formData.jobName = null;
		$scope.formData.companyName = null;
		$scope.formData.userName = null;
		$scope.formData.dateTimeStart = null;
		$scope.formData.dateTimeEnd = null;
	};


	/*--------------------------------------*/
	$scope.openStart = function($event) {
	    $event.preventDefault();
	    $event.stopPropagation();

	    $scope.formData.openedStart = true;
	};
	$scope.openEnd = function($event) {
	    $event.preventDefault();
	    $event.stopPropagation();

	    $scope.formData.openedEnd = true;
	};

	$scope.pageChanged = function() {
		$scope.search();
	};
	/*---------------------------------------*/
	
});