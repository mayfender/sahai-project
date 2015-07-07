
backOffice.controller('searchVatCtrl', function($rootScope, $scope, $http, $filter, $log, $state, searchObj, urlContext, usSpinnerService) {
    $scope.items = searchObj.data.vatLst;
	$scope.totalItems = searchObj.data.totalItems;
	$scope.itemsPerPage = 5;
	$scope.maxSize = 5;
	$scope.format = "dd/MM/yyyy";
	$scope.formData = {currentPage : 1};
	var userName = 'admin';
	
    $scope.search = function(){
    	$rootScope.startSpin();
    	$http.post(urlContext+'/vatAction/searchVat', 
    	    {
    		companyName       : $scope.formData.companyName,
    		docNo             : $scope.formData.docNo,
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
	    	$scope.items      = response.vatLst;
	    	$scope.totalItems = response.totalItems;
	    	$rootScope.stopSpin();
	    });  
	};
	
	$scope.clear = function() {
		$scope.formData.companyName = null;
		$scope.formData.docNo = null;
		$scope.formData.dateTimeStart = null;
		$scope.formData.dateTimeEnd = null;
	};
	
	$scope.addVatIn = function(id) {
		$state.go('junctVat.addVatIn', {vatInId : id});
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