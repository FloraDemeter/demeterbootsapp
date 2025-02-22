import React from "react";
import OrderForm from "./orderForm"
import RepairForm from "./repairForm"
import InvoiceForm from "./invoiceForm"
import CustomerForm from "./customerForm"
import EmployeeForm from "./employeeForm"
import StockForm from "./stockForm"
import { ContentProps } from "../content"


interface FormProps {
    content: Exclude<ContentProps["content"], "jobs">;
}

const ContentForm: React.FC<FormProps> = ({ content }) => {
    switch (content) {
        case "order": return <OrderForm data={[]}/>;
        case "repair": return <RepairForm data={[]} />;
        case "invoice": return <InvoiceForm data={[]} />;
        case "customer": return <CustomerForm data={[]} />;
        case "employee": return <EmployeeForm data={[]} />;
        case "stock": return <StockForm data={[]} />;
    }
};

export default ContentForm;