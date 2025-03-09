
import React, { useEffect, useState} from 'react';
import { useSearchParams } from 'react-router-dom';
import TextField from '../../components/elements/textfield';
import { Button } from '../../components/elements/button';
import { OrderLineTable } from '../../components/elements/table';
import OrderLinePopUp  from './orderlinePopup';
import DropDown from '../../components/elements/dropdown';
import Checkbox from '../../components/elements/checkbox';
import ConfirmPopUp from '../../components/layout/confirmPopUp';
import { Order, OrderLine } from '../../components/interfaces/Order';
import { Customer } from '../../components/interfaces/Customer';
import { getOrderID, getOrderLineByOrderID } from '../../services/orders';
import { getCustomers } from '../../services/customers';
import { Status } from '../../components/interfaces/BusinessInfo';
import { getStatuses } from '../../services/businessInfo';

const OrderForm: React.FC = () => {
    const [searchParams] = useSearchParams();
    const isNew = searchParams.get("view") === "new";
    const orderId = searchParams.get("view");

    const today = new Date();
    
    const [order, setOrderInfo] = useState<Order>();
    const [orderline, setOrderLineInfo] = useState<OrderLine[]>([]);
    const [selectedCustomerType, setSelectedCustomerType] = useState<string>("Please select one");
    const [selectedStatus, setSelectedStatus] = useState<string>("Please select one");

    const [isAddPopUpOpen, setIsAddPopUpOpen] = useState(false);
    const [isEditPopUpOpen, setIsEditPopUpOpen] = useState(false);
    const [isConfirmPopUpOpen, setIsConfirmPopUpOpen] = useState(false);

    const [customers, setCustomerDropDown]= useState<any[]>([]);
    const [statuses, setStatusDropDown]= useState<any[]>([]);

    useEffect(() => {
        if (!isNew) {
            fetchOrder(orderId);
        }
    }, [isNew]);

    const fetchOrder = async (orderId: string | null) => {
        try {
            if (!orderId) {
                throw new Error("Order ID is missing");
            }

            const orderData = await getOrderID(orderId);
            setOrderInfo(orderData);

            const orderLineData = await getOrderLineByOrderID(orderId);
            setOrderLineInfo(orderLineData);

            const customersData = await getCustomers();
            const customersList = customersData.map((customer: Customer) => {
                return {
                    value: customer.id,
                    label: customer.firstName + " " + customer.lastName
                }
            });

            setCustomerDropDown(customersList)

            const statusData = await getStatuses();
            const statusList = statusData.map((status: Status) => {
                return {
                    value: status.id,
                    label: status.description
                }
            });
            setStatusDropDown(statusList);

            setSelectedCustomerType(orderData.customerId);
            setSelectedStatus(orderData.status);

        } catch (error) {
            console.error("Error fetching order details and order lines",error);
        }
    
    };
    
    const deleteOrderLine = () => {};
    const generateNewInvoice = () => {};

    return (
        <div className='order-form'>
            <form>
                <div className="column-left">
                    <DropDown label="Customer" options={customers} selectedValue={selectedCustomerType} onChange={setSelectedCustomerType} />
                    <TextField label="Order Date" value={order?.orderDate} type="date" />
                    <TextField label="Predicted Finish Date" value={order?.predictedFinishDate} type="date" />
                </div>
                <div className="column-right">
                    <DropDown label="Status" options={statuses} selectedValue={selectedStatus} onChange={setSelectedStatus} />
                    <TextField label="Total" value={order?.total} />
                    <Checkbox label="Is warranty Accepted" defaultChecked={order?.isWarrantyAccepted} />
                </div>
                <div className="full-width">
                    <Button type="submit" variant="primary">Save</Button>
                </div>
            </form>
            
            <div className="actions">
                <Button variant="primary" type="button" onClick={() => setIsAddPopUpOpen(true)}>Add new order line</Button>
                <OrderLinePopUp isPopUpOpen={isAddPopUpOpen} setIsPopUpOpen={setIsAddPopUpOpen} isAdd={true} />
                <Button variant="primary" type="button" onClick={() => setIsEditPopUpOpen(true)}>Edit new order line</Button>
                <OrderLinePopUp isPopUpOpen={isEditPopUpOpen} setIsPopUpOpen={setIsEditPopUpOpen} isAdd={false} />
                <Button variant="primary" type="button" onClick={() => setIsConfirmPopUpOpen(true)}>Delete new order line</Button>
                <ConfirmPopUp isPopUpOpen={isConfirmPopUpOpen} setIsPopUpOpen={setIsConfirmPopUpOpen} purpose="delete" text="Are you sure you want to delete this order line?" />
                <Button variant="primary" type="button" onClick={() => generateNewInvoice()}>Generate new invoice</Button>
                {/* <GenerateInvoicePopUp isPopUpOpen={isInvoicePopUpOpen} setIsPopUpOpen={setIsInvoicePopUpOpen} source="order" /> */}
            </div>
            
            <OrderLineTable data={orderline} />
        </div>
    );
};

export default OrderForm;