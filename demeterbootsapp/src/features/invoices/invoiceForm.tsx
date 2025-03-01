
import React, { useEffect, useState} from 'react';
import TextField from '../../components/elements/textfield';
import { InvoiceLineTable} from '../../components/elements/table';
import Checkbox from '../../components/elements/checkbox';
import { Button } from '../../components/elements/button';

const InvoiceForm: React.FC = () => {

    const today = new Date();

    const defaultInvoiceInfo = { 
        CustomerName: "", 
        Status: "", 
        Payment: "", 
        Total: 0, 
        InvoiceDate: today, 
        PaymentDate: "",
        IsPaid: true 
    };

    const dummyInvoiceData = { 
        CustomerName: "John Doe", 
        Status: "In Process", 
        Payment: "Card", 
        Total: 5412, 
        InvoiceDate: today, 
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
    }, []);

    const openDocument = () => {

    }

    return (
        <div className='invoice-form'>
            <form>
                <div className="column-left">
                    <TextField label="Customer Name" value={invoiceInfo.CustomerName} />
                    <TextField type="date" label="Invoice Date" value={invoiceInfo.InvoiceDate.toISOString().split("T")[0]} />
                    <TextField label="Total" value={invoiceInfo.Total} />
                    <TextField label="Status" value={invoiceInfo.Status} />
                </div>
                <div className="column-right">
                    <Checkbox label="Is invoice paid?" defaultChecked={invoiceInfo.IsPaid} />
                    <TextField label="Payment Type" value={invoiceInfo.Payment} />
                    <TextField type="date" label="Payment Date" value={invoiceInfo.PaymentDate} />
                    <Button type="submit" variant="primary">Update</Button>
                </div>
            </form>
            <Button variant="primary" type="button" onClick={openDocument}>Open Document</Button>           
            <InvoiceLineTable data={lineInfo} /> 
        </div>
    );
};

export default InvoiceForm;