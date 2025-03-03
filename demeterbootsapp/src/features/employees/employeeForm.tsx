
import React, { useState, useEffect } from 'react';
import { useSearchParams } from 'react-router-dom';
import TextField from '../../components/elements/textfield';
import { Button } from '../../components/elements/button';
import { EmployeeJobsTable } from '../../components/elements/table';
import Checkbox from '../../components/elements/checkbox';
import { Employee, Job } from '../../components/interfaces/Employee';
import {getEmployeeID, getJobsByEmployeeID} from "../../services/employees";

const EmployeeForm: React.FC = () => {
    const [searchParams] = useSearchParams();
    const isNew = searchParams.get("view") === "new";
    const employeeID = searchParams.get("view");
    
    const today = new Date();

    const [employeeInfo, setEmployeeInfo] = useState<Employee>();
    const [jobInfo, setJobInfo] = useState<Job[]>([]);
    
    useEffect(() => {
        if (!isNew) {
            fetchEmployee(employeeID);
        }
    }, [isNew]);

    const fetchEmployee = async (employeeID: string | null) => {
        try {
            if (!employeeID) {
                throw new Error("Employee ID is missing");
            }

            const employeeData = await getEmployeeID(employeeID);
            setEmployeeInfo(employeeData);

            const jobData = await getJobsByEmployeeID(employeeID);
            setJobInfo(jobData);
        } catch (error) {
            console.error("Error fetching employee details and jobs: ", error);
        }
    }

    return (
        <div className='employee-form'>
            <form>
                <div className="column-left">
                    <TextField label="First Name" value={employeeInfo?.firstName} />
                    <TextField label="Last Name" value={employeeInfo?.lastName} />
                    <TextField label="Email" value={employeeInfo?.email} />
                    <TextField label="Phone" value={employeeInfo?.phone} />
                    <TextField label="Username" value={employeeInfo?.username} />
                    <TextField label="Password" type="password" value={employeeInfo?.password} />
                </div>
                <div className="column-right">
                    <TextField label="Street" value={employeeInfo?.street} />
                    <TextField label="City" value={employeeInfo?.city} />
                    <TextField label="Postcode" value={employeeInfo?.postCode} />
                    <TextField label="Access Level" value={employeeInfo?.accessLevel} />
                    <TextField label="Start Date" type="date" value={employeeInfo?.startDate} />
                    <Checkbox label="Is User Active?" defaultChecked={employeeInfo?.isActive} />
                </div>
                <Button type="submit" variant="primary">Save</Button>
            </form>
            <EmployeeJobsTable data={jobInfo} />
        </div>
    );
};

export default EmployeeForm;
