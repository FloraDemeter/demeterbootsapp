import React, { useState, useEffect } from 'react';
import { getCustomers } from "../../services/customers";
import { CustomerTable } from '../../components/elements/table';
import { Customer } from '../../components/interfaces/Customer';

const CustomerList: React.FC = () => {
    const [customers, setCustomers] = useState<Customer[]>([]);

    const fetchCustomers = async () => {
        try {
            const data = await getCustomers();
            setCustomers(data);
        } catch (error) {
            console.error("Error fetching customers:", error);
        }
    };

    useEffect(() => {
        fetchCustomers();
    }, []);

    return (
        <div className="customer-list">
            <CustomerTable data={customers} />
        </div>
    );
};

export default CustomerList;
