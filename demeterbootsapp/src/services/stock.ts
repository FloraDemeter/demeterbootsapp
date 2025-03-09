import axios from 'axios';

const BASE_URL = 'http://localhost:8080/api/stock';

export const getLeathers = async() => {
    try {
        const response = await axios.get(`${BASE_URL}/leathers`, {
            withCredentials: true
        });
        return response.data;
    } catch(error) {
        console.error("API call failed to get leathers: ", error);
        throw error;
    }
};

export const getProductStyles = async() => {
    try {
        const response = await axios.get(`${BASE_URL}/productstyles`, {
            withCredentials: true
        });
        return response.data;
    } catch(error) {
        console.error("API call failed to get product styles: ", error);
        throw error;
    }
};

export const getRepairCategories = async() => {
    try {
        const response = await axios.get(`${BASE_URL}/repaircategories`, {
            withCredentials: true
        });
        return response.data;
    } catch(error) {
        console.error("API call failed to get repair categories: ", error);
        throw error;
    }
};

export const getProductTypes = async() => {
    try {
        const response = await axios.get(`${BASE_URL}/producttypes`, {
            withCredentials: true
        });
        return response.data;
    } catch(error) {
        console.error("API call failed to get product types: ", error);
        throw error;
    }
};