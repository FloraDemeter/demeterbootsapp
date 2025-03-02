import axios from 'axios';

const BASE_URL = 'http://localhost:3000/api';

export const getCustomers = async() => {
    try {
        const response = await axios.get(`${BASE_URL}/customers`, {
            withCredentials: true
        });
        return response.data;
    } catch(error) {
        console.error("API call failed: ", error);
        throw error;
    }
};