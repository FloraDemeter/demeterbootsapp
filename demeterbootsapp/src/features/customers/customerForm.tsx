import React, { useState } from 'react';
import TextField from '../../components/elements/textfield';
import { Button } from '../../components/elements/button';
import { MeasurementsTable } from '../../components/elements/table';
import MeasurementPopUp from './measurementPopup';

interface FormInterface {
    data: any[];
}

const CustomerForm: React.FC<FormInterface> = ({data}) => {
    const testingInfo = [
        {Notes: "this is a test", Feet: 20, Bunion: 10, "Highest Point":20, Heel:40, Ankle:12, Calf:13, "Under Knee":34, Height:12, "Calf Height":12, "20cm Mark":42},
        {Notes: "this is a second test", Feet: 20, Bunion: 10, "Highest Point":20, Heel:40, Ankle:12, Calf:13, "Under Knee":34, Height:12, "Calf Height":12, "20cm Mark":42}
      ];

    const customerInfo= {FirstName: "John", LastName: "Doe", Phone:"1234567890", Email:"johndoe@email.com", Street:"1234 Anywhere Street", City:"New York", PostCode: "1234WF"}

    const [isPopUpOpen, setIsPopUpOpen] = useState(false);
    return (
        <div className='customer-form'>
            <form>
                <div className="column-left">
                    <TextField label="First Name" value={customerInfo.FirstName} />
                    <TextField label="Last Name" value={customerInfo.LastName}/>
                    <TextField label="Phone" value={customerInfo.Phone}/>
                    <TextField label="Email" value={customerInfo.Email}/>
                </div>
                <div className="column-right">
                    <TextField label="Street" value={customerInfo.Street} />
                    <TextField label="City" value={customerInfo.City}/>
                    <TextField label="Postcode" value={customerInfo.PostCode} />
                    <Button type="submit" variant="primary">Save</Button>
                </div>
            </form>
            <MeasurementsTable data={testingInfo} />
            <Button variant="primary" type="button" onClick={() => setIsPopUpOpen(true)}>Add new measurement</Button>
            <MeasurementPopUp isPopUpOpen={isPopUpOpen} setIsPopUpOpen={setIsPopUpOpen}/>
        </div>
    );
};

export default CustomerForm;