'use strict';
app.constant('CONSTANTS', (function() {
  var MAIN_URL = 'https://edha.herokuapp.com/edha/api/';
  //var MAIN_URL = 'http://localhost:7779/edha/api/';
  var PRODUCTS = 'products/';
  var CUSTOMERS = 'customer/';
  var ORDERS = 'orders/';
  var USERS = 'user/';
  return {
	REST_URL: MAIN_URL,
	LOGIN_URL : MAIN_URL + 'user/login',
	PRODUCTS_GET_URL : MAIN_URL + PRODUCTS + 'list' ,
	PRODUCT_SAVE_URL : MAIN_URL + PRODUCTS + 'edit/',
	PRODUCT_ADD_URL : MAIN_URL + PRODUCTS + 'add',
	PRODUCT_DELETE_URL : MAIN_URL + PRODUCTS + 'remove/',
	CUSTOMER_GET_URL : MAIN_URL + CUSTOMERS + 'list',
	GET_ORDERS_URL : MAIN_URL + ORDERS + 'list',
	PRODUCTS_ORDER_GET_URL : MAIN_URL + PRODUCTS + 'list-for-order' ,
	ASSIGNED_CUSTOMER_GET_URL : MAIN_URL + CUSTOMERS + 'list-assigned-customer/',
	GET_ORDERS_BY_STATUS : MAIN_URL + ORDERS + 'list-by-status/',
	GET_ORDERS_BY_FILTER : MAIN_URL + ORDERS + 'list-orders-by-filter/',
	POST_PLACE_NEW_ORDER : MAIN_URL + ORDERS + 'add',
	UPDATE_ORDER_STATUS : MAIN_URL + ORDERS + 'update-order-status/',
	GET_CUSTOMERS_BY_FILTER : MAIN_URL + CUSTOMERS + 'list-customers-by-filter/',
	ADD_NEW_CUSTOMER : MAIN_URL + CUSTOMERS + 'add',
	GET_VALIDATE_USERNAME_URL : MAIN_URL + USERS + 'check-username/',
	POST_SAVE_USER_URL : MAIN_URL + USERS + 'add',
	POST_SAVE_EDITEDUSER_URL : MAIN_URL + USERS + 'edit',
	GET_USERS_LIST : MAIN_URL + USERS + 'list',
	GET_USER_BY_USERNAME : MAIN_URL + USERS + 'get/',
	GET_ACTIVE_USERS  : MAIN_URL + USERS + 'list-active-users',
	GET_MY_ORDERS : MAIN_URL + ORDERS + 'list-my-orders/',
	GET_MY_CLOSED_ORDERS : MAIN_URL + ORDERS + 'list-my-closed-orders/',
	LOGOUT_URL : MAIN_URL +  USERS +  'logout',
	POST_UPDATE_PASWORD : MAIN_URL + USERS + 'change-password',
	POST_SAVE_EXPENSE : MAIN_URL + USERS + 'save-expense',
	GET_MONTHLY_EXPENSE : MAIN_URL + USERS + 'get-month-expense/'
  }
})());
