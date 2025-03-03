import React, { useState, useEffect } from 'react';
import { EmployeeTable } from '../../components/elements/table';
import { getEmployees } from "../../services/employees";
import { Employee } from '../../components/interfaces/Employee';


const EmployeeList: React.FC = () => {
    const [employees, setEmployees] = useState<Employee[]>([]);
    
    const fetchEmployees = async () => {
        try {
            const data = await getEmployees();
            setEmployees(data);
        } catch (error) {
            console.error("Error fetching employees:", error);
        }
    };

    useEffect(() => {
        fetchEmployees();
    }, []);
          
    return (
        <div className="employee-list">
            <EmployeeTable data={employees}/>
        </div>
    )

}

export default EmployeeList;