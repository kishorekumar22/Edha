'use strict';
 
app.factory('usersService', ['$http', '$q','CONSTANTS', function($http,$q,c){
 
    return {
    validateUserName: function(username) {
        return $http.get(c.GET_VALIDATE_USERNAME_URL + username)
    },

    saveUser : function(user){
        return $http.post(c.POST_SAVE_USER_URL , user);
    },

    getUsersList : function(){
        return $http.get(c.GET_USERS_LIST);
    },

    getUserDetails : function(username) {
        return $http.get(c.GET_USER_BY_USERNAME + username);
    },

    getActiveUsers : function(){
        return $http.get(c.GET_ACTIVE_USERS);
    },

    saveEditedUser :function(user){
        return $http.post(c.POST_SAVE_EDITEDUSER_URL , user);
    },

    updatePassword : function(newPwd){
        return $http.post(c.POST_UPDATE_PASWORD , newPwd);
    },
    saveExpense : function(expenses){
        return $http.post(c.POST_SAVE_EXPENSE , expenses);
    },
    getMonthlyExpense : function(user){
        return $http.get(c.GET_MONTHLY_EXPENSE + user);
    }

};

}]);
