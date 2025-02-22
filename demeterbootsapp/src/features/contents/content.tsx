import React from "react";
import "../../components/styling/components/components.scss";
import ContentLists from "./contentLists";
import ContentForms from "./contentForms";
import { useSearchParams, Navigate } from "react-router-dom";

export interface ContentProps {
    content: "order" | "repair" | "invoice" | "customer" | "employee" | "jobs" | "stock";
    data: any[];
}

const Content: React.FC<ContentProps> = ({content, data}) => {
    const titles: Record<ContentProps["content"], string> = {
        order: "Orders",
        repair: "Repairs",
        invoice: "Invoices",
        customer: "Customers",
        employee: "Employees",
        jobs: "Jobs",
        stock: "Stock"
    };

    const [searchParams] = useSearchParams();
    const viewParam = searchParams.has("view");

    const viewID = searchParams.get("view");

    if (viewParam && content === "jobs") {
        return <Navigate to="/landing" replace/>
    }
    return (
        <div className="content">
            <h1>{titles[content]} {viewID}</h1>

            {viewParam ? (
                content !== "jobs" && (
                    <ContentForms content={content as Exclude<ContentProps["content"], "jobs">} />
                )
            ) : (
                <ContentLists content={content} />
            )}
        </div>
    );
};

export default Content;