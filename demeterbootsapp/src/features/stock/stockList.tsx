import React from 'react';
import { ProductTypeTable, RepairCategoryTable, LeatherTable } from '../../components/elements/table';
import { Button } from '../../components/elements/button';

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

    return (
        <div>
            <div className="product-type">
                <div className="buttons">
                    <Button variant="primary">Add new product type</Button>
                    <Button variant="primary">Edit new product type</Button>
                </div>
                <ProductTypeTable data={productTypes} />
            </div>
            <div className="repair-category">
                <div className="buttons">
                    <Button variant="primary">Add new repair category</Button>
                    <Button variant="primary">Edit new repair category</Button>
                </div>
                <RepairCategoryTable data={repairCategories} />
            </div>
            <div className="leathers">
                <div className="buttons">
                    <Button variant="primary">Add new leather</Button>
                    <Button variant="primary">Edit new leather</Button>
                </div>
                <LeatherTable data={leathers} />
            </div>
        </div>
    )

}

export default StockList;