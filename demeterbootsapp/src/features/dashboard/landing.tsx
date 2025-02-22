import React from 'react';
import { RedirectButton } from '../../components/elements/button';
import List from '../../components/elements/list';
import '../../components/styling/components/components.scss';

interface LandingProps {
    username: string;
    usertype: string;
}

const Landing: React.FC<LandingProps> = ({username, usertype}) => {
    return (
        <div className="landing">
            <div className="userinfo">
                <p>The current user logged in is:&nbsp;<span>{username}</span></p>
                <p>Account Type:&nbsp;<span>usertype</span></p>
            </div>
            <div className="jobNotifs">
                <h3>Job Notifications</h3>
                <List data={["String 1", "String 2"]} />
            </div>
            <div className="buttons">
                <RedirectButton href="/order">View Orders</RedirectButton>
                <RedirectButton href="/repair">View Repairs</RedirectButton>
                <RedirectButton href="/invoice">View Invoices</RedirectButton>
                <RedirectButton href="/customer">View Customers</RedirectButton>
                <RedirectButton href="/employee">View Employees</RedirectButton>
                <RedirectButton href="/jobs">View Jobs</RedirectButton>
                <RedirectButton href="/stock">View Stock</RedirectButton>
            </div>
        </div>
    );
};

export default Landing;