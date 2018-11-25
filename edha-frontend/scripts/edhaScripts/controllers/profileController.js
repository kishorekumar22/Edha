'use strict';
app.controller('ProfileController', function($scope,usersService,$routeParams,validationService,ordersService,customerService,$rootScope) {
   $scope.user ={};
   $scope.customersOfUser =[];
   $scope.userOrders = [];
   $scope.tabSelected = 'customers';
   $scope.userSales =[];
   $scope.user.username = $routeParams.username;
   $scope.expenses = [];
   $scope.newExpense = {};
   $scope.newExpense.amount='';
   $scope.newExpense.description = '';
   $scope.payment={};
   //$scope.showPasswordChange = JSON.parse(localStorage.currentUser).username == $routeParams.username;
   $scope.getUserDetails = function(){
      usersService.getUserDetails($routeParams.username)
      .then(
             function(response) {
                $scope.user = response.data;
                $rootScope.authenticated = true;
            },
            function(errResponse){
              $rootScope.checkAuth(errResponse);
                console.error('Error while getting User Details');
                $scope.errorMessage = "Error in getting User Details.Contact support!";
            }); 
        $scope.getCustomersofUser();
   };

   $scope.editUser = function(){
      $scope.modalData = JSON.parse(JSON.stringify($scope.user));
      $scope.modalData.usernameDisabled = true;
      $scope.modalData.active = $scope.modalData.active.toString();
   };

   $scope.addEditUserDetails = function(){
        var validation = validationService.validateUser($scope.modalData);
        if(validation && validation.length > 0){
          $scope.modalData.errorMessage = validation;
        }else{
          $('#userModal').modal("hide");
          usersService.saveEditedUser($scope.modalData)
          .then(
             function(response) {
                $scope.getUserDetails();
                $rootScope.authenticated = true;
            },
            function(errResponse){
              $rootScope.checkAuth(errResponse);
                console.error('Error while saving User details');
                $scope.errorMessage = "Error in saving User details.Contact support!";
            });
        }
      };

       $scope.validateUserName = function(username){
        if(username !=undefined && username.length > 0){
        usersService.validateUserName(username)
        .then(
           function(response) {
              $scope.modalData.usernameError = response.data;
            $rootScope.authenticated = true;
          },
          function(errResponse){
            $rootScope.checkAuth(errResponse);
              console.error('Error while validating Users');
              $scope.errorMessage = "Error in validating Users.Contact support!";
          });
      }else{
        $scope.modalData.usernameError = true;
      }
      };


      $scope.getCustomersofUser = function(){
      $scope.tabSelected = 'customers';
      customerService.getAssignedCustomer($routeParams.username)
      .then(
             function(response) {
                $scope.customersOfUser = response.data;
                $rootScope.authenticated = true;
            },
            function(errResponse){
              $rootScope.checkAuth(errResponse);
                console.error('Error while getting Customer Details');
                $scope.errorMessage = "Error in getting Customer Details.Contact support!";
            }); 
      };

      $scope.getNewOrdersOfUser =function(){
      $scope.tabSelected="orders";
      ordersService.listMyOrders($routeParams.username)
      .then(
           function(response) {
              $scope.userOrders = response.data;
            $rootScope.authenticated = true;
          },
          function(errResponse){
            $rootScope.checkAuth(errResponse);
              console.error('Error while fetching orders for User');
              $scope.errorMessage = "Error in fetching orders for User.Contact support!";
          });
    };

    $scope.getClosedOrdersOfUser = function(){
      $scope.tabSelected='sales';
      ordersService.listMyClosedOrders($routeParams.username)
      .then(
           function(response) {
              $scope.userSales = response.data;
            $rootScope.authenticated = true;
          },
          function(errResponse){
            $rootScope.checkAuth(errResponse);
              console.error('Error while fetching sales of User');
              $scope.errorMessage = "Error in fetching sales of User.Contact support!";
          });
    };
   
    $scope.getSum = function(products){
        var total = 0;
        angular.forEach(products,function(p){
      total += p.qty * p.product.price;

    });
        return total;
    };

    $scope.addExpense = function(){
      var exists = false;
      angular.forEach($scope.expenses, function(e){
        if(e.description == $scope.newExpense.description){
          e.amount += $scope.newExpense.amount;
          exists = true;
        }
      });
      if(!exists)
      $scope.expenses.push({'description' : $scope.newExpense.description , 'amount' : $scope.newExpense.amount})
      $scope.newExpense.description = "";
      $scope.newExpense.amount =0;
    };

    $scope.saveExpense = function(){
      usersService.saveExpense($scope.expenses)
      .then(
             function(response) {
                $rootScope.authenticated = true;
            },
            function(errResponse){
              $rootScope.checkAuth(errResponse);
              if(errResponse.data && errResponse.data.message){
                $scope.saveExpenseNotAllowed = true;
              }
              else{
                console.error('Error while saving expenses');
                $scope.errorMessage = "Error in saving expenses.Contact support!";
            }
            }); 

    $scope.expenses = [];
    };
    

    $scope.getMonthlyExpense = function(){
      $scope.tabSelected='expenses';
      usersService.getMonthlyExpense($routeParams.username)
      .then(
             function(response) {
                $rootScope.authenticated = true;
                $scope.monthlyExpense = response.data;
            },
            function(errResponse){
              $rootScope.checkAuth(errResponse);      
                console.error('Error while getting expenses');
                $scope.errorMessage = "Error in getting expenses.Contact support!";
       
            });
    };

    $scope.calculateExpense = function(expense){
      var total = 0;
      angular.forEach(expense, function(e){
        total += e.amount;
      });
      return total;
    };

    $scope.addPayment = function (payment){
    
      var validation = validationService.validatePayment(payment);

      if(validation && validation.length > 0){
          $scope.payment.errorMessage = validation;
        }else{
          $('#paymentModal').modal("hide");
          ordersService.addPaymentToOrder(payment,payment.orderId)
          .then(
           function(response) {
             $scope.getNewOrdersOfUser();
            $rootScope.authenticated = true;
          },
          function(errResponse){
            $rootScope.checkAuth(errResponse);
              console.error('Error while fetching sales of User');
              $scope.errorMessage = "Error in fetching sales of User.Contact support!";
          });
        }
    };

   $scope.getUserDetails();
  });
