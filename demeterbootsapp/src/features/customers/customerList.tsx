import React, { useState, useEffect } from 'react';
import { getCustomers } from "../../services/customers";

interface Customer {
  id: string;
  firstName: string;
  lastName: string;
  street: string;
  postCode: string;
  city: string;
  email: string;
  phone: string;
}

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
            <ul>
                {customers.map((customer, index) => (
                    <li key={index}>
                        <strong>ID:</strong> {customer.id} <br />
                        <strong>Name:</strong> {customer.firstName} {customer.lastName} <br />
                        <strong>Street:</strong> {customer.street} <br />
                        <strong>Post Code:</strong> {customer.postCode} <br />
                        <strong>City:</strong> {customer.city} <br />
                        <strong>Email:</strong> {customer.email} <br />
                        <strong>Phone:</strong> {customer.phone} <br />
                        <hr />
                    </li>
                ))}
            </ul>
        </div>
    );
};

export default CustomerList;
