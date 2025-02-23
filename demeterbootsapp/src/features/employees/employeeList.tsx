import React, { useState, useEffect } from 'react';
import { EmployeeTable } from '../../components/elements/table';


const EmployeeList: React.FC = () => {
    const dummyEmployeeData = [
            { ID: 'E00000001', Name: 'John Doe', Phone: '1234567890', 'Access Level': 'Admin' },
            { ID: 'E00000002', Name: 'Jane Doe', Phone: '0123456789', 'Access Level': 'Finacial' }
          ];
    
        const [employees, setEmployees] = useState(dummyEmployeeData);
    
        const fetchEmployees = async () => {
            try {
                setEmployees(dummyEmployeeData);
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