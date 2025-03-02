import React, { useState, useEffect } from 'react';
import { useSearchParams } from 'react-router-dom';
import TextField from '../../components/elements/textfield';
import { Button } from '../../components/elements/button';
import { MeasurementsTable } from '../../components/elements/table';
import MeasurementPopUp from './measurementPopup';
import ConfirmPopUp from '../../components/layout/confirmPopUp';
import { Customer, Measurements } from '../../components/interfaces/Customer';
import { getCustomerID, getMeasurementsByID } from "../../services/customers";

const CustomerForm: React.FC = () => {
    const [searchParams] = useSearchParams();
    const isNew = searchParams.get("view") === "new";
    const customerId = searchParams.get("view");

    const [customerInfo, setCustomerInfo] = useState<Customer>();
    const [measurementInfo, setMeasurementInfo] = useState<Measurements[]>([]);

    useEffect(() => {
        if (!isNew) {
            fetchCustomer(customerId);
        }
    }, [isNew]);

    const fetchCustomer = async (customerId: string | null) => {
        try {

            if (!customerId) {
               throw new Error("Customer ID is missing");
            }

            const customerData = await getCustomerID(customerId);
            setCustomerInfo(customerData);

            const measurementData = await getMeasurementsByID(customerId);
            setMeasurementInfo(measurementData);
        }catch (error) {
            console.error("Error fetching customer details and measurements: ", error);
        }
    };

    const [isPopUpOpen, setIsPopUpOpen] = useState(false);
    const [isConfirmPopUpOpen, setIsConfirmPopUpOpen] = useState(false);

    return (
        <div className='customer-form'>
            <form>
                <div className="column-left">
                    <TextField label="First Name" value={customerInfo?.firstName} />
                    <TextField label="Last Name" value={customerInfo?.lastName} />
                    <TextField label="Phone" value={customerInfo?.phone} />
                    <TextField label="Email" value={customerInfo?.email} />
                </div>
                <div className="column-right">
                    <TextField label="Street" value={customerInfo?.street} />
                    <TextField label="City" value={customerInfo?.city} />
                    <TextField label="Postcode" value={customerInfo?.postCode} />
                    <Button type="submit" variant="primary">Save</Button>
                </div>
            </form>
            <div className="actions">
                <Button variant="primary" type="button" onClick={() => setIsPopUpOpen(true)}>Add new measurement</Button>
                <MeasurementPopUp isPopUpOpen={isPopUpOpen} setIsPopUpOpen={setIsPopUpOpen} />
                <Button variant="primary" type="button" onClick={() => setIsConfirmPopUpOpen(true)} >Show...</Button>
                <ConfirmPopUp isPopUpOpen={isConfirmPopUpOpen} setIsPopUpOpen={setIsConfirmPopUpOpen} purpose='confirmation' text="Select the type of job you would like to see" />
            </div>
            <MeasurementsTable data={measurementInfo} />
        </div>
    );
};

export default CustomerForm;
