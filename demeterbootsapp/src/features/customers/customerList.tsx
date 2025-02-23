import React, { useState, useEffect } from 'react';
import { CustomerTable } from '../../components/elements/table';

const CustomerList: React.FC = () => {

    const dummyCustomerData = [
        { ID: 'C00000001', Name: 'John Doe', Phone: '1234567890', City: 'New York' },
        { ID: 'C00000002', Name: 'Jane Doe', Phone: '0123456789', City: 'London' }
      ];

    const [customers, setCustomers] = useState(dummyCustomerData);

    const fetchCustomers = async () => {
        try {
            setCustomers(dummyCustomerData);
        } catch (error) {
            console.error("Error fetching customers:", error);
        }
    };

    useEffect(() => {
        fetchCustomers();
    }, []);
      
    return (
        <div className="customer-list">
            <CustomerTable data={customers}/>
        </div>
    )
}

export default CustomerList;