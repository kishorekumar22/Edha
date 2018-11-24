'use strict';
app.controller('ProductsController', function($scope,productsService,$rootScope,validationService) {
    $scope.errorMessage = "";
	$scope.products = [];
    $scope.modalData = {};
    //$scope.editedProduct = {};
    $scope.getProductsList = function(){
        productsService.getProducts()
        .then(
           function(response) {
              $scope.products = response.data;
              $rootScope.authenticated = true;
          },
          function(errResponse){
            $rootScope.checkAuth(errResponse);
              console.error('Error while getting products');
              $scope.errorMessage = "Error in getting Products.Contact support!";
          });     
      };

    $scope.modifyProduct = function(p){
       $scope.modalData = JSON.parse(JSON.stringify(p))
        $scope.modalData.isEdit = true;
       };

    //Also used for adding new products
    $scope.saveEditedProduct = function(){
    var validation = validationService.validateProduct($scope.modalData);
        if(validation && validation.length > 0){
          $scope.modalData.errorMessage = validation;
        }else{
        $('#productModal').modal("hide");
        productsService.saveProduct($scope.modalData)
        .then(
           function(response) {
              $scope.getProductsList();
              $rootScope.authenticated = true;
          },
          function(errResponse){

              console.error('Error while saving product after edit');
              $scope.errorMessage = "Error in saving the product";
              $scope.getProductsList();
              $rootScope.checkAuth(errResponse);
          }); 
      }
    };

    $scope.deleteProduct = function() { 
        productsService.deleteProduct($scope.modalData.id)
        .then(
           function(response) {
              $scope.getProductsList();
              $rootScope.authenticated = true;
          },
          function(errResponse){
              console.error('Error while deleting product');
              $scope.errorMessage = "Error in deleting the product";
              $scope.getProductsList();
              $rootScope.checkAuth(errResponse);
          }); 
    };
$scope.getProductsList();
});
