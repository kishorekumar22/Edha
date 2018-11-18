'use strict';
 
app.factory('validationService', function(){
 
    return {
	    isEmptyOrNull: function(input) {
	        return input == undefined || input.length == 0;
    },

	    isInvalidPhoneNumber : function(input){
		return input == undefined || input.length == 0 || input.length >10 || (!(/^\d{10}$/).test(input));
    },

    	    isInvalidEmailId : function(input){
		return input == undefined || input.length == 0 || (!(/^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/i).test(input));
   },


  validateUser : function(user){
        if(this.isEmptyOrNull(user.fullName)){
          return "Invalid Name!";
        }
         if(this.isEmptyOrNull(user.username)){
          return "Invalid username!";
        }
         if(user.userAddress == undefined || this.isEmptyOrNull(user.userAddress.streetName)){
          return "Invalid Street name!";
        }
         if(user.userAddress == undefined || this.isEmptyOrNull(user.userAddress.city)){
          return "Invalid City!";
        } 
        if(this.isInvalidPhoneNumber(user.phoneNumber)){
          return "Invalid Phonenumber!";
        }
        if(this.isInvalidEmailId(user.emailId)){
          return "Invalid EmailId!";
        }
        if(this.isEmptyOrNull(user.role)){
          return "Please enter Role!";
        }
         if(this.isEmptyOrNull(user.active)){
          return "Please enter Status!";
	}
	return undefined;
      }
}
});
