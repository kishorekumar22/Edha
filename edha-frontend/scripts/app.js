var app = angular.module("edhaApp", ['ngRoute']);


app.config(function($routeProvider,$httpProvider) {


    $routeProvider
    .when("/", {
        templateUrl : "login.html",
        controller : "LoginController"
    })
    .when("/login", {
        templateUrl : "login.html",
        controller : "LoginController"
    })
    .when("/customers", {
        templateUrl : "customers.html",
        controller : "CustomerController"
    })
   .when("/products", {
        templateUrl : "products.html",
        controller : "ProductsController"
    })
   .when("/orders", {
        templateUrl : "orders.html",
        controller : "OrdersController"
    })
   .when("/users", {
        templateUrl : "users.html",
        controller : "UsersController"
    })
   .when("/profile/:username", {
        templateUrl : "profile.html",
        controller : "ProfileController"
    })

   .when("/settings", {
        templateUrl : "settings.html",
        controller : "SettingsController"
    })
    .when("/sale", {
        templateUrl : "sales.html"
    });
    $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';
    $httpProvider.interceptors.push('LoadingInterceptor');
    
    //$httpProvider.defaults.headers.common["edha-token"] = 'XMLHttpRequest';
    //$httpProvider.defaults.withCredentials = true
    
});


app.run(['$location','$rootScope','$http','usersService', function(l,r,h,usersService){
    
    if(localStorage.currentUser){
        var user = JSON.parse(localStorage.currentUser);
        h.defaults.headers.common["edha-auth"] = user.token;
        r.username = user.username;
    }
    

  r.checkAuth = function(response){
        if (response.status == 401) {
            r.authenticated = false;
            l.path('/');
        }
        //alert(localStorage.currentUser);
    };

    r.performLogout = function(){
    h.defaults.headers.common["edha-auth"] = '';
    delete localStorage.currentUser; 
    l.path("/");
    r.authenticated = false;
      };

    r.changePassword = function(newPassword2){
    usersService.updatePassword(newPassword2).then(
             function(response) {
                //$scope.user = response.data;
                r.authenticated = true;
            },
            function(errResponse){
              r.checkAuth(errResponse);
            }); 
    };

}]);


app.service('LoadingInterceptor', 
    ['$q', '$rootScope',  
    function($q, $rootScope) {
        'use strict';
 
        return {
            request: function(config) {
                $rootScope.isLoading = true;
                return config;
            },
            requestError: function(rejection) {
                $rootScope.isLoading = false;
                return $q.reject(rejection);
            },
            response: function(response) {
                $rootScope.isLoading = false;
                return response;
            },
            responseError: function(rejection) {
                $rootScope.isLoading = false;
                return $q.reject(rejection);
            }
        };
    }]);


