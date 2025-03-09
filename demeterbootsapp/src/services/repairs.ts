import axios from 'axios';

const BASE_URL = 'http://localhost:8080/api/repairs';

export const getRepairs = async() => {
    try {
        const response = await axios.get(`${BASE_URL}`, {
            withCredentials: true
        });
        return response.data;
    } catch(error) {
        console.error("API call failed to get repairs: ", error);
        throw error;
    }
};

export const getRepairID = async(repairID: string) => {
    try {
        const response = await axios.get(`${BASE_URL}/${repairID}`, {
            withCredentials: true
        });
        return response.data;
    } catch (error) {
        console.error("API call failed to get repair info: ", error);
        throw error;
    }
}

export const getRepairLineByRepairID = async(repairID: string) => {
    try {
        const response = await axios.get(`${BASE_URL}/repairline/${repairID}`, {
            withCredentials: true
        });
        return response.data;
    } catch (error) {
        console.error("API call failed to get repair lines: ", error);
        throw error;
    }
}