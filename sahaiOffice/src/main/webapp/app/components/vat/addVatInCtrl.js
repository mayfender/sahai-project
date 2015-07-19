backOffice.controller('addVatInCtrl', function($rootScope, $scope, $filter, $http, $log, $state, editObj, urlContext) {
	$scope.formData2 = {};
	$scope.format = "dd/MM/yyyy";
	var id = null;
	var url = null;
	
	if(editObj != null){
		$scope.formData2.companyName = editObj.data.companyName;
		$scope.formData2.docNo = editObj.data.vatDocNo;
		$scope.formData2.vatInDate = editObj.data.vatInDate;
		$scope.formData2.dueDate = editObj.data.vatDueDate;
		$scope.formData2.payCondition = editObj.data.vatPayCondition;
		$scope.formData2.totalPrice = editObj.data.totalPrice;
		$scope.formData2.others = editObj.data.others;
		$scope.saveBtnMsg = 'แก้ใข';
		//url = urlContext+'/vatAction/updateVat';
		//id = editObj.data.id;
	}else{
		url = urlContext+'/vatAction/saveVat';
		$scope.saveBtnMsg = 'บันทึก';			
	}
	
	$scope.saveVatIn = function() {
		$rootScope.startSpin();
		$http.post(url, {
			companyName        : $scope.formData2.companyName,
			vatDocNo           : $scope.formData2.docNo,
			vatCreatedDateTime : $filter('date')($scope.formData2.vatInDate, 'dd/MM/yyyy'),
			vatDueDate         : $filter('date')($scope.formData2.dueDate, 'dd/MM/yyyy'),
			vatPayCondition    : $scope.formData2.payCondition,
			totalPrice         : $scope.formData2.totalPrice,
			others             : $scope.formData2.others,
			vatType            : "1"
		}).success(function(response) {
			$scope.result = response;
			
			if($scope.result.status != 0) {
				alert('Have some Error!!');
				return;
			}
			
			$scope.search();
			$scope.goBack();
		});
	};
	
	$scope.clear = function() {
		$scope.formData2.companyName = null;
		$scope.formData2.comments = null;
		$scope.formData2.jobName = null;
	};
	
	$scope.goBack = function() {
		$state.go('junctVat.searchVat');
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
	/*---------------------------------------*/

});