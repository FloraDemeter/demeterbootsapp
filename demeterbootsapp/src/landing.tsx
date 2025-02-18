import React from 'react';
import { RedirectButton } from './components/button';
import List from './components/list';

const Landing: React.FC = () => {
    return (
        <div className="landing">
            <div className="userinfo">
                <p>The current user logged in is:</p>
                <p>Account Type:</p>
            </div>
            <div className="buttons">
                <RedirectButton href="/orders">View Orders</RedirectButton>
                <RedirectButton href="/repair">View Repairs</RedirectButton>
                <RedirectButton href="/invoice">View Invoices</RedirectButton>
                <RedirectButton href="/customer">View Customers</RedirectButton>
                <RedirectButton href="/employee">View Employees</RedirectButton>
                <RedirectButton href="/jobs">View Jobs</RedirectButton>
                <RedirectButton href="/stock">View Stock</RedirectButton>
            </div>
            <div className="jobNotifs">
                <List data={["String 1", "String 2"]} />
            </div>
        </div>
    );
};

export default Landing;