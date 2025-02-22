import React from "react";
import OrderForm from "../orders/orderForm"
import RepairForm from "../repairs/repairForm"
import InvoiceForm from "../invoices/invoiceForm"
import CustomerForm from "../customers/customerForm"
import EmployeeForm from "../employees/employeeForm"
import StockForm from "../stock/stockForm"
import { ContentProps } from "../contents/content"


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