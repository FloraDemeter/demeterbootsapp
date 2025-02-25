import React, { useState, useEffect } from 'react';
import { RepairTable } from '../../components/elements/table';

const RepairList: React.FC = () => {
    const dummyrRepairData = [
        { ID: 'R00000001', 'Customer Name': 'Anna Doe', Location: 'London',Status: "Preprocessing", Total: 100 },
        { ID: 'R00000002', 'Customer Name': 'Josh Doe', Location: 'Chelmsford',Status: "Making", Total: 80 }
      ];

    const [repairs, setRepairs] = useState(dummyrRepairData);

    const fetchRepairs = async () => {
        try {
            setRepairs(dummyrRepairData);
        } catch (error) {
            console.error("Error fetching repairs:", error);
        }
    };

    useEffect(() => {
        fetchRepairs();
    }, []);
      
    return (
        <div className="repair-list">
            <RepairTable data={repairs}/>
        </div>
    )   
}

export default RepairList;