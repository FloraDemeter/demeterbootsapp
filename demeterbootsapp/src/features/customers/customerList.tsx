import React from 'react';
import { CustomerTable } from '../../components/elements/table';

const CustomerList: React.FC = () => {

    const testingInfo = [
        { ID: 'C00000001', Name: 'John Doe', Phone: '1234567890', City: 'New York' },
        { ID: 'C00000002', Name: 'Jane Doe', Phone: '0123456789', City: 'London' }
      ];
      
    return (
        <div className="customer-list">
            <CustomerTable data={testingInfo}/>
        </div>
    )

}

export default CustomerList;