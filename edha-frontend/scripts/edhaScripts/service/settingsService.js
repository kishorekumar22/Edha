'use strict';
 
app.factory('settingsService', ['$http', '$q','CONSTANTS', function($http,$q,c){
 
    return {
    saveExpenseType: function(expenseTypeData) {
        return $http.post(c.POST_SAVE_EXPENSE_TYPE_URL , expenseTypeData)
    },
    fetchExpenseType : function(){
    	return $http.get(c.GET_EXPENSE_TYPES_URL)
    },
    deleteExpenseType : function(expenseTypeData){
    	return $http.post(c.POST_DELETE_EXPENSE_TYPE_URL , expenseTypeData)
    },
    saveDiscountType: function(discountTypeData) {
        return $http.post(c.POST_SAVE_DISCOUNT_TYPE_URL , discountTypeData)
    },
    fetchDiscountType : function(){
    	return $http.get(c.GET_DISCOUNT_TYPES_URL)
    },
    deleteDiscountType : function(discountTypeData){
    	return $http.post(c.POST_DISCOUNT_EXPENSE_TYPE_URL , discountTypeData)
    }
}
}]);
