import React from "react";
import OrderForm from "../../features/orders/orderForm"
import RepairForm from "../../features/repairs/repairForm"
import InvoiceForm from "../../features/invoices/invoiceForm"
import CustomerForm from "../../features/customers/customerForm"
import EmployeeForm from "../../features/employees/employeeForm"
import StockForm from "../../features/stock/stockForm"
import { ContentProps } from "./content"


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