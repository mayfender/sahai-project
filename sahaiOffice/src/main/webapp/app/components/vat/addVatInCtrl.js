backOffice.controller('addVatInCtrl', function($rootScope, $scope, $filter, $http, $log, $state, editObj, urlContext) {
	$scope.formData2 = {};
	$scope.formData2.userName = 'admin';
	var id = null;
	var url = null;
	
	if(editObj != null){
		$scope.formData2.companyName = editObj.data.companyName;
		$scope.formData2.comments = editObj.data.comments;
		$scope.formData2.jobName = editObj.data.jobName;			
		$scope.saveBtnMsg = 'แก้ใข';
		url = urlContext+'/jobAction/updateJob';
		id = editObj.data.id;
	}else{
		url = urlContext+'/jobAction/saveJob';
		$scope.saveBtnMsg = 'บันทึก';			
	}
	
	$scope.saveVatIn = function() {
		$rootScope.startSpin();
		$http.post(url, {
			companyName : $scope.formData2.companyName,
			comments    : $scope.formData2.docNo,
			jobName     : $scope.formData2.vatInDate,
			userName    : $scope.formData2.dueDate,
			userName    : $scope.formData2.payCondition,
			userName    : $scope.formData2.totalPrice,
			userName    : $scope.formData2.others
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

});