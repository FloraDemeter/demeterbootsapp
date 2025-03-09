import React, {useEffect, useState} from 'react';
import { ProductTypeTable, RepairCategoryTable, LeatherTable } from '../../components/elements/table';
import { Button } from '../../components/elements/button';
import { ProductTypePopUp, RepairCategoryPopUp, LeatherPopUp } from './stockPopups';
import ConfirmPopUp from '../../components/layout/confirmPopUp';
import { Leather, ProductType, RepairCategory } from '../../components/interfaces/Stock';
import { getProductTypes, getLeathers, getRepairCategories } from '../../services/stock';

const StockList: React.FC = () => {
    const [isAddProductTypePopUpOpen, setIsAddProductTypePopUpOpen] = useState(false);
    const [isAddRepairCategoryPopUpOpen, setIsAddRepairCategoryPopUpOpen] = useState(false);
    const [isAddLeatherPopUpOpen, setIsAddLeatherPopUpOpen] = useState(false);
    
    const [isEditProductTypePopUpOpen, setIsEditProductTypetPopUpOpen] = useState(false);
    const [isEditRepairCategoryPopUpOpen, setIsEditRepairCategorytPopUpOpen] = useState(false);
    const [isEditLeatherPopUpOpen, setIsEditLeathertPopUpOpen] = useState(false);
    
    const [isConfirmProductTypePopUpOpen, setIsConfirmProductTypePopUpOpen] = useState(false);
    const [isConfirmRepairCategoryPopUpOpen, setIsConfirmRepairCategoryPopUpOpen] = useState(false);
    const [isConfirmLeatherPopUpOpen, setIsConfirmLeatherPopUpOpen] = useState(false);

    const [productTypeInfo, setProductType] = useState<ProductType[]>([]);
    const [repairCategoryInfo, setRepairCategory] = useState<RepairCategory[]>([]);
    const [leatherInfo, setLeather] = useState<Leather[]>([]);

    const fetchStock = async () => {
        try {
            const productTypeData = await getProductTypes();
            const repairCategoryData = await getRepairCategories();
            const leatherData = await getLeathers();

            setProductType(productTypeData);
            setRepairCategory(repairCategoryData);
            setLeather(leatherData);
        } catch (error) {
            console.error("Error fetching stock:", error);
        }
    }

    useEffect(() => {
        fetchStock();
    }, []);

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
                <ProductTypeTable data={productTypeInfo} />
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
                <RepairCategoryTable data={repairCategoryInfo} />
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
                <LeatherTable data={leatherInfo} />
            </div>
        </div>
    )

}

export default StockList;