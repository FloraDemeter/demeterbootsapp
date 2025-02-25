
import React, { useEffect, useState} from 'react';
import { useSearchParams } from 'react-router-dom';
import TextField from '../../components/elements/textfield';
import { Button } from '../../components/elements/button';
import { OrderLineTable } from '../../components/elements/table';
import OrderLinePopUp  from './orderlinePopup';
import DropDown from '../../components/elements/dropdown';
import Checkbox from '../../components/elements/checkbox';

const OrderForm: React.FC = () => {
    const [searchParams] = useSearchParams();
    const isNew = searchParams.get("view") === "new";

    const today = new Date();
    const predictedFinishDate = new Date();
    predictedFinishDate.setDate(today.getDate() + 21);
    
    const defaultInfo = { 
        Customer: "", 
        Location: "", 
        Status: "", 
        Total: "",
        isWarrantyAcccepted: false,
        Date: today,
        PredictedFinish: predictedFinishDate 
    };
    
    const dummyOrderData = { 
        Customer: "C000000001", 
        Location: "London", 
        Status: "In Progress", 
        Total: "265",
        isWarrantyAcccepted: true,
        Date: today,
        PredictedFinish: predictedFinishDate 
    };
    

    const defaultLineInfo: any[] = [];
    const dummyLineData = [
        { Type: "Boots", Style: "testing testing", Leather: "red", Price: 500, Notes: "make it extra wide"},
        { Type: "Chaps", Style: "testing testing", Leather: "black", Price: 200, Notes: "tessst"}
    ];
    const [order, setOrderInfo] = useState(defaultInfo);
    const [orderline, setOrderLineInfo] = useState(defaultLineInfo);

    useEffect(() => {
        if (!isNew) {
            setOrderInfo(dummyOrderData);
            setOrderLineInfo(dummyLineData);
            setSelectedCustomerType(dummyOrderData.Customer);
            setSelectedStatus("1");
        }
    }, [isNew]);

    const [selectedCustomerType, setSelectedCustomerType] = useState<string>("Please select one");
    const [selectedStatus, setSelectedStatus] = useState<string>("Please select one");

    const [isAddPopUpOpen, setIsAddPopUpOpen] = useState(false);
    const [isEditPopUpOpen, setIsEditPopUpOpen] = useState(false);

    const customers = [
        {value:"", label:"Please select one"},
        {value:"C000000001", label:"Jon Doe"},
        {value:"C000000002", label:"Jane Doe"},
    ]

    const statuses = [
        {value:"", label:"Please select one"},
        {value:"1", label:"Finished"},
        {value:"2", label:"Preprocessing"},
        {value:"3", label:"In Progress"},
    ]

    const deleteOrderLine = () => {};
    const generateNewInvoice = () => {};

    return (
        <div className='order-form'>
            <form>
                <div className="column-left">
                    <DropDown label="Customer" options={customers} selectedValue={selectedCustomerType} onChange={setSelectedCustomerType} />
                    <TextField label="Order Date" value={order.Date.toISOString().split("T")[0]} type="date" />
                    <TextField label="Predicted Finish Date" value={order.PredictedFinish.toISOString().split("T")[0]} type="date" />
                </div>
                <div className="column-right">
                    <DropDown label="Status" options={statuses} selectedValue={selectedStatus} onChange={setSelectedStatus} />
                    <TextField label="Total" value={order.Total} />
                    <Checkbox label="Is warranty Accepted" checked={order.isWarrantyAcccepted} />
                </div>
                <Button type="submit" variant="primary">Save</Button>
            </form>
            <OrderLineTable data={orderline} />
            <Button variant="primary" type="button" onClick={() => setIsAddPopUpOpen(true)}>Add new order line</Button>
            <OrderLinePopUp isPopUpOpen={isAddPopUpOpen} setIsPopUpOpen={setIsAddPopUpOpen} isAdd={true} />
            <Button variant="primary" type="button" onClick={() => setIsEditPopUpOpen(true)}>Edit new order line</Button>
            <OrderLinePopUp isPopUpOpen={isEditPopUpOpen} setIsPopUpOpen={setIsEditPopUpOpen} isAdd={false} />
            <Button variant="primary" type="button" onClick={() => deleteOrderLine()}>Delete new order line</Button>
            <Button variant="primary" type="button" onClick={() => generateNewInvoice()}>Generate new invoice</Button>
            {/* <GenerateInvoicePopUp isPopUpOpen={isInvoicePopUpOpen} setIsPopUpOpen={setIsInvoicePopUpOpen} /> */}
            
        </div>
    );
};

export default OrderForm;