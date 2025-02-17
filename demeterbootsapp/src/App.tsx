import React from 'react';
import Button from './components/button';
import Checkbox from './components/checkbox';
import { OrderTable, OrderLineTable, RepairTable, RepairLineTable, InvoiceTable, InvoiceLineTable, JobTable, CustomerTable, MeasurementsTable, EmployeeTable } from './components/table';
import TextArea from './components/textarea';
import TextField from './components/textfield';
import "./styling/components/components.scss";

import './App.css';

const orderData = [
  { ID: 1, "Customer Name": "John Doe", Location: "New York", Status: "Pending", Total: "$250" },
  { ID: 2, "Customer Name": "Jane Smith", Location: "Los Angeles", Status: "Shipped", Total: "$180" }
];

const orderLineData = [
  { Type: "Boots", Style: "Western", Leather: "Cowhide", Price: "$120", Notes: "Brown color" },
  { Type: "Shoes", Style: "Oxford", Leather: "Calfskin", Price: "$150", Notes: "Black polish" }
];

const repairData = [
  { ID: 1, "Customer Name": "Mark Twain", Location: "Chicago", Status: "Completed", Total: "$75" },
  { ID: 2, "Customer Name": "Emily Johnson", Location: "Dallas", Status: "In Progress", Total: "$90" }
];

const repairLineData = [
  { Type: "Boots", "Repair Kind": "Sole Replacement", Leather: "Buffalo", Price: "$50", Notes: "Urgent repair" },
  { Type: "Shoes", "Repair Kind": "Stitching", Leather: "Goat", Price: "$25", Notes: "Minor fix" }
];

const invoiceData = [
  { ID: 1, "Customer Name": "Michael Scott", Status: "Paid", Total: "$300", "Is Payment Made?": "Yes" },
  { ID: 2, "Customer Name": "Pam Beesly", Status: "Pending", Total: "$150", "Is Payment Made?": "No" }
];

const invoiceLineData = [
  { "Task ID": 101, Price: "$200" },
  { "Task ID": 102, Price: "$100" }
];

const jobData = [
  { "Employee Name": "Jim Halpert", Status: "Ongoing", "Task ID": 501 },
  { "Employee Name": "Dwight Schrute", Status: "Completed", "Task ID": 502 }
];

const customerData = [
  { ID: 1, Name: "Angela Martin", City: "Scranton", Phone: "123-456-7890" },
  { ID: 2, Name: "Kevin Malone", City: "New York", Phone: "987-654-3210" }
];

const measurementsData = [
  { Notes: "Normal fit", Feet: "10", Bunion: "No", "Highest Point": "25cm", Heel: "5cm", Ankle: "22cm", Calf: "30cm", "Under Knee": "35cm", Height: "40cm", "Calf Height": "15cm", "20cm Mark": "Yes" },
  { Notes: "Wide fit", Feet: "12", Bunion: "Yes", "Highest Point": "28cm", Heel: "4cm", Ankle: "25cm", Calf: "35cm", "Under Knee": "38cm", Height: "45cm", "Calf Height": "17cm", "20cm Mark": "No" }
];

const employeeData = [
  { ID: 1, Name: "Stanley Hudson", "Access Level": "Admin" },
  { ID: 2, Name: "Meredith Palmer", "Access Level": "Employee" }
];


function App() {
  return (
    <div className="App">
      <Button variant="primary">Click me!</Button>
      <Checkbox label="I agree to the terms and conditions" />
      <OrderTable data={orderData} />
      <OrderLineTable data={orderLineData} />
      <RepairTable data={repairData} />
      <RepairLineTable data={repairLineData} />
      <InvoiceTable data={invoiceData} />
      <InvoiceLineTable data={invoiceLineData} />
      <JobTable data={jobData} />
      <CustomerTable data={customerData} />
      <MeasurementsTable data={measurementsData} />
      <EmployeeTable data={employeeData} />
      <TextArea label="Comments" rows={5} />
      <TextField label="Name"/>
    </div>
  );
}

export default App;
