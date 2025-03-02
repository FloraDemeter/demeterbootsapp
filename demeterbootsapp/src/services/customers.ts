import axios from 'axios';

const BASE_URL = 'http://localhost:3000/api';

export const getCustomers = async() => {
    try {
        const response = await axios.get(`${BASE_URL}/customers`, {
            withCredentials: true
        });
        return response.data;
    } catch(error) {
        console.error("API call failed to get customers: ", error);
        throw error;
    }
};

export const getCustomerID = async(customerID: string) => {
    try {
        const response = await axios.get(`${BASE_URL}/customers/${customerID}`, {
            withCredentials: true
        });
        return response.data;
    } catch (error) {
        console.error("API call failed to get customer info: ", error);
        throw error;
    }
}

export const getMeasurementsByID = async(customerID: string) => {
    try {
        const response = await axios.get(`${BASE_URL}/customers/measurements/${customerID}`, {
            withCredentials: true
        });
        return response.data;
    } catch (error) {
        console.error("API call failed to get measurements: ", error);
        throw error;
    }
}