import React from "react";
import OrderForm from "../../features/orders/orderForm"
import RepairForm from "../../features/repairs/repairForm"
import InvoiceForm from "../../features/invoices/invoiceForm"
import CustomerForm from "../../features/customers/customerForm"
import EmployeeForm from "../../features/employees/employeeForm"
import { ContentProps } from "./content"


interface FormProps {
    content: Exclude<ContentProps["content"], ["jobs", "stock"]>;
}

const ContentForm: React.FC<FormProps> = ({ content }) => {
    switch (content) {
        case "order": return <OrderForm />;
        case "repair": return <RepairForm />;
        case "invoice": return <InvoiceForm />;
        case "customer": return <CustomerForm />;
        case "employee": return <EmployeeForm />;
        default: return null;
    }
};

export default ContentForm;