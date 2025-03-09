import axios from 'axios';

const BASE_URL = 'http://localhost:8080/api/jobs';

export const getJobs = async() => {
    try {
        const response = await axios.get(`${BASE_URL}`, {
            withCredentials: true
        });
        return response.data;
    } catch(error) {
        console.error("API call failed to get jobs: ", error);
        throw error;
    }
};