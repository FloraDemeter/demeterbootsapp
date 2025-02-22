import React from "react";
import { useParams, useSearchParams, Navigate } from "react-router-dom";
import SideMenu from "./sidemenu";
import Content from "./content";

// Define allowed content types
type ContentType = "order" | "repair" | "invoice" | "customer" | "employee" | "jobs" | "stock";

const MainTemplate: React.FC = () => {
    const { contentType } = useParams<{ contentType?: string }>();
    const [searchParams] = useSearchParams();
    
    // Ensure contentType is valid
    const validContentTypes: ContentType[] = ["order", "repair", "invoice", "customer", "employee", "jobs", "stock"];
    
    if (!contentType || !validContentTypes.includes(contentType as ContentType)) {
        return <Navigate to="/landing" replace />;
    }

    // Determine view type
    const viewParam = searchParams.get("view");
    const contentTypeView = viewParam ? "card" : "list";

    return (
        <div className="main">
            <SideMenu />
            <Content type={contentTypeView} content={contentType as ContentType} data={[]} />
        </div>
    );
};

export default MainTemplate;
