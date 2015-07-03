backOffice.controller('addJobCtrl', function($rootScope, $scope, $filter, $http, $log, $state, editObj, urlContext) {
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
	
	$scope.saveJob = function() {
		$rootScope.startSpin();
		$http.post(url, {
			id          : id,
			companyName : $scope.formData2.companyName,
			comments    : $scope.formData2.comments,
			jobName     : $scope.formData2.jobName,
			userName    : $scope.formData2.userName
		}).success(function(response) {
			$scope.result = response;
			
			if($scope.result.status != 0) {
				alert('Have some Error!!');
				return;
			}
			
			/*if(id != null) {
				$scope.item = $filter('filter')($scope.items, {id: id})[0];				
				$scope.item.companyName = $scope.formData2.companyName;
				$scope.item.jobName = $scope.formData2.jobName;
			}else {
				var item = {companyName : $scope.formData2.companyName, jobName : $scope.formData2.jobName, userName : $scope.formData2.userName};
				$scope.items.splice(0, 0, item);
			}*/
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
		$state.go('junctJob.searchJob');
	};

});