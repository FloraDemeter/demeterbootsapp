import React, { useEffect, useState} from 'react';
import { useSearchParams } from 'react-router-dom';
import TextField from '../../components/elements/textfield';
import { Button } from '../../components/elements/button';
import { RepairLineTable } from '../../components/elements/table';
import RepairLinePopUp  from './repairlinePopup';
import DropDown from '../../components/elements/dropdown';
import Checkbox from '../../components/elements/checkbox';
import ConfirmPopUp from '../../components/layout/confirmPopUp';
import { Repair, RepairLine } from '../../components/interfaces/Repair';
import { Customer } from '../../components/interfaces/Customer';
import { getRepairID, getRepairLineByRepairID } from '../../services/repairs';
import { getCustomers } from '../../services/customers';
import { Status } from '../../components/interfaces/BusinessInfo';
import { getStatuses } from '../../services/businessInfo';

const RepairForm: React.FC = () => {
    const [searchParams] = useSearchParams();
    const isNew = searchParams.get("view") === "new";
    const repairId = searchParams.get("view");

    const today = new Date();
   
    const [repair, setRepairInfo] = useState<Repair>();
    const [repairline, setRepairLineInfo] = useState<RepairLine[]>([]);
    const [selectedCustomerType, setSelectedCustomerType] = useState<string>("Please select one");
    const [selectedStatus, setSelectedStatus] = useState<string>("Please select one");

    const [isAddPopUpOpen, setIsAddPopUpOpen] = useState(false);
    const [isEditPopUpOpen, setIsEditPopUpOpen] = useState(false);
    const [isConfirmPopUpOpen, setIsConfirmPopUpOpen] = useState(false);

    const [customers, setCustomerDropDown]= useState<any[]>([]);
    const [statuses, setStatusDropDown]= useState<any[]>([]);

    useEffect(() => {
        if (!isNew) {
            fetchRepair(repairId);
        }
    }, [isNew]);

    const fetchRepair = async (repairId: string | null) => {
        try {
            if (!repairId) {
                throw new Error("Repair ID is missing");
            }

            const repairData = await getRepairID(repairId);
            setRepairInfo(repairData);

            const repairLineData = await getRepairLineByRepairID(repairId);
            setRepairLineInfo(repairLineData);

            
            const customersData = await getCustomers();
            
            const customersList = customersData.map((customer: Customer) => {
                return {
                    value: customer.id,
                    label: customer.firstName + " " + customer.lastName
                }
            });
            setCustomerDropDown(customersList);
            
            const statusData = await getStatuses();
            const statusesList = statusData.map((status: Status) => {
                return {
                    value: status.id,
                    label: status.description
                }
            });
            setStatusDropDown(statusesList);
            
            setSelectedCustomerType(repairData.customerId);
            setSelectedStatus(repairData.status);
            
        }catch (error) {
            console.error("Error fetching repair details and repair lines: ", error);
        }
    }

    const deleteRepairLine = () => {};
    const generateNewInvoice = () => {};

    return (
        <div className='repair-form'>
            <form>
                <div className="column-left">
                    <DropDown label="Customer" options={customers} selectedValue={selectedCustomerType} onChange={setSelectedCustomerType} />
                    <TextField label="Repair Date" value={repair?.repairDate} type="date" />
                    <TextField label="Predicted Finish Date" value={repair?.predictedFinishDate} type="date" />
                </div>
                <div className="column-right">
                    <DropDown label="Status" options={statuses} selectedValue={selectedStatus} onChange={setSelectedStatus} />
                    <TextField label="Total" value={repair?.total} />
                    <Checkbox label="Is warranty Accepted" defaultChecked={repair?.isWarrantyAccepted} />
                </div>
                <div className="full-width">
                    <Button type="submit" variant="primary">Save</Button>
                </div>
            </form>
            <div className="actions">
                <Button variant="primary" type="button" onClick={() => setIsAddPopUpOpen(true)}>Add new repair line</Button>
                <RepairLinePopUp isPopUpOpen={isAddPopUpOpen} setIsPopUpOpen={setIsAddPopUpOpen} isAdd={true} />
                <Button variant="primary" type="button" onClick={() => setIsEditPopUpOpen(true)}>Edit repair line</Button>
                <RepairLinePopUp isPopUpOpen={isEditPopUpOpen} setIsPopUpOpen={setIsEditPopUpOpen} isAdd={false} />
                <Button variant="primary" type="button" onClick={() => setIsConfirmPopUpOpen(true)}>Delete repair line</Button>
                <ConfirmPopUp isPopUpOpen={isConfirmPopUpOpen} setIsPopUpOpen={setIsConfirmPopUpOpen} purpose="delete" text="Are you sure you want to delete this repair line?" />
                <Button variant="primary" type="button" onClick={() => generateNewInvoice()}>Generate new invoice</Button>
                {/* <GenerateInvoicePopUp isPopUpOpen={isInvoicePopUpOpen} setIsPopUpOpen={setIsInvoicePopUpOpen} /> */}
            </div>

            <RepairLineTable data={repairline} />
        </div>
    );
};

export default RepairForm;