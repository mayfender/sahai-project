var backOffice = angular.module('backOffice', ['ui.router', 'ui.bootstrap', 'angularSpinner']);

backOffice.value('urlContext', 'http://localhost:8080/sahaiOffice/resResource');

backOffice.run(function($rootScope, usSpinnerService) {
	$rootScope.startSpin = function(){
        usSpinnerService.spin('spinner-1');
    };
    $rootScope.stopSpin = function(){
        usSpinnerService.stop('spinner-1');
    };
});

backOffice.config(function($stateProvider, $urlRouterProvider, usSpinnerConfigProvider) {
	//--: Spinner
	usSpinnerConfigProvider.setDefaults({color: 'black', stop:'spinner-1'});
	
	//--: Router
	$stateProvider
	.state('home', {
		url        : '/home',
		templateUrl: 'home/home.html',
		controller : function($scope, $rootScope) {
			$rootScope.stopSpin();
		}
	})
	.state('junctJob', {
		url        : '/junctJob',
		template   : '<ui-view />',
		controller : 'searchJobCtrl',
		resolve    : {
				searchObj : function($rootScope, $http, $stateParams, urlContext){
					$rootScope.startSpin();
					return $http.post(urlContext+'/jobAction/searchJob',
							{
							companyName       : null,
				    		jobName           : null,
					    	userName          : null,
					    	dateTimeStart     : null,
					    	dateTimeEnd       : null,
					    	currentPage       : 1,
					    	itemsPerPage      : 5
							}
					       ).then(function(resp){
					    	   if(resp.data.status != 0) {
					    		   console.log('Have error!');
					    	   }
					    	   $rootScope.stopSpin();
					    	   return resp;
					       });
				}
			}
	})
	.state('junctJob.searchJob', {
		url        : '/searchJob',
		templateUrl: 'manageJob/searchJob.html',
		onEnter    : function(){
			 document.body.scrollTop = document.documentElement.scrollTop = 0;
		}
	})
	.state('junctJob.addJob', {
		url        : '/addJob',
		templateUrl: 'manageJob/addJob.html',
		controller : 'addJobCtrl',
		params     : {jobId : null},
		resolve    : {
		   editObj : function($rootScope, $http, $stateParams, urlContext){
				if($stateParams.jobId) {
					$rootScope.startSpin();
					return $http.post(urlContext+'/jobAction/viewJob', $stateParams.jobId).
					then(function(resp){
				    	   if(resp.data.status != 0) {
				    		   console.log('Have error!');
				    	   }
				    	   $rootScope.stopSpin();
				    	   return resp;
				       });
				}else{
					return null;
				}
			}
		},
		onEnter    : function(){
			 document.body.scrollTop = document.documentElement.scrollTop = 0;
		}
	})
	
	//----------: Task :--
	.state('junctJob.junctTask', {
		url        : '/junctTask',
		template   : '<ui-view />',
		controller : 'showTaskCtrl',
		params     : {jobId : null, companyName : null},
		resolve    : {
			tasks : function($rootScope, $http, $stateParams, urlContext){
					$rootScope.startSpin();
					return $http.get(urlContext+'/taskAction/showTasks?jobId='+$stateParams.jobId).
					then(function(resp){
				    	   if(resp.data.status != 0) {
				    		   console.log('Have error!');
				    	   }
				    	   $rootScope.stopSpin();
				    	   return resp;
				       });
				}
			}
	})
	.state('junctJob.junctTask.showTask', {
		url        : '/showTask',
		templateUrl: 'manageJob/manageTask/showTask.html',
		onEnter    : function(){
			 document.body.scrollTop = document.documentElement.scrollTop = 0;
		}
	})
	.state('junctJob.junctTask.addTask', {
		url        : '/addTask',
		params     : {jobId : null, taskId : null, mode : null},
		templateUrl: 'manageJob/manageTask/addTask.html',
	    controller : 'addTaskCtrl',
	    resolve    : {
			taskObj : function($rootScope, $http, $stateParams, urlContext){
				if($stateParams.taskId) {
					$rootScope.startSpin();
					return $http.get(urlContext+'/taskAction/findTask?taskId='+$stateParams.taskId).
					then(function(resp){
				    	   if(resp.data.status != 0) {
				    		   console.log('Have error!');
				    	   }
				    	   $rootScope.stopSpin();
				    	   return resp;
				       });
				}
			}
	    },
	    onEnter    : function(){
			 document.body.scrollTop = document.documentElement.scrollTop = 0;
		}
	})
	
	//----------: Vat :--
	.state('junctVat', {
		url        : '/junctVat',
		template   : '<ui-view />',
		controller : 'searchVatCtrl',
		resolve    : {
				searchObj : function($rootScope, $http, $stateParams, urlContext){
					$rootScope.startSpin();
					return $http.post(urlContext+'/vatAction/searchVat',
							{
					    	currentPage       : 1,
					    	itemsPerPage      : 5,
					    	vatType           : 0
							}
					       ).then(function(resp){
					    	   if(resp.data.status != 0) {
					    		   console.log('Have error!');
					    	   }
					    	   $rootScope.stopSpin();
					    	   return resp;
					       });
				}
			}
	})
	.state('junctVat.searchVat', {
		url        : '/searchVat',
		templateUrl: 'vat/searchVat.html',
		onEnter    : function(){
			 document.body.scrollTop = document.documentElement.scrollTop = 0;
		}
	})
	.state('junctVat.addVatIn', {
		url        : '/addVatIn',
		templateUrl: 'vat/addVatIn.html',
		params     : {vatInId : null},
		controller : 'addVatInCtrl',
		resolve    : {
			editObj : function($rootScope, $http, $stateParams, urlContext){
				if($stateParams.vatInId) {
					/*$rootScope.startSpin();
					return $http.get(urlContext+'/taskAction/findTask?taskId='+$stateParams.taskId).
					then(function(resp){
				    	   if(resp.data.status != 0) {
				    		   console.log('Have error!');
				    	   }
				    	   $rootScope.stopSpin();
				    	   return resp;
				       });*/
				}
			}
	    },
		onEnter    : function(){
			 document.body.scrollTop = document.documentElement.scrollTop = 0;
		}
	});
	
	$urlRouterProvider.otherwise('/home');
	
});


/*--------------------: Filter :-----------------------*/
backOffice.filter('sumFilter', function() {
	return function(items){
		var total = 0;
		items.forEach(function(item) {
        	total += item.amount;
        });
        return total;
	};
	
	
	
});