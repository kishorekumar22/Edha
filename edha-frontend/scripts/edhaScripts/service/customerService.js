'use strict';
 
app.factory('customerService', ['$http','CONSTANTS', function($http,c){
 
    return {
    getCustomers: function() {
        return $http.get(c.CUSTOMER_GET_URL)
    },
    getAssignedCustomer: function(username) {
        return $http.get(c.ASSIGNED_CUSTOMER_GET_URL + username)
    },
    getCustomersByFilter : function(filterParams){
        return $http.get(c.GET_CUSTOMERS_BY_FILTER + filterParams)
    },
    addCustomer : function(customer){
        return $http.post(c.ADD_NEW_CUSTOMER, customer)
    }

};

}]);
