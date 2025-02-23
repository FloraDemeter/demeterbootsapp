import React from "react";

interface TableProps {
  data: any[]; // You can refine this type based on your data structure
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
                <tr key={rowIndex} className="">
                    {headers.map((header, colIndex) => (
                    <td key={colIndex} className="">
                        {row[header]}
                    </td>
                    ))}
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
              {headers.map((header, colIndex) => (
                <td key={colIndex} className="">
                  {row[header]}
                </td>
              ))}
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
            {headers.map((header, colIndex) => (
            <td key={colIndex} className="">
                {row[header]}
            </td>
            ))}
        </tr>
        ))}
    </tbody>
    </table>
);
};

export const RepairLineTable: React.FC<TableProps> = ({ data }) => {
const headers = ["Type", "Repair Kind", "Leather", "Price", "Notes"];
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
            {headers.map((header, colIndex) => (
            <td key={colIndex} className="">
                {row[header]}
            </td>
            ))}
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
            {headers.map((header, colIndex) => (
            <td key={colIndex} className="">
                {row[header]}
            </td>
            ))}
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
            {headers.map((header, colIndex) => (
            <td key={colIndex} className="">
                {row[header]}
            </td>
            ))}
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
            {headers.map((header, colIndex) => (
            <td key={colIndex} className="">
                {row[header]}
            </td>
            ))}
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
                {headers.map((header, colIndex) => (
                <td key={colIndex} className="">
                    {row[header]}
                </td>
                ))}
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
            {headers.map((header, colIndex) => (
            <td key={colIndex} className="">
                {row[header]}
            </td>
            ))}
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
            {headers.map((header, colIndex) => (
            <td key={colIndex} className="">
                {row[header]}
            </td>
            ))}
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
            {headers.map((header, colIndex) => (
            <td key={colIndex} className="">
                {row[header]}
            </td>
            ))}
        </tr>
        ))}
    </tbody>
    </table>
);
};
