'use strict';
 
app.factory('ordersService', ['$http', '$q','CONSTANTS', function($http,$q,c){
 
    return {
    listOrders: function() {
        return $http.get(c.GET_ORDERS_URL)
    },
    listOrdersByStatus : function(status){
    	return $http.get(c.GET_ORDERS_BY_STATUS + status)
    },
    listOrdersByFilter : function(filter){
    	return $http.get(c.GET_ORDERS_BY_FILTER + filter)
    },
    placeNewOrder : function(order){
    	return $http.post(c.POST_PLACE_NEW_ORDER,order)
    },
    updateOrderStaus : function(orderid,status){
    	return $http.get(c.UPDATE_ORDER_STATUS + orderid + '?newStatus=' + status )
    },
    listMyOrders : function(username){
    	return $http.get(c.GET_MY_ORDERS + username)
    },
    listMyClosedOrders : function(username){
    	return $http.get(c.GET_MY_CLOSED_ORDERS + username)
    },
    addPaymentToOrder : function(payment,orderId){
        return $http.post(c.POST_ADD_PAYMENT + orderId, payment)
    }
};

}]);
