import React, { useState } from 'react';
import PopUp from '../../components/layout/popup';
import { Button } from '../../components/elements/button';
import Textfield from '../../components/elements/textfield';
import Textarea from '../../components/elements/textarea';
import DropDown from '../../components/elements/dropdown';

interface RepairLinePopUpProps {
    isPopUpOpen: boolean;
    setIsPopUpOpen: (isOpen: boolean) => void;
    isAdd: boolean;
}

const RepairLinePopUp: React.FC<RepairLinePopUpProps> = ({isPopUpOpen, setIsPopUpOpen, isAdd}) => {
    const handleSubmit = (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        console.log("Done");
        setIsPopUpOpen(false);
    }

    const [selectedProductType, setSelectedProductType] = useState<string>("Please select one");
    const [selectedRepairCat, setSelectedRepairCat] = useState<string>("Please select one");
    

    const dropdownOptions = [
        {value:"",  label: "Please select one"}, 
        {value:"1",  label: "Budapest"}, 
        {value:"2",  label: "Line"}, 
        {value:"3", label: "Holes"}];
    
    return (
        <PopUp isOpen={isPopUpOpen} popUpName="repairLine-popup" onClose={() => setIsPopUpOpen(false)}>
            <h2> {isAdd ? 'Add new' : 'Edit'}  repair line</h2>
            <form onSubmit={handleSubmit}>
                <div className="column-left">
                    <Textarea label="Notes" />
                    <Textfield label="Price" placeholder='' /> 
                </div>
                <div className="column-right">
                <DropDown label="Product Type" options={dropdownOptions}  selectedValue={selectedProductType} onChange={setSelectedProductType}/>
                   <DropDown label="Repair Category" options={dropdownOptions} selectedValue={selectedRepairCat} onChange={setSelectedRepairCat} />
                </div>
                <Button type="submit" variant="primary">Save</Button>
            </form>
        </PopUp>
    )
};

export default RepairLinePopUp;