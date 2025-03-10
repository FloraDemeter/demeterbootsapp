import React from "react";

interface TableProps {
  data: any[];
}

export const OrderTable: React.FC<TableProps> = ({ data }) => {
    const headers = ["ID", "Customer Name", "Location", "Status", "Total"];
    return (
        <table className="tbl-main">
            <thead className="">
                <tr>
                {headers.map((header, index) => (
                    <th key={index} className="">
                    {header}
                    </th>
                ))}
                </tr>
            </thead>
            <tbody>
                {data.map((row, rowIndex) => (
                <tr key={rowIndex} className="border">
                    <td>{row.id}</td>
                    <td>{row.customerID}</td>
                    <td>{row.location}</td>
                    <td>{row.status}</td>
                    <td>{row.total}</td>
                </tr>
                ))}
            </tbody>
        </table>
    );
};

export const OrderLineTable: React.FC<TableProps> = ({ data }) => {
    const headers = ["Type", "Style", "Leather", "Price", "Notes"];
    return (
      <table className="tbl-main">
        <thead className="">
          <tr>
            {headers.map((header, index) => (
              <th key={index} className="">
                {header}
              </th>
            ))}
          </tr>
        </thead>
        <tbody>
            {data.map((row, rowIndex) => (
            <tr key={rowIndex} className="border">
                <td>{row.productTypeID}</td>
                <td>{row.productStyle}</td>
                <td>{row.leatherID}</td>
                <td>{row.price}</td>
                <td>{row.notes}</td>
            </tr>
            ))}
        </tbody>
      </table>
    );
  };

export const RepairTable: React.FC<TableProps> = ({ data }) => {
const headers = ["ID", "Customer Name", "Location", "Status", "Total"];
return (
    <table className="tbl-main">
    <thead className="">
        <tr>
        {headers.map((header, index) => (
            <th key={index} className="">
            {header}
            </th>
        ))}
        </tr>
    </thead>
    <tbody>
        {data.map((row, rowIndex) => (
        <tr key={rowIndex} className="border">
            <td>{row.id}</td>
            <td>{row.customerID}</td>
            <td>{row.location}</td>
            <td>{row.status}</td>
            <td>{row.total}</td>
        </tr>
        ))}
    </tbody>
    </table>
);
};

export const RepairLineTable: React.FC<TableProps> = ({ data }) => {
const headers = ["Type", "Repair Kind", "Price", "Notes"];
return (
    <table className="tbl-main">
    <thead className="">
        <tr>
        {headers.map((header, index) => (
            <th key={index} className="">
            {header}
            </th>
        ))}
        </tr>
    </thead>
    <tbody>
        {data.map((row, rowIndex) => (
        <tr key={rowIndex} className="border">
            <td>this would be type</td>
            <td>{row.repairCategoryID}</td>
            <td>{row.price}</td>
            <td>{row.notes}</td>
        </tr>
        ))}
    </tbody>
    </table>
);
};

export const InvoiceTable: React.FC<TableProps> = ({ data }) => {
const headers = ["ID", "Customer Name", "Status", "Total", "Is Payment Made?"];
return (
    <table className="tbl-main">
    <thead className="">
        <tr>
        {headers.map((header, index) => (
            <th key={index} className="">
            {header}
            </th>
        ))}
        </tr>
    </thead>
    <tbody>
        {data.map((row, rowIndex) => (
        <tr key={rowIndex} className="border">
            <td>{row.id}</td>
            <td>{row.customerId}</td>
            <td>{row.status}</td>
            <td>{row.total}</td>
            <td>{row.isPaid ? "Yes" : "No"}</td>
        </tr>
        ))}
    </tbody>
    </table>
);
};

export const InvoiceLineTable: React.FC<TableProps> = ({ data }) => {
const headers = ["Task ID", "Price"];
return (
    <table className="tbl-main">
    <thead className="">
        <tr>
        {headers.map((header, index) => (
            <th key={index} className="">
            {header}
            </th>
        ))}
        </tr>
    </thead>
    <tbody>
        {data.map((row, rowIndex) => (
        <tr key={rowIndex} className="border">
            <td>{row.taskID}</td>
            <td>{row.total}</td>
        </tr>
        ))}
    </tbody>
    </table>
);
};

export const JobTable: React.FC<TableProps> = ({ data }) => {
const headers = ["Employee Name", "Status", "Task ID"];
return (
    <table className="tbl-main">
    <thead className="">
        <tr>
        {headers.map((header, index) => (
            <th key={index} className="">
            {header}
            </th>
        ))}
        </tr>
    </thead>
    <tbody>
        {data.map((row, rowIndex) => (
        <tr key={rowIndex} className="border">
            <td>{row.employeeID}</td>
            <td>{row.status}</td>
            <td>{row.taskID}</td>
        </tr>
        ))}
    </tbody>
    </table>
);
};

export const EmployeeJobsTable: React.FC<TableProps> = ({ data }) => {
    const headers = ["Task ID", "Status"];
    return (
        <table className="tbl-main">
        <thead className="">
            <tr>
            {headers.map((header, index) => (
                <th key={index} className="">
                {header}
                </th>
            ))}
            </tr>
        </thead>
        <tbody>
            {data.map((row, rowIndex) => (
            <tr key={rowIndex} className="border">
                <td>{row.taskID}</td>
                <td>{row.status}</td>
            </tr>
            ))}
        </tbody>
        </table>
    );
    };

export const CustomerTable: React.FC<TableProps> = ({ data }) => {
const headers = ["ID", "Name", "City", "Phone"];
return (
    <table className="tbl-main">
    <thead className="">
        <tr>
        {headers.map((header, index) => (
            <th key={index} className="">
            {header}
            </th>
        ))}
        </tr>
    </thead>
    <tbody>
        {data.map((row, rowIndex) => (
        <tr key={rowIndex} className="border">
            <td>{row.id}</td>
            <td>{row.firstName} {row.lastName}</td>
            <td>{row.city}</td>
            <td>{row.phone}</td>
        </tr>
        ))}
    </tbody>
    </table>
);
};

export const MeasurementsTable: React.FC<TableProps> = ({ data }) => {
const headers = ["Notes", "Feet", "Bunion", "Highest Point", "Heel", "Ankle", "Calf", "Under Knee", "Height", "Calf Height", "20cm Mark"];
return (
    <table className="tbl-main">
    <thead className="">
        <tr>
        {headers.map((header, index) => (
            <th key={index} className="">
            {header}
            </th>
        ))}
        </tr>
    </thead>
    <tbody>
        {data.map((row, rowIndex) => (
        <tr key={rowIndex} className="border">
            <td>{row.notes}</td>
            <td>{row.feet}</td>
            <td>{row.bunion}</td>
            <td>{row.highPoint}</td>
            <td>{row.heel}</td>
            <td>{row.ankle}</td>
            <td>{row.calf}</td>
            <td>{row.underKnee}</td>
            <td>{row.height}</td>
            <td>{row.calfHeight}</td>
            <td>{row.tMark}</td>
        </tr>
        ))}
    </tbody>
    </table>
);
};

export const EmployeeTable: React.FC<TableProps> = ({ data }) => {
const headers = ["ID", "Name", "Phone", "Access Level"];
return (
    <table className="tbl-main">
    <thead className="">
        <tr>
        {headers.map((header, index) => (
            <th key={index} className="">
            {header}
            </th>
        ))}
        </tr>
    </thead>
    <tbody>
        {data.map((row, rowIndex) => (
        <tr key={rowIndex} className="border">
            <td>{row.id}</td>
            <td>{row.firstName}</td>
            <td>{row.lastName}</td>
            <td>{row.accessLevel}</td>
        </tr>
        ))}
    </tbody>
    </table>
);
};

export const ProductTypeTable: React.FC<TableProps> = ({ data }) => {
    const headers = ["Description", "Price"];
    return (
        <table className="tbl-main">
            <thead className="">
                <tr>
                {headers.map((header, index) => (
                    <th key={index} className="">
                    {header}
                    </th>
                ))}
                </tr>
            </thead>
            <tbody>
                {data.map((row, rowIndex) => (
                <tr key={rowIndex} className="border">
                    <td>{row.description}</td>
                    <td>{row.price}</td>
                </tr>
                ))}
            </tbody>
        </table>
    );
};

export const RepairCategoryTable: React.FC<TableProps> = ({ data }) => {
    const headers = [ "Product Type" ,"Description", "Price"];
    return (
        <table className="tbl-main">
            <thead className="">
                <tr>
                {headers.map((header, index) => (
                    <th key={index} className="">
                    {header}
                    </th>
                ))}
                </tr>
            </thead>
            <tbody>
                {data.map((row, rowIndex) => (
                <tr key={rowIndex} className="border">
                    <td>{row.productTypeId}</td>
                    <td>{row.description}</td>
                    <td>{row.price}</td>
                </tr>
                ))}
            </tbody>
        </table>
    );
};

export const LeatherTable: React.FC<TableProps> = ({ data }) => {
    const headers = ["Description", "Colour", "Is Available?"];
    return (
        <table className="tbl-main">
            <thead className="">
                <tr>
                {headers.map((header, index) => (
                    <th key={index} className="">
                    {header}
                    </th>
                ))}
                </tr>
            </thead>
            <tbody>
                {data.map((row, rowIndex) => (
                <tr key={rowIndex} className="border">
                    <td>{row.description}</td>
                    <td>{row.colour}</td>
                    <td>{row.isAvailable ? "Yes" : "No"}</td>
                </tr>
                ))}
            </tbody>
        </table>
    );
};