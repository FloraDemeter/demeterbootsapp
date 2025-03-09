import axios from 'axios';

const BASE_URL = 'http://localhost:8080/api/employees';

export const getEmployees = async() => {
    try {
        const response = await axios.get(`${BASE_URL}`, {
            withCredentials: true
        });
        return response.data;
    } catch(error) {
        console.error("API call failed to get employees: ", error);
        throw error;
    }
};

export const getEmployeeID = async(employeeID: string) => {
    try {
        const response = await axios.get(`${BASE_URL}/${employeeID}`, {
            withCredentials: true
        });
        return response.data;
    } catch (error) {
        console.error("API call failed to get employee info: ", error);
        throw error;
    }
}

export const getJobsByEmployeeID = async(employeeID: string) => {
    try {
        const response = await axios.get(`${BASE_URL}/jobs/${employeeID}`, {
            withCredentials: true
        });
        return response.data;
    } catch (error) {
        console.error("API call failed to get jobs: ", error);
        throw error;
    }
}