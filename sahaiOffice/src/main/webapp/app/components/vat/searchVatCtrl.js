
backOffice.controller('searchVatCtrl', function($rootScope, $scope, $http, $filter, $log, $state, searchObj, urlContext, usSpinnerService) {
    $scope.items = searchObj.data.vatLst;
	$scope.totalItems = searchObj.data.totalItems;
	$scope.sumVatInTotalPrice = searchObj.data.sumVatInTotalPrice;
	$scope.sumVatOutTotalPrice = searchObj.data.sumVatOutTotalPrice;
	$scope.buyVat = searchObj.data.buyVat;
	$scope.saleVat = searchObj.data.saleVat;
	$scope.payVat = searchObj.data.payVat;
	$scope.itemsPerPage = 5;
	$scope.maxSize = 5;
	$scope.format = "dd/MM/yyyy";
	$scope.formData = {currentPage : 1};
	$scope.vatTypeLabel = 'เลือกทั้งหมด';
	$scope.vatTypeSelected = 0;
	
    $scope.search = function(){
    	$rootScope.startSpin();
    	$http.post(urlContext+'/vatAction/searchVat', 
    	    {
    		companyName       : $scope.formData.companyName,
    		docNo             : $scope.formData.docNo,
	    	dateTimeStart     : $filter('date')($scope.formData.dateTimeStart, 'dd-MM-yyyy'),
	    	dateTimeEnd       : $filter('date')($scope.formData.dateTimeEnd, 'dd-MM-yyyy'),
	    	currentPage       : $scope.formData.currentPage,
	    	itemsPerPage      : $scope.itemsPerPage,
	    	vatType           : $scope.vatTypeSelected
	    	}
    	).success(function(response) {
    		if(response.status != 0) {
				alert("Have some Error");
				return;	
	    	}
	    	$log.log(response);
	    	$scope.items      = response.vatLst;
	    	$scope.totalItems = response.totalItems;
	    	$scope.sumVatInTotalPrice = response.sumVatInTotalPrice;
	    	$scope.sumVatOutTotalPrice = response.sumVatOutTotalPrice;
	    	$scope.buyVat = response.buyVat;
	    	$scope.saleVat = response.saleVat;
	    	$scope.payVat = response.payVat;
	    	
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
	
	
	/*------------------: Vat type combobox:--------------------*/
	$scope.vatType = function(selected, $event) {
		$scope.vatTypeSelected = selected;
		$scope.vatTypeLabel = $event.target.innerHTML;
		$scope.search();
	}

	$scope.status = {
		isopen : false
	};
	/*------------------: Vat type combobox:--------------------*/

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