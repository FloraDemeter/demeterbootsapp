
import React, { useEffect, useState} from 'react';
import TextField from '../../components/elements/textfield';
import { InvoiceLineTable} from '../../components/elements/table';
import Checkbox from '../../components/elements/checkbox';
import { Button } from '../../components/elements/button';

const InvoiceForm: React.FC = () => {

    const defaultInvoiceInfo = { 
        CustomerName: "", 
        Status: "", 
        Payment: "", 
        Total: 0, 
        InvoiceDate: Date.now(), 
        PaymentDate: "",
        IsPaid: true 
    };

    const dummyInvoiceData = { 
        CustomerName: "John Doe", 
        Status: "In Process", 
        Payment: "Card", 
        Total: 5412, 
        InvoiceDate: Date.now(), 
        PaymentDate: "",
        IsPaid: true 
    };

    const defaultLineInfo: any[] = [];
    const dummyLineData = [
        { 'Task ID': "O00000006", Price: 205 },
        { 'Task ID': "R00000005", Price: 136 }
    ];
    const [lineInfo, setLineInfo] = useState(defaultLineInfo);
    const [invoiceInfo, setInvoiceInfo] = useState(defaultInvoiceInfo);

    useEffect(() => {
        setLineInfo(dummyLineData);
        setInvoiceInfo(dummyInvoiceData);
    });

    return (
        <div className='invoice-form'>
            <form>
                <div className="column-left">
                    <TextField label="Customer Name" value={invoiceInfo.CustomerName} />
                    <TextField label="Status" value={invoiceInfo.Status} />
                    <TextField label="Payment Type" value={invoiceInfo.Payment} />
                    <TextField label="Total" value={invoiceInfo.Total} />
                </div>
                <div className="column-right">
                    <TextField type="date" label="Invoice Date" value={invoiceInfo.InvoiceDate} />
                    <TextField type="date" label="Payment Date" value={invoiceInfo.PaymentDate} />
                    <Checkbox label="Is invoice paid?" checked={invoiceInfo.IsPaid} />
                    <Button type="submit" variant="primary">Update</Button>
                </div>
            </form>
            <InvoiceLineTable data={lineInfo} /> 
            <Button variant="primary">Open Document</Button>           
        </div>
    );
};

export default InvoiceForm;