import React, { useState } from 'react';
import PopUp from '../../components/layout/popup';
import { Button } from '../../components/elements/button';
import Textfield from '../../components/elements/textfield';
import Textarea from '../../components/elements/textarea';
import DropDown from '../../components/elements/dropdown';
import Checkbox from '../../components/elements/checkbox';

interface StockPopUpProps {
    isPopUpOpen: boolean;
    setIsPopUpOpen: (isOpen: boolean) => void;
    isAdd: boolean;
}

export const ProductTypePopUp: React.FC<StockPopUpProps> = ({isPopUpOpen, setIsPopUpOpen, isAdd}) => {
    const handleSubmit = (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        setIsPopUpOpen(false);
    }
    
    return (
        <PopUp isOpen={isPopUpOpen} popUpName="producttype-popup" onClose={() => setIsPopUpOpen(false)}>
            <h2> {isAdd ? 'Add new' : 'Edit'} Product Type</h2>
            <form onSubmit={handleSubmit} className="single-column">
                <div >
                    <Textfield label="Description" />
                    <Textfield label="Price" />
                    <Button type="submit" variant="primary">Save</Button>
                </div>
            </form>
        </PopUp>
    )
};

export const RepairCategoryPopUp: React.FC<StockPopUpProps> = ({isPopUpOpen, setIsPopUpOpen, isAdd}) => {
    const handleSubmit = (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        setIsPopUpOpen(false);
    }

    const [selectedProductType, setSelectedProductType] = useState<string>("Please select one");
    
    const dropdownOptions = [
        {value:"",  label: "Please select one"}, 
        {value:"1",  label: "Budapest"}, 
        {value:"2",  label: "Line"}, 
        {value:"3", label: "Holes"}];
    
    return (
        <PopUp isOpen={isPopUpOpen} popUpName="repaircategory-popup" onClose={() => setIsPopUpOpen(false)}>
            <h2> {isAdd ? 'Add new' : 'Edit'} Repair Category</h2>
            <form onSubmit={handleSubmit} className="single-column">
                <div >
                    <DropDown label="Product Type" options={dropdownOptions}  selectedValue={selectedProductType} onChange={setSelectedProductType}/>
                    <Textfield label="Description" />
                    <Textfield label="Price" />
                    <Button type="submit" variant="primary">Save</Button>
                </div>
            </form>
        </PopUp>
    )
};

export const LeatherPopUp: React.FC<StockPopUpProps> = ({isPopUpOpen, setIsPopUpOpen, isAdd}) => {
    const handleSubmit = (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        setIsPopUpOpen(false);
    }
    
    return (
        <PopUp isOpen={isPopUpOpen} popUpName="leather-popup" onClose={() => setIsPopUpOpen(false)}>
            <h2> {isAdd ? 'Add new' : 'Edit'} Leather</h2>
            <form onSubmit={handleSubmit}>
                <div className="column-left">
                    <Textfield label="Colour" />
                    <Textfield label="Description" />
                </div>
                <div className="column-right">
                    <Textfield label="Image Path" />
                    <Checkbox label="Is Available" />
                </div>
                <div className="full-width">
                    <Button type="submit" variant="primary">Save</Button>
                </div>
            </form>
        </PopUp>
    )
};