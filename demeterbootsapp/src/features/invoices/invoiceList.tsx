import React, { useState, useEffect } from 'react';
import { InvoiceTable } from '../../components/elements/table';
import { getInvoices } from '../../services/invoices';
import { Invoice } from '../../components/interfaces/Invoice';

const InvoiceList: React.FC = () => {
    const [invoices, setInvoices] = useState<Invoice[]>([]);

    const fetchInvoices = async () => {
        try {
            const data = await getInvoices();
            setInvoices(data);
        } catch (error) {
            console.error("Error fetching invoices:", error);
        }
    };

    useEffect(() => {
        fetchInvoices();
    }, []);
        
    return (
        <div className="invoice-list">
            <InvoiceTable data={invoices}/>
        </div>
    )
};

export default InvoiceList;