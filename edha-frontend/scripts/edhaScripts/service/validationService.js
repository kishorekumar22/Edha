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

          validatePAN : function(input){
    return !(/^[a-zA-Z]{3}[PCHFATBLJG]{1}[a-zA-Z]{1}[0-9]{4}[a-zA-Z]{1}$/.test(input));
  },

          validateGSTIN : function(input){
      return !(/^[0-9]{2}[a-zA-Z]{3}[PCHFATBLJG]{1}[a-zA-Z]{1}[0-9]{4}[a-zA-Z]{1}[0-9]{1}[Z]{1}[A-Z0-9]{1}$/.test(input));
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
        if(this.isEmptyOrNull(user.fathersName)){
          return "Invalid Father/Husband Name!";
        }
        if(user.pan != undefined && user.pan.length > 0 &&this.validatePAN(user.pan)){
          return "Invalid PAN!"
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
      },


  validateOrder : function(order){
    if(this.isEmptyOrNull(order.customer)){
      return "Please select a customer for order";
    }
    if(this.isEmptyOrNull(order.customer)){
      return "Please select a customer for order";
    }
    if(order.products == undefined || order.products.length == 0 ){
      return "Please select atleast one product";
    }
    if(order.payments.length > 0 && this.isEmptyOrNull(order.payments[0].amount)){
      return "Please payment amount value";
    }
    return undefined;
  },

  validatePayment : function(payment){
    if(this.isEmptyOrNull(payment.orderId)){
      return "Invalid OrderId! could not proceed";
    }
    if(this.isEmptyOrNull(payment.date)){
      return "Please enter date of Payment";
    }
    if(this.isEmptyOrNull(payment.amount)){
      return "Please enter Payment amount";
    }
    if(this.isEmptyOrNull(payment.mode)){
      return "Please select Payment mode";
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
        if(customer.emailId != undefined &&  customer.emailId.length > 0 && this.isInvalidEmailId(customer.emailId)){
          return "Invalid EmailId!";
        }
	     if(this.isInvalidPhoneNumberNonMadatory(customer.phoneNumber2)){
          return "Invalid Contact Number2!";
        }
        if(customer.gstin != undefined && customer.gstin.length > 0 && this.validateGSTIN(customer.gstin)){
          return "Invalid GSTIN!";
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
