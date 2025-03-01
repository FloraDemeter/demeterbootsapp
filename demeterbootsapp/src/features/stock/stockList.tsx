import React, {useState} from 'react';
import { ProductTypeTable, RepairCategoryTable, LeatherTable } from '../../components/elements/table';
import { Button } from '../../components/elements/button';
import { ProductTypePopUp, RepairCategoryPopUp, LeatherPopUp } from './stockPopups';
import ConfirmPopUp from '../../components/layout/confirmPopUp';

const StockList: React.FC = () => {
    const productTypes = [
        {"Description": "Boots", "Price": 500},
        {"Description": "Short Boots", "Price": 250}
    ];
    const repairCategories = [
        {"Product Type": "Chaps", "Description": "Zip Replacement", "Price": 500}, 
        {"Product Type": "Boots", "Description": "Sole Refill", "Price": 500}
    ];
    const leathers = [
        {"Description": "Crocodile", "Colour": "Brown", "Is Available?": true},
        {"Description": "Patent", "Colour": "Red", "Is Available?": false}
    ];

    const [isAddProductTypePopUpOpen, setIsAddProductTypePopUpOpen] = useState(false);
    const [isAddRepairCategoryPopUpOpen, setIsAddRepairCategoryPopUpOpen] = useState(false);
    const [isAddLeatherPopUpOpen, setIsAddLeatherPopUpOpen] = useState(false);
    
    const [isEditProductTypePopUpOpen, setIsEditProductTypetPopUpOpen] = useState(false);
    const [isEditRepairCategoryPopUpOpen, setIsEditRepairCategorytPopUpOpen] = useState(false);
    const [isEditLeatherPopUpOpen, setIsEditLeathertPopUpOpen] = useState(false);
    
    const [isConfirmProductTypePopUpOpen, setIsConfirmProductTypePopUpOpen] = useState(false);
    const [isConfirmRepairCategoryPopUpOpen, setIsConfirmRepairCategoryPopUpOpen] = useState(false);
    const [isConfirmLeatherPopUpOpen, setIsConfirmLeatherPopUpOpen] = useState(false);

    return (
        <div>
            <div className="product-type">
                <div className="actions">
                    <Button variant="primary" onClick={() => setIsAddProductTypePopUpOpen(true)}>Add new product type</Button>
                    <ProductTypePopUp isPopUpOpen={isAddProductTypePopUpOpen} setIsPopUpOpen={setIsAddProductTypePopUpOpen} isAdd={true} />
                    <Button variant="primary" onClick={() => setIsEditProductTypetPopUpOpen(true)}>Edit product type</Button>
                    <ProductTypePopUp isPopUpOpen={isEditProductTypePopUpOpen} setIsPopUpOpen={setIsEditProductTypetPopUpOpen} isAdd={false} />
                    <Button variant="primary" onClick={() => setIsConfirmProductTypePopUpOpen(true)}>Delete product type</Button>
                    <ConfirmPopUp isPopUpOpen={isConfirmProductTypePopUpOpen} setIsPopUpOpen={setIsConfirmProductTypePopUpOpen} purpose="delete" text='Are you sure you would like to delete this product type?' />
                </div>
                <ProductTypeTable data={productTypes} />
            </div>
            <div className="repair-category">
                <div className="actions">
                    <Button variant="primary" onClick={() => setIsAddRepairCategoryPopUpOpen(true)}>Add new repair category</Button>
                    <RepairCategoryPopUp isPopUpOpen={isAddRepairCategoryPopUpOpen} setIsPopUpOpen={setIsAddRepairCategoryPopUpOpen} isAdd={true} />
                    <Button variant="primary" onClick={() => setIsEditRepairCategorytPopUpOpen(true)}>Edit repair category</Button>
                    <RepairCategoryPopUp isPopUpOpen={isEditRepairCategoryPopUpOpen} setIsPopUpOpen={setIsEditRepairCategorytPopUpOpen} isAdd={false} />
                    <Button variant="primary" onClick={() => setIsConfirmRepairCategoryPopUpOpen(true)}>Delete repair category</Button>
                    <ConfirmPopUp isPopUpOpen={isConfirmRepairCategoryPopUpOpen} setIsPopUpOpen={setIsConfirmRepairCategoryPopUpOpen} purpose="delete" text='Are you sure you would like to delete this repair category?' />
                </div>
                <RepairCategoryTable data={repairCategories} />
            </div>
            <div className="leathers">
                <div className="actions">
                    <Button variant="primary" onClick={() => setIsAddLeatherPopUpOpen(true)}>Add new leather</Button>
                    <LeatherPopUp isPopUpOpen={isAddLeatherPopUpOpen} setIsPopUpOpen={setIsAddLeatherPopUpOpen} isAdd={true} />
                    <Button variant="primary" onClick={() => setIsEditLeathertPopUpOpen(true)}>Edit leather</Button>
                    <LeatherPopUp isPopUpOpen={isEditLeatherPopUpOpen} setIsPopUpOpen={setIsEditLeathertPopUpOpen} isAdd={false} />
                    <Button variant="primary" onClick={() => setIsConfirmLeatherPopUpOpen(true)}>Delete leather</Button>
                    <ConfirmPopUp isPopUpOpen={isConfirmLeatherPopUpOpen} setIsPopUpOpen={setIsConfirmLeatherPopUpOpen} purpose="delete" text='Are you sure you would like to delete this leather?' />
                </div>
                <LeatherTable data={leathers} />
            </div>
        </div>
    )

}

export default StockList;