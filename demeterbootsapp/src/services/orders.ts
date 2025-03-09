import axios from 'axios';

const BASE_URL = 'http://localhost:8080/api/orders';

export const getOrders = async() => {
    try {
        const response = await axios.get(`${BASE_URL}`, {
            withCredentials: true
        });
        return response.data;
    } catch(error) {
        console.error("API call failed to get orders: ", error);
        throw error;
    }
};

export const getOrderID = async(orderID: string) => {
    try {
        const response = await axios.get(`${BASE_URL}/${orderID}`, {
            withCredentials: true
        });
        return response.data;
    } catch (error) {
        console.error("API call failed to get order info: ", error);
        throw error;
    }
}

export const getOrderLineByOrderID = async(orderID: string) => {
    try {
        const response = await axios.get(`${BASE_URL}/orderline/${orderID}`, {
            withCredentials: true
        });
        return response.data;
    } catch (error) {
        console.error("API call failed to get order lines: ", error);
        throw error;
    }
}