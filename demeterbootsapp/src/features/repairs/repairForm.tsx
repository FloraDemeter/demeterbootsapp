
import React, { useEffect, useState} from 'react';
import { useSearchParams } from 'react-router-dom';
import TextField from '../../components/elements/textfield';
import { Button } from '../../components/elements/button';
import { RepairLineTable } from '../../components/elements/table';
import RepairLinePopUp  from './repairlinePopup';
import DropDown from '../../components/elements/dropdown';
import Checkbox from '../../components/elements/checkbox';
import ConfirmPopUp from '../../components/layout/confirmPopUp';

const RepairForm: React.FC = () => {
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
    
    const dummyRepairData = { 
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
        { Type: "Boots", 'Repair Kind': "testing testing", Price: 500, Notes: "make it extra wide"},
        { Type: "Chaps", 'Repair Kind': "testing testing", Price: 200, Notes: "tessst"}
    ];
    const [repair, setRepairInfo] = useState(defaultInfo);
    const [repairline, setRepairLineInfo] = useState(defaultLineInfo);

    useEffect(() => {
        if (!isNew) {
            setRepairInfo(dummyRepairData);
            setRepairLineInfo(dummyLineData);
            setSelectedCustomerType(dummyRepairData.Customer);
            setSelectedStatus("1");
        }
    }, [isNew]);

    const [selectedCustomerType, setSelectedCustomerType] = useState<string>("Please select one");
    const [selectedStatus, setSelectedStatus] = useState<string>("Please select one");

    const [isAddPopUpOpen, setIsAddPopUpOpen] = useState(false);
    const [isEditPopUpOpen, setIsEditPopUpOpen] = useState(false);
    const [isConfirmPopUpOpen, setIsConfirmPopUpOpen] = useState(false);

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

    const deleteRepairLine = () => {};
    const generateNewInvoice = () => {};

    return (
        <div className='repair-form'>
            <form>
                <div className="column-left">
                    <DropDown label="Customer" options={customers} selectedValue={selectedCustomerType} onChange={setSelectedCustomerType} />
                    <TextField label="Repair Date" value={repair.Date.toISOString().split("T")[0]} type="date" />
                    <TextField label="Predicted Finish Date" value={repair.PredictedFinish.toISOString().split("T")[0]} type="date" />
                </div>
                <div className="column-right">
                    <DropDown label="Status" options={statuses} selectedValue={selectedStatus} onChange={setSelectedStatus} />
                    <TextField label="Total" value={repair.Total} />
                    <Checkbox label="Is warranty Accepted" defaultChecked={repair.isWarrantyAcccepted} />
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