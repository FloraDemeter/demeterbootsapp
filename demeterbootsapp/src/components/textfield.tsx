import React from "react";

interface TextFieldProps extends React.InputHTMLAttributes<HTMLInputElement> {
  label?: string;
}

const TextField: React.FC<TextFieldProps> = ({ label, ...props }) => {
  return (
    <div className="flex flex-col">
      {label && <label className="font-medium mb-1">{label}</label>}
      <input type="text" className="border p-2 rounded w-full" {...props} />
    </div>
  );
};

export default TextField;
