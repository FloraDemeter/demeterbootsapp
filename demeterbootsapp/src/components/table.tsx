import React from "react";

interface TableProps {
  headers: string[];
  data: any[]; // You can refine this type based on your data structure
}

const Table: React.FC<TableProps> = ({ headers, data }) => {
  return (
    <table className="w-full border border-gray-300">
      <thead className="bg-gray-200">
        <tr>
          {headers.map((header, index) => (
            <th key={index} className="px-4 py-2 border">
              {header}
            </th>
          ))}
        </tr>
      </thead>
      <tbody>
        {data.map((row, rowIndex) => (
          <tr key={rowIndex} className="border">
            {headers.map((header, colIndex) => (
              <td key={colIndex} className="px-4 py-2 border">
                {row[header]}
              </td>
            ))}
          </tr>
        ))}
      </tbody>
    </table>
  );
};

export default Table;
