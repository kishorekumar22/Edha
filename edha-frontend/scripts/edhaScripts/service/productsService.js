'use strict';
 
app.factory('productsService', ['$http', '$q','CONSTANTS', function($http,$q,c){
 
    return {
    getProducts: function() {
        return $http.get(c.PRODUCTS_GET_URL)
    },
    
    getProductsForOrder: function() {
        return $http.get(c.PRODUCTS_ORDER_GET_URL)
    },
    saveProduct : function(product){
    	var id = product.id
    	delete product.id;
    	if(id)
    		return $http.post(c.PRODUCT_SAVE_URL + id, product)
    	else
    		return $http.post(c.PRODUCT_ADD_URL, product)
    },
    deleteProduct : function(id){
    	return $http.get(c.PRODUCT_DELETE_URL + id)
    }   

};

}]);
