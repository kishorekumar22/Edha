'use strict';
app.controller('LoginController', function($rootScope, $scope, $http, $location ,CONSTANTS, usersService) {
   $scope.performLogin = function(){
    var headers = $scope.credentials ? {authorization : "Basic "
        + btoa($scope.credentials.username + ":" + $scope.credentials.password)
    } : {};

    $http.get(CONSTANTS.LOGIN_URL , {headers : headers})
      .then(
           function(response) {
               if (response.status==200) {
                $rootScope.authenticated = true;
                $rootScope.username = $scope.credentials.username;
                localStorage.currentUser = JSON.stringify({ username: $scope.credentials.username, token: response.data });
                $http.defaults.headers.common["edha-auth"] = response.data;
                //alert(response.headers['edha-auth']);
                $scope.error = false;
                $location.path("/products");
              } else {
                $rootScope.authenticated = false;
                $scope.error = true;
              }
             },
          function(errResponse){
              console.error('Login Failed' + JSON.stringify(errResponse));
              $rootScope.authenticated = false;
              $scope.error = true;
              

          }); 
   };
  });
