import axios from 'axios';

const BASE_URL = 'http://localhost:8080/api/invoices';

export const getInvoices = async() => {
    try {
        const response = await axios.get(`${BASE_URL}`, {
            withCredentials: true
        });
        return response.data;
    } catch(error) {
        console.error("API call failed to get invoices: ", error);
        throw error;
    }
};

export const getInvoiceID = async(invoiceID: string) => {
    try {
        const response = await axios.get(`${BASE_URL}/${invoiceID}`, {
            withCredentials: true
        });
        return response.data;
    } catch (error) {
        console.error("API call failed to get invoice info: ", error);
        throw error;
    }
}

export const getInvoiceLineByInvoiceID = async(invoiceID: string) => {
    try {
        const response = await axios.get(`${BASE_URL}/invoiceline/${invoiceID}`, {
            withCredentials: true
        });
        return response.data;
    } catch (error) {
        console.error("API call failed to get invoice lines: ", error);
        throw error;
    }
}