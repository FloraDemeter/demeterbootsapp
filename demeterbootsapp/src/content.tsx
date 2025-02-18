import React from "react";
import { RedirectButton } from "./components/button";
import "./styling/components/components.scss";

interface ContentProps {
    type: "card" | "list";
    content: "order" | "repair" | "invoice" | "customer" | "employee" | "jobs" | "stock";
    data: any[];
}

const Content: React.FC<ContentProps> = ({type, content, data}) => {
    const titles: Record<ContentProps["content"], string> = {
        order: "Orders",
        repair: "Repairs",
        invoice: "Invoices",
        customer: "Customers",
        employee: "Employees",
        jobs: "Jobs",
        stock: "Stock"
    };
    
    return (
        <div className="content">
            <h1>{titles[content]}</h1>

            {type === "card" ? (
                <p>this is a card page</p>
            ): (
                <p>this is a list page</p>
            )}
        </div>
    );
};

export default Content;