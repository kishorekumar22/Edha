'use strict';
app.controller('SettingsController', function($scope,settingsService,$rootScope) {
	$scope.newExpenseType={};
	$scope.newDiscountType={};
	$scope.saveExpenseType = function(exType){
		$('#expenseTypeModal').modal("hide");
		settingsService.saveExpenseType(exType).then(
           function(response) {
              $scope.expenseTypes = response.data;
              $rootScope.authenticated = true;
              $scope.getExpenseTypes();
          },
          function(errResponse){
            $rootScope.checkAuth(errResponse);
              console.error('Error while saving Expense types');
              $scope.errorMessage = "Error in saving Expense types.Contact support!";
          }); 
	};

	$scope.getExpenseTypes = function(){
		settingsService.fetchExpenseType().then(
           function(response) {
              $scope.expenseTypes = response.data;
              //angular.forEach($scope.expenseTypes,function(ex){ex.isEdit=false;});
              $rootScope.authenticated = true;
              $scope.newExpenseType={};
          },
          function(errResponse){
            $rootScope.checkAuth(errResponse);
              console.error('Error while getting Expense types');
              $scope.errorMessage = "Error in getting Expense types.Contact support!";
          }); 
	};

	$scope.deleteExpenseType = function(exType){
		settingsService.deleteExpenseType(exType).then(
           function(response) {
              $scope.expenseTypes = response.data;
              $rootScope.authenticated = true;
              $scope.getExpenseTypes();
          },
          function(errResponse){
            $rootScope.checkAuth(errResponse);
              console.error('Error while deleting Expense types');
              $scope.errorMessage = "Error in deleting Expense types.Contact support!";
          }); 
	};

	$scope.editExpenseType = function(exType){
		$scope.newExpenseType = exType;
		$('#expenseTypeModal').modal("show");
	}



	$scope.saveDiscountType = function(exType){
		$('#discountTypeModal').modal("hide");
		settingsService.saveDiscountType(exType).then(
           function(response) {
              $scope.discountTypes = response.data;
              $rootScope.authenticated = true;
              $scope.getDiscountTypes();
          },
          function(errResponse){
            $rootScope.checkAuth(errResponse);
              console.error('Error while saving Expense types');
              $scope.errorMessage = "Error in saving Discount types.Contact support!";
          }); 
	};

	$scope.getDiscountTypes = function(){
		settingsService.fetchDiscountType().then(
           function(response) {
              $scope.discountTypes = response.data;
              //angular.forEach($scope.expenseTypes,function(ex){ex.isEdit=false;});
              $rootScope.authenticated = true;
              $scope.newDiscountType={};
          },
          function(errResponse){
            $rootScope.checkAuth(errResponse);
              console.error('Error while getting Discount types');
              $scope.errorMessage = "Error in getting Discount types.Contact support!";
          }); 
	};

	$scope.deleteDiscountType = function(dType){
		settingsService.deleteDiscountType(dType).then(
           function(response) {
              $scope.discountTypes = response.data;
              $rootScope.authenticated = true;
              $scope.getDiscountTypes();
          },
          function(errResponse){
            $rootScope.checkAuth(errResponse);
              console.error('Error while deleting Discount types');
              $scope.errorMessage = "Error in deleting Discount types.Contact support!";
          }); 
	};

	$scope.editDiscountType = function(dType){
		$scope.newDiscountType = dType;
		$('#discountTypeModal').modal("show");
	}

$scope.getExpenseTypes();
$scope.getDiscountTypes();
});