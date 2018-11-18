'use strict';
app.controller('ProductsController', function($scope,productsService,$rootScope) {
    $scope.errorMessage = "";
	$scope.products = [];
    $scope.modalData = {};
    $scope.editedProduct = {};
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
        $scope.modalData.name = p.productName;
        $scope.modalData.id = p.id;
        $scope.modalData.description = p.productDescription;
        $scope.modalData.inventoryQty = p.inventoryQty;
        $scope.modalData.price = p.price;
        $scope.editedProduct = p;
        $scope.modalData.isEdit = true;
       };

    //Also used for adding new products
    $scope.saveEditedProduct = function(){
        $scope.editedProduct.productName = $scope.modalData.name; 
        $scope.editedProduct.productDescription = $scope.modalData.description;
        $scope.editedProduct.inventoryQty = $scope.modalData.inventoryQty;
        $scope.editedProduct.price = $scope.modalData.price;
        productsService.saveProduct($scope.editedProduct)
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
