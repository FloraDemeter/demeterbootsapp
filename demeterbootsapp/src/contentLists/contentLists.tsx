import React from "react";
import OrderList from "./orderList"
import RepairList from "./repairList"
import InvoiceList from "./invoiceList"
import CustomerList from "./customerList"
import EmployeeList from "./employeeList"
import JobsList from "./jobsList"
import StockList from "./stockList"
import { ContentProps } from "../content"

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