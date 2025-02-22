import React from "react";
import { RedirectButton } from "./components/button";
import "./styling/components/components.scss";


const SideMenu: React.FC = () => {
    return (
        <div className="side-menu">
            <RedirectButton href="/orders" variant="primary">Orders</RedirectButton>
            <RedirectButton href="/repair" variant="primary">Repair</RedirectButton>
            <RedirectButton href="/invoice" variant="primary">Invoice</RedirectButton>
            <RedirectButton href="/customer" variant="primary">Customer</RedirectButton>
            <RedirectButton href="/employee" variant="primary">Employee</RedirectButton>
            <RedirectButton href="/stock" variant="primary">Stock</RedirectButton>
            <RedirectButton href="/jobs" variant="primary">Jobs</RedirectButton>
            <RedirectButton href="/landing" variant="primary">Return to landing</RedirectButton>
        </div>
    );
};

export default SideMenu;