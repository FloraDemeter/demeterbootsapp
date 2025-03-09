import React, { useState, useEffect } from 'react';
import { RepairTable } from '../../components/elements/table';
import { Repair } from '../../components/interfaces/Repair';
import { getRepairs } from '../../services/repairs';


const RepairList: React.FC = () => {
    const [repairs, setRepairs] = useState<Repair[]>([]);

    const fetchRepairs = async () => {
        try {
            const data = await getRepairs();
            setRepairs(data);
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