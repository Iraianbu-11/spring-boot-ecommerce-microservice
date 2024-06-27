import axios from "axios";

const API_ENDPOINT = "http://localhost:8222/api/v1/";

// Product Microservice

export const listProducts = () => axios.get(API_ENDPOINT+"products");


// Customer Microservice

export const addCustomer = (customer) => axios.post(API_ENDPOINT + "customers", customer ,  {
    headers: {
      'Content-Type': 'application/json'
    } });

export const getCustomers = () => axios.get(API_ENDPOINT + "customers");

export const updateCustomer = (customer) => axios.put(API_ENDPOINT + "customers",customer , {
    headers: {
        'Content-Type': 'application/json'
      } 
});

export const isCustomerPresent = (id) => axios.get(API_ENDPOINT + "customers/exists/" + id);

export const deleteCustomer = (id) => axios.delete(API_ENDPOINT + "customers/" + id , {
    headers: {
        'Content-Type': 'application/json'
      } 
});



// Order Microservice

export const listOrders = () => axios.get(API_ENDPOINT + "orders");

export const placeOrder = (order) => axios.post(API_ENDPOINT + "orders", order ,  {
  headers: {
    'Content-Type': 'application/json'
  } });

