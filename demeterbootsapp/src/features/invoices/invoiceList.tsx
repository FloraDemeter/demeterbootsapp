import React, { useState, useEffect } from 'react';
import { InvoiceTable } from '../../components/elements/table';

const InvoiceList: React.FC = () => {
   const dummyInvoiceData = [
           { ID: 'I00000001', 'Customer Name': 'John Doe', Status: 'Payment received', Total: '250' , 'Is Payment Made?': 'Yes'},
           { ID: 'I00000002', 'Customer Name': 'Jane Doe', Status: 'Awaiting payment', Total: '655' , 'Is Payment Made?': 'No'}
         ];
   
       const [invoices, setInvoices] = useState(dummyInvoiceData);
   
       const fetchInvoices = async () => {
           try {
               setInvoices(dummyInvoiceData);
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
}

export default InvoiceList;