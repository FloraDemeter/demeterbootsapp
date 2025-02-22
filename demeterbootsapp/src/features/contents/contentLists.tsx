import React from "react";
import OrderList from "../orders/orderList"
import RepairList from "../repairs/repairList"
import InvoiceList from "../invoices/invoiceList"
import CustomerList from "../customers/customerList"
import EmployeeList from "../employees/employeeList"
import JobsList from "../jobs/jobsList"
import StockList from "../stock/stockList"
import { ContentProps } from "../contents/content"

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