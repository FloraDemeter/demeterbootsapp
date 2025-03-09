import React, { useState, useEffect } from 'react';
import { OrderTable } from '../../components/elements/table';
import { Order } from '../../components/interfaces/Order';
import { getOrders } from '../../services/orders';

const OrderList: React.FC = () => {
    const [orders, setOrders] = useState<Order[]>([]);

    const fetchOrders = async () => {
        try {
            const data = await getOrders();
            setOrders(data);
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