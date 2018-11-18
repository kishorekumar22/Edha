'use strict';
app.controller('OrdersController', function($scope,ordersService,productsService,customerService,$rootScope) {
    $scope.errorMessage = "";
    $scope.orders = [];
    $scope.products=[];
    $scope.modalData = {};
    $scope.customers =[];
    $scope.newOrder ={};
    $scope.productQtyInOrder = "";
    $scope.customerForOrder ="";
    $scope.productInOrder ={};
    var username = JSON.parse(localStorage.currentUser).username;
    $scope.filter ={"customerName" : "" , "inchargeName" :""};
    $scope.getOrdersList = function(){
        ordersService.listOrders()
        .then(
           function(response) {
              $scope.orders = response.data;
              $rootScope.authenticated = true;
          },
          function(errResponse){
            $rootScope.checkAuth(errResponse);
              console.error('Error while getting Orders');
              $scope.errorMessage = "Error in getting Orders.Contact support!";
          }); 
      };


    $scope.getOrdersByStatus = function(orderStatus){
      ordersService.listOrdersByStatus(orderStatus)
        .then(
           function(response) {
              $scope.orders = response.data;
              $rootScope.authenticated = true;
          },
          function(errResponse){
            $rootScope.checkAuth(errResponse);
              console.error('Error while getting Orders');
              $scope.errorMessage = "Error in getting Orders.Contact support!";
          }); 
    };
    $scope.getProductsList = function(){
      customerService.getAssignedCustomer(username)
      .then(
           function(response) {
              $scope.customers = response.data;
              $rootScope.authenticated = true;
          },
          function(errResponse){
            $rootScope.checkAuth(errResponse);
              console.error('Error while getting Customers - new order');
              
          }); 
    };

    $scope.getCustomerList = function(){
      productsService.getProductsForOrder()
      .then(
           function(response) {
              $scope.products = response.data;
              $rootScope.authenticated = true;
          },
          function(errResponse){
            $rootScope.checkAuth(errResponse);
              console.error('Error while getting products - new order');
              
          }); 
    };
      $scope.getSum = function(products){
        var total = 0;
        angular.forEach(products,function(p){
      total += p.qty * p.product.price;

    });
        return total;
    };
    $scope.addProduct = function(){
      var prodInCart = JSON.parse($scope.productInOrder);
      if($scope.newOrder.products){
        checkAndAddProductInCart(prodInCart);
      }else{
        $scope.newOrder.products = [];
        $scope.newOrder.products.push({"qty" : $scope.productQtyInOrder,"product":{"id":prodInCart.id,"name":prodInCart.productName,"price":prodInCart.price}})
      }
    $scope.newOrder.discount = $scope.discountOffered;
    $scope.newOrder.customer = {"id" : $scope.customerForOrder};
    $scope.productQtyInOrder = "";
    $scope.productInOrder ="";
    //hard coded to be handled in back end
    $scope.newOrder.user={"username": username};
    
    //alert(JSON.stringify($scope.newOrder));

    };

    $scope.updateOrderStatus = function(order,newStatus){
      ordersService.updateOrderStaus(order.id,newStatus)
        .then(
           function(response) {
              order.status = response.data.status;
              $rootScope.authenticated = true;
          },
          function(errResponse){
              $rootScope.checkAuth(errResponse);
              console.error('Error while updating Order status');
              $scope.errorMessage = "Error in updated Order status.Contact support!";
          }); 

    };  

    $scope.placeNewOrder = function(){
      //Hard coded
      $scope.newOrder.user = {};
      $scope.newOrder.user.username =JSON.parse(localStorage.currentUser).username;

      //validation


        ordersService.placeNewOrder($scope.newOrder)
        .then(
           function(response) {
              $scope.getOrdersList();
              $rootScope.authenticated = true;
          },
          function(errResponse){
            $rootScope.checkAuth(errResponse);
              console.error('Error while placing Orders');
              $scope.errorMessage = "Error in placing Orders.Contact support!";
          }); 
      };

    $scope.resetProducts = function(){
      $scope.productQtyInOrder = 0;
      $scope.productInOrder ="";
      $scope.newOrder.products=[];
    }

    var checkAndAddProductInCart = function(prodInCart){
      var isPresent = false;
      angular.forEach($scope.newOrder.products ,function(p){
        if(p.product.id == prodInCart.id){
          isPresent = true;
          p.qty +=  $scope.productQtyInOrder;
          }
      });
      if(!isPresent){
        $scope.newOrder.products.push({"qty" : $scope.productQtyInOrder,"product":{"id":prodInCart.id,"name":prodInCart.productName,"price":prodInCart.price}})
      }
    };
    var checkSize = function(d){
      if(d < 10)return '0'+d;
      return d;
    }

    $scope.getOrdersByFilter = function(){
      if($scope.filter.customerName.length > 0 || $scope.filter.periodFrom || $scope.filter.inchargeName.length > 0){
        var filter = '?';
        var period = "";
        if($scope.filter.periodFrom){
          var from = $scope.filter.periodFrom;
          period = checkSize(from.getDate()) + '/' + checkSize(from.getMonth() + 1)  +'/' + from.getFullYear();
        }
        if($scope.filter.periodTo){
          var to = $scope.filter.periodTo;
          period += '-' + checkSize(to.getDate()) + '/' + checkSize(to.getMonth() + 1)  +'/' + to.getFullYear();
        }
        if($scope.filter.customerName.length > 0)
          filter += 'customerName=' +$scope.filter.customerName + '&'; 
        if(period.length > 0)
          filter += 'period=' + period + '&';
        if($scope.filter.inchargeName.length > 0)
          filter += 'inchargeName=' +$scope.filter.inchargeName + '&';

        ordersService.listOrdersByFilter(filter.substr(0,filter.length - 1))
          .then(
             function(response) {
                $scope.orders = response.data;
                $rootScope.authenticated = true;
            },
            function(errResponse){
              $rootScope.checkAuth(errResponse);
                console.error('Error while getting Orders');
                $scope.errorMessage = "Error in getting Orders.Contact support!";
            }); 
      }else{
        $scope.getOrdersList();
      }
    };

    $scope.getOrdersOfToday = function(){
      var today = new Date();
      var filter = '?period=';
      var dateString = checkSize(today.getDate()) + '/' + checkSize(today.getMonth() + 1)  +'/' + today.getFullYear();
      filter += dateString;
      ordersService.listOrdersByFilter(filter)
          .then(
             function(response) {
                $scope.orders = response.data;
                $rootScope.authenticated = true;
            },
            function(errResponse){
              $rootScope.checkAuth(errResponse);
                console.error('Error while getting Orders');
                $scope.errorMessage = "Error in getting Orders.Contact support!";
            }); 
    }
    $scope.getOrdersOfThisMonth = function(){
      var today = new Date();
      var filter = '?period=01/' + checkSize(today.getMonth() + 1)  +'/' + today.getFullYear() + '-';
      var dateString = checkSize(today.getDate()) + '/' + checkSize(today.getMonth() + 1)  +'/' + today.getFullYear();
      filter += dateString;

      ordersService.listOrdersByFilter(filter)
          .then(
             function(response) {
                $scope.orders = response.data;
                $rootScope.authenticated = true;
            },
            function(errResponse){
              $rootScope.checkAuth(errResponse);
                console.error('Error while getting Orders');
                $scope.errorMessage = "Error in getting Orders.Contact support!";
            }); 
    }

    
//initializes the order list
$scope.getOrdersList();
  });
