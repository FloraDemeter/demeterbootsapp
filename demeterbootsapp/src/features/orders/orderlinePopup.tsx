import React, { useState } from 'react';
import PopUp from '../../components/layout/popup';
import { Button } from '../../components/elements/button';
import Textfield from '../../components/elements/textfield';
import Textarea from '../../components/elements/textarea';
import DropDown from '../../components/elements/dropdown';

interface OrderLinePopUpProps {
    isPopUpOpen: boolean;
    setIsPopUpOpen: (isOpen: boolean) => void;
    isAdd: boolean;
}

const OrderLinePopUp: React.FC<OrderLinePopUpProps> = ({isPopUpOpen, setIsPopUpOpen, isAdd}) => {
    const handleSubmit = (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        console.log("Done");
        setIsPopUpOpen(false);
    }

    const [selectedProductType, setSelectedProductType] = useState<string>("Please select one");
    const [selectedLeather, setSelectedLeather] = useState<string>("Please select one");
    const [selectedToeStyle, setSelectedToeStyle] = useState<string>("Please select one");
    const [selectedHoles, setSelectedHoles] = useState<string>("Please select one");
    const [selectedZipperElastic, setSelectedZipperElastic] = useState<string>("Please select one");
    const [selectedTopStyle, setSelectedTopStyle] = useState<string>("Please select one");
    const [selectedTopLeather, setSelectedTopLeather] = useState<string>("Please select one");

    const dropdownOptions = [
        {value:"",  label: "Please select one"}, 
        {value:"1",  label: "Budapest"}, 
        {value:"2",  label: "Line"}, 
        {value:"3", label: "Holes"}];
    
    return (
        <PopUp isOpen={isPopUpOpen} popUpName="orderLine-popup" onClose={() => setIsPopUpOpen(false)}>
            <h2> {isAdd ? 'Add new' : 'Edit'} OrderLine</h2>
            <form onSubmit={handleSubmit}>
                <div className="column-left">
                    <Textarea label="Notes" />
                    <Textfield label="Price" placeholder='' /> 
                    <DropDown label="Product Type" options={dropdownOptions}  selectedValue={selectedProductType} onChange={setSelectedProductType}/>
                    <DropDown label="Leather" options={dropdownOptions}  selectedValue={selectedLeather} onChange={setSelectedLeather}/>
                </div>
                <div className="column-right">
                   <DropDown label="Toe Style" options={dropdownOptions}  selectedValue={selectedToeStyle} onChange={setSelectedToeStyle}/> {/*//visibility should depend on the product type */}
                   <DropDown label="Holes" options={dropdownOptions}  selectedValue={selectedHoles} onChange={setSelectedHoles}/> {/*visibility should depend on the product type */}
                   <DropDown label="Zipper/Elastic?" options={dropdownOptions}  selectedValue={selectedZipperElastic} onChange={setSelectedZipperElastic}/>
                   <DropDown label="Top Style" options={dropdownOptions}  selectedValue={selectedTopStyle} onChange={setSelectedTopStyle}/>
                   <DropDown label="Top Leather" options={dropdownOptions}  selectedValue={selectedTopLeather} onChange={setSelectedTopLeather}/>
                </div>
                <Button type="submit" variant="primary">Save</Button>
            </form>
        </PopUp>
    )
};

export default OrderLinePopUp;