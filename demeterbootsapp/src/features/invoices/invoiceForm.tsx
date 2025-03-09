
import React, { useEffect, useState} from 'react';
import { useSearchParams } from 'react-router-dom';
import TextField from '../../components/elements/textfield';
import { Button } from '../../components/elements/button';
import Checkbox from '../../components/elements/checkbox';
import { InvoiceLineTable} from '../../components/elements/table';
import { Invoice, InvoiceLine } from '../../components/interfaces/Invoice';
import { getInvoiceID, getInvoiceLineByInvoiceID } from '../../services/invoices';

const InvoiceForm: React.FC = () => {
    const today = new Date();
    const [searchParams] = useSearchParams();
    const isNew = searchParams.get("view") === "new";
    const invoiceId = searchParams.get("view");

    const [lineInfo, setLineInfo] = useState<InvoiceLine[]>([]);
    const [invoiceInfo, setInvoiceInfo] = useState<Invoice>()
    useEffect(() => {
        if (!isNew) {
            fetchInvoice(invoiceId);
        }
    }, [isNew]);

    const fetchInvoice = async (invoiceID: string | null) => {
        try {
            if (!invoiceID) {
                throw new Error("Invoice ID is missing");
            }

            const invoiceData = await getInvoiceID(invoiceID);
            setInvoiceInfo(invoiceData);

            const lineData = await getInvoiceLineByInvoiceID(invoiceID);
            setLineInfo(lineData);
        } catch (error) {
            console.error("Error fetching invoice details and lines: ", error);
        }
    }

    const openDocument = () => {

    }

    return (
        <div className='invoice-form'>
            <form>
                <div className="column-left">
                    <TextField label="Customer Name" value={invoiceInfo?.customerId} />
                    <TextField type="date" label="Invoice Date" value={invoiceInfo?.invoiceDate} />
                    <TextField label="Total" value={invoiceInfo?.total} />
                    <TextField label="Status" value={invoiceInfo?.status} />
                </div>
                <div className="column-right">
                    <Checkbox label="Is invoice paid?" defaultChecked={invoiceInfo?.isPaid} />
                    <TextField label="Payment Type" value={invoiceInfo?.paymentType} />
                    <TextField type="date" label="Payment Date" value={invoiceInfo?.paymentDate} />
                    <Button type="submit" variant="primary">Update</Button>
                </div>
            </form>
            <Button variant="primary" type="button" onClick={openDocument}>Open Document</Button>           
            <InvoiceLineTable data={lineInfo} /> 
        </div>
    );
};

export default InvoiceForm;