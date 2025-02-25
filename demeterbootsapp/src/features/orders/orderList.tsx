import React, { useState, useEffect } from 'react';
import { OrderTable } from '../../components/elements/table';
const OrderList: React.FC = () => {
    const dummyOrderData = [
        { ID: 'O00000001', 'Customer Name': 'John Doe', Location: 'London',Status: "Preprocessing", Total: 500 },
        { ID: 'O00000002', 'Customer Name': 'Jane Doe', Location: 'Chelmsford',Status: "Making", Total: 250 }
      ];

    const [orders, setOrders] = useState(dummyOrderData);

    const fetchOrders = async () => {
        try {
            setOrders(dummyOrderData);
        } catch (error) {
            console.error("Error fetching orders:", error);
        }
    };

    useEffect(() => {
        fetchOrders();
    }, []);
      
    return (
        <div className="order-list">
            <OrderTable data={orders}/>
        </div>
    )   
}

export default OrderList;