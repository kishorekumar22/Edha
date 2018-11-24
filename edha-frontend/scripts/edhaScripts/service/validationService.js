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
	isInvalidPhoneNumberNonMadatory : function(input){
		return input != undefined && input.length > 0 && (!(/^\d{10}$/).test(input));

	},

  validateProduct : function(product){
    if(this.isEmptyOrNull(product.productName)){
      return "Invalid ProductName!";
    }
    if(this.isEmptyOrNull(product.price)){
      return "Invalid ProductPrice!";
    }
    if(this.isEmptyOrNull(product.inventoryQty)){
      return "Invalid Product Qty!";
    }
    return undefined;
  },

  validateUser : function(user){
        if(this.isEmptyOrNull(user.fullName)){
          return "Invalid Name!";
        }
        if(this.isInvalidEmailId(user.emailId)){
          return "Invalid EmailId!";
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
        
        if(this.isEmptyOrNull(user.role)){
          return "Please enter Role!";
        }
         if(this.isEmptyOrNull(user.active)){
          return "Please enter Status!";
	}
	return undefined;
      },

	validateCustomer : function(customer){
        if(this.isEmptyOrNull(customer.customerName)){
          return "Invalid Customer Name!";
        }
         if(customer.customerAddress == undefined || this.isEmptyOrNull(customer.customerAddress.streetName)){
          return "Invalid Street name!";
        }
         if(customer.customerAddress == undefined || this.isEmptyOrNull(customer.customerAddress.city)){
          return "Invalid City!";
        } 
        if(this.isInvalidPhoneNumber(customer.phoneNumber1)){
          return "Invalid Contact Number 1!";
        }
        if(this.isInvalidEmailId(customer.emailId)){
          return "Invalid EmailId!";
        }
	     if(this.isInvalidPhoneNumberNonMadatory(customer.phoneNumber2)){
          return "Invalid Contact Number2!";
        }
        //if(this.isInvalidEmailId(customer.phoneNumber1)){
          //return "Invalid Contact Number2!";
        //}
        if(!(customer.incharge && customer.incharge.username)){
          return "Please select incharge!";
        }
	return undefined;
      }
}

});
