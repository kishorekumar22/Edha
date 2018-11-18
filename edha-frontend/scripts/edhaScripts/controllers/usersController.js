'use strict';
app.controller('UsersController', function($scope,usersService , validationService,$rootScope) {
    $scope.errorMessage = "";
    $scope.modalData = {"username" : ""};
    $scope.getUsersList = function(){
        usersService.getUsersList()
        .then(
           function(response) {
              $scope.users = response.data;
              $rootScope.authenticated = true;
          },
          function(errResponse){
            $rootScope.checkAuth(errResponse);
              console.error('Error while getting Users');
              $scope.errorMessage = "Error in getting Users.Contact support!";
          });     
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

      $scope.addEditUserDetails = function(){
        var validation = validationService.validateUser($scope.modalData);
        if(validation && validation.length > 0){
          $scope.modalData.errorMessage = validation;
        }else{
          $('#userModal').modal("hide");
          usersService.saveUser($scope.modalData)
          .then(
             function(response) {
                $scope.getUsersList();
                $rootScope.authenticated = true;
            },
            function(errResponse){
              $rootScope.checkAuth(errResponse);
                console.error('Error while saving User details');
                $scope.errorMessage = "Error in saving User details.Contact support!";
            });
        }
      };

     

    
$scope.getUsersList();
  });
