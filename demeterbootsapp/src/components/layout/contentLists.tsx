import React from "react";
import OrderList from "../../features/orders/orderList"
import RepairList from "../../features/repairs/repairList"
import InvoiceList from "../../features/invoices/invoiceList"
import CustomerList from "../../features/customers/customerList"
import EmployeeList from "../../features/employees/employeeList"
import JobsList from "../../features/jobs/jobsList"
import StockList from "../../features/stock/stockList"
import { ContentProps } from "./content"

interface ListProps {
    content: ContentProps["content"]
}

const ContentList: React.FC<ListProps> = ({ content }) => {
    switch (content) {
        case "order": return <OrderList />;
        case "repair": return <RepairList />;
        case "invoice": return <InvoiceList />;
        case "customer": return <CustomerList />;
        case "employee": return <EmployeeList />;
        case "jobs": return <JobsList />;
        case "stock": return <StockList />;
    }
};

export default ContentList;