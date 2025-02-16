import axios from 'axios';

const BASE_URL = 'http://localhost:3000';

export const fetchData = async() => {
    try {
        const response = await axios.get(`${BASE_URL}/data`);
        return response.data;
    } catch(error) {
        console.error("API call failed: ", error);
        throw error;
    }
};