backOffice.controller('addTaskCtrl', function($rootScope, $scope, $modal, $http, $filter, $log, $state, $stateParams, taskObj, urlContext) {	
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
	
	$scope.taskItems = [{"partNo":"","itemName":"","quantity":"","unit":"","unitPrice":"","amount":""}];
	$scope.backBtnMsg = 'ยกเลิก';
	var url;
	
	if(taskObj) {
		console.log(taskObj);
		
		$scope.saveBtnMsg = 'แก้ใข';
		url = urlContext+'/taskAction/updateTask';
		
		if($stateParams.mode === 'v') {
			$scope.isDisable = true;
			$scope.backBtnMsg = 'กลับ';
		}else{
			$scope.isDisable = false;
		}
		
		$scope.taskId = taskObj.data.id;
		$scope.docNo = taskObj.data.docNo;
		$scope.docType = parseInt(taskObj.data.docType);
		$scope.companyName = taskObj.data.companyName;
		$scope.contactPersonName = taskObj.data.contactPersonName;
		$scope.contactPersonTel = taskObj.data.contactPersonTel;
		$scope.contactPersonFax = taskObj.data.contactPersonFax;
		$scope.userName = taskObj.data.userName;
		$scope.userTel = taskObj.data.userTel;
		$scope.comments = taskObj.data.comments;
		$scope.taskItems = taskObj.data.items;
//		$scope.discount = taskObj.data.discount;
		$scope.address = taskObj.data.address;		
		$scope.payCondition = taskObj.data.payCondition;		
		$scope.placeToSend = taskObj.data.placeToSend;		
		$scope.dateToSend = taskObj.data.dateToSend;		
		$scope.vatObj = taskObj.data.vatObj;
		$scope.isSold = $scope.vatObj != null ? true : false;		
		$scope.isCreatedVat = $scope.vatObj == null ? true : false;
		$scope.vatAddress = $scope.vatObj != null ? $scope.vatObj.address : $scope.address;
		
		if($scope.docType == 1) {
			$scope.isPR = true;	
		}else if($scope.docType == 2) {
			$scope.isPR = false;	
			$scope.isQuote = false;										
		}else if($scope.docType == 3) {
			$scope.isPR = false;	
			$scope.isQuote = true;			
		}
		
		if(taskObj.data.items.length > 1) {
    		$scope.isShowDelete = true;
    	}
	}else{
		$scope.isDisable = false;
		$scope.saveBtnMsg = 'บันทึก';
		url = urlContext+'/taskAction/saveTask';
	}
	
	$scope.firstPrice;
//	$scope.discount;
//	$scope.afterDiscount;
	$scope.vat;
	$scope.totalPrice;
	
	$scope.saveTask = function() {
		$rootScope.startSpin();
    	$http.post(url, 
    	    {
    		id                : $scope.taskId,
    		jobId             : $stateParams.jobId,
    		docNo             : $scope.docNo,
    		docType           : $scope.docType,
    		companyName       : $scope.companyName,
	    	contactPersonName : $scope.contactPersonName,
	    	contactPersonTel  : $scope.contactPersonTel,
	    	contactPersonFax  : $scope.contactPersonFax,
	    	userName          : $scope.userName,
	    	userTel           : $scope.userTel,
	    	address           : $scope.address,
	    	comments          : $scope.comments,
	    	payCondition      : $scope.payCondition,
	    	placeToSend       : $scope.placeToSend,
	    	dateToSend        : $scope.dateToSend,
	    	firstPrice        : $scope.firstPrice,
//	    	discount          : $scope.discount,
//	    	afterDiscount     : $scope.afterDiscount,
	    	vat               : $scope.vat,
	    	totalPrice        : $scope.totalPrice,
	    	items             : $scope.taskItems
	    	}
    	).success(function(response) {
    		if(response.status != 0) {
    			alert('Have some Error!!');
    			return;
    		}
    		
    		$scope.searchTask();
    		$state.go('junctJob.junctTask.showTask');
    	});  
	};
	
	$scope.addTaskItem = function(){
		$scope.isShowDelete = true;
    	$scope.taskItems[$scope.taskItems.length] = {"partNo":"","itemName":"","quantity":"","unit":"","unitPrice":"","amount":""};
	};
	
	$scope.deleteTaskItem = function(i){
    	$scope.taskItems.splice(i, 1);   
    	
    	if($scope.taskItems.length == 1) {
    		$scope.isShowDelete = false;
    	}
	};
	
	$scope.keyEnter = function(keyEvent) {
    	if (keyEvent.which === 13){
    		$scope.addTaskItem();
    		keyEvent.stopPropagation();
    		keyEvent.preventDefault();
    	}
	};
	
	$scope.goBack = function() {
		$state.go('junctJob.junctTask.showTask');
	};
	
	$scope.docTypeChange = function() {
		if($scope.docType == 1) {
			$scope.isPR = true;
			$scope.isQuote = false;
			$scope.companyName = null;
		}else if($scope.docType == 2) {
			$scope.companyName = $stateParams.companyName;						
			$scope.isQuote = false;
			$scope.isPR = false;
		}else if($scope.docType == 3) {
			$scope.isPR = false;
			$scope.isQuote = true;
			$scope.companyName = null;
		}
	};
	
	$scope.exportFile = function(isVat) {
		document.addTaskForm.action = urlContext + "/exportHandler";
		document.addTaskForm.itemId.value = $scope.taskId;
		document.addTaskForm.isVat.value = isVat;
		document.addTaskForm.submit();
	};
	
	$scope.clear = function() {
		$scope.docType = null;
		$scope.companyName = null;
		$scope.contactPersonName = null;
		$scope.contactPersonTel = null;
		$scope.contactPersonFax = null;
		$scope.userName = null;
		$scope.comments = null;
	};
	
	
	
	//--------: Modal dialog
	$scope.openModalDialog = function (size) {
		
		var modalInstance = $modal.open({
			animation: true,
			templateUrl: 'myModalContent.html',
			controller : function($scope, $modalInstance, data) {
				$scope.data = {};
				$scope.data.address = data.address;
				$scope.data.payCondition = data.payCondition;
				$scope.data.payDate = data.payDate;
				$scope.data.poNo = data.poNo;
				$scope.format = "dd-MM-yyyy";
				
				$scope.ok = function () {
				    $modalInstance.close($scope.data);
				};

				$scope.cancel = function () {
				    $modalInstance.dismiss('cancel');
				};
				
				$scope.openStart = function($event) {
				    $event.preventDefault();
				    $event.stopPropagation();

				    $scope.data.openedStart = true;
				};
			},
			resolve: {
		        data: function () {
		          var data = {};
		          data.address = $scope.vatAddress;
		          
		          if($scope.vatObj != null) {
		        	  data.payCondition = $scope.vatObj.payCondition;
		        	  data.payDate = $scope.vatObj.payDate;
		        	  data.poNo = $scope.vatObj.poNo;		        	  
		          }
		          return data;
		        }
			}
		});		
		
		 modalInstance.result.then(function (result) {
			 $http.post(urlContext+'/taskAction/saveVat', 
		    	    {
				 	isCreatedVat: $scope.isCreatedVat,
				 	taskId: 	  $scope.taskId,
		    		address:      result.address,
			    	payCondition: result.payCondition,
			    	payDate:      $filter('date')(result.payDate, 'dd/MM/yyyy'),
			    	poNo: 		  result.poNo
			    	}
		    ).success(function(response) {
	    		if(response.status != 0) {
	    			alert('Have some Error!!');
	    			return;
	    		}
	    		
	    		$scope.vatObj = {};
	    		$scope.vatObj.id = response.id;
	    		$scope.vatObj.payCondition = response.payCondition;
	    		$scope.vatObj.payDate = response.payDate;
	    		$scope.vatObj.poNo = response.poNo;
	    		$scope.vatAddress = response.address;
	    		$scope.isCreatedVat = false;
	    		
	    		$scope.exportFile(1);
		    });  
		      
		 }, function (result) {
			 //Cancel button to be clicked.
		 });
	};
	
});