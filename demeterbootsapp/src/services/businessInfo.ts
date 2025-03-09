import axios from 'axios';

const BASE_URL = 'http://localhost:8080/api/businessinfo';

export const getStatuses = async() => {
    try {
        const response = await axios.get(`${BASE_URL}/status`, {
            withCredentials: true
        });
        return response.data;
    } catch(error) {
        console.error("API call failed to get status: ", error);
        throw error;
    }
};

export const getPaymentTypes = async() => {
    try {
        const response = await axios.get(`${BASE_URL}/paymenttypes`, {
            withCredentials: true
        });
        return response.data;
    } catch(error) {
        console.error("API call failed to get payment types: ", error);
        throw error;
    }
};