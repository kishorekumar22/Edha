'use strict';
app.controller('CustomerController', function($rootScope,$scope,customerService,validationService,usersService) {
    $scope.errorMessage = "";
	  $scope.customers = [];
    $scope.modalData ={};
    $scope.users=[];
    //$scope.filter = {"customerName" : "", "inchargeName": ""};
    $scope.getCustomerList = function(){
        customerService.getCustomers()
        .then(
           function(response) {
              $scope.customers = response.data;
              $rootScope.authenticated = true;
          },
          function(errResponse){
            $rootScope.checkAuth(errResponse);
              console.error('Error while getting customer data');
              $scope.errorMessage = "Error in getting Customer data.Contact support!";
          });  
          usersService.getActiveUsers()
          .then(
           function(response) {
              $scope.users = response.data;
              $rootScope.authenticated = true;
          },
          function(errResponse){
            $rootScope.checkAuth(errResponse);
              console.error('Error while getting user data');
              $scope.errorMessage = "Error in getting users data.Contact support!";
          });     
      };
    $scope.getCustomerByFilter = function(filter){
  
        var params = '?';
        if(filter.customerName && filter.customerName.length > 0 )
            params += 'customerName=' + filter.customerName + '&';
        if(filter.inchargeName && filter.inchargeName.length > 0)
          params += 'inchargeName=' + filter.inchargeName + '&';
        if(params.length > 1){
          customerService.getCustomersByFilter(params.substr(0,params.length - 1))
          .then(
           function(response) {
              $scope.customers = response.data;
              $rootScope.authenticated = true;
          },
          function(errResponse){
            $rootScope.checkAuth(errResponse);
              console.error('Error while getting customer data');
              $scope.errorMessage = "Error in getting Customer data.Contact support!";
          });     
      }
        
      };
      $scope.addNewCustomer = function(customer){
        //hard coded 
        //customer.createdBy = { 'username' : "testuser1"};
        var validation = validationService.validateCustomer(customer);
        if(validation && validation.length > 0){
          $scope.modalData.errorMessage = validation;
        }else{
           $('#customerModal').modal("hide");
          customerService.addCustomer(customer)
          .then(
             function(response) {
                $scope.getCustomerList();
                $rootScope.authenticated = true;
            },
            function(errResponse){
              $rootScope.checkAuth(errResponse);
                console.error('Error while adding customer data');
                $scope.errorMessage = "Error in adding Customer data.Contact support!";
            });
        }
      };

      $scope.editCustomer = function(customer){
        $scope.modalData = JSON.parse(JSON.stringify(customer));
      };

   
    
$scope.getCustomerList();
  });
