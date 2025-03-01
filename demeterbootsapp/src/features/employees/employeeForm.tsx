
import React, { useState, useEffect } from 'react';
import { useSearchParams } from 'react-router-dom';
import TextField from '../../components/elements/textfield';
import { Button } from '../../components/elements/button';
import { EmployeeJobsTable } from '../../components/elements/table';
import Checkbox from '../../components/elements/checkbox';

const EmployeeForm: React.FC = () => {
    const [searchParams] = useSearchParams();
    const isNew = searchParams.get("view") === "new";

    const today = new Date();

    const defaultInfo = { 
        FirstName: "", 
        LastName: "", 
        Phone: "", 
        Email: "", 
        Street: "", 
        City: "", 
        PostCode: "",
        username: "",
        password:"",
        isActive: true,
        accessLevel: "",
        startDate: today
    };

    const dummyData = { 
        FirstName: "John", 
        LastName: "Doe", 
        Phone: "1234567890", 
        Email: "johndoe@email.com", 
        Street: "1234 Anywhere Street", 
        City: "New York", 
        PostCode: "1234WF",
        username: "AdminJohn",
        password:"testing",
        isActive: true,
        accessLevel: "Admin",
        startDate: today
    };

    const defaultJobInfo: any[] = [];
    const dummyJobData = [
        { "Task ID": "O00000001", Status:"Preprocessing" },
        { "Task ID": "I00000001", Status:"Wating for payment" }
    ];

    const [customerInfo, setCustomerInfo] = useState(defaultInfo);
    const [jobInfo, setMeasurementInfo] = useState(defaultJobInfo);
    
    useEffect(() => {
        if (!isNew) {
            setCustomerInfo(dummyData);
            setMeasurementInfo(dummyJobData);
        }
    }, [isNew]);

    return (
        <div className='employee-form'>
            <form>
                <div className="column-left">
                    <TextField label="First Name" value={customerInfo.FirstName} />
                    <TextField label="Last Name" value={customerInfo.LastName} />
                    <TextField label="Email" value={customerInfo.Email} />
                    <TextField label="Phone" value={customerInfo.Phone} />
                    <TextField label="Username" value={customerInfo.username} />
                    <TextField label="Password" type="password" value={customerInfo.password} />
                </div>
                <div className="column-right">
                    <TextField label="Street" value={customerInfo.Street} />
                    <TextField label="City" value={customerInfo.City} />
                    <TextField label="Postcode" value={customerInfo.PostCode} />
                    <TextField label="Access Level" value={customerInfo.accessLevel} />
                    <TextField label="Start Date" type="date" value={customerInfo.startDate.toISOString().split("T")[0]} />
                    <Checkbox label="Is User Active?" defaultChecked={customerInfo.isActive} />
                </div>
                <Button type="submit" variant="primary">Save</Button>
            </form>
            <EmployeeJobsTable data={jobInfo} />
        </div>
    );
};

export default EmployeeForm;
