import React from "react";

interface TextareaProps extends React.TextareaHTMLAttributes<HTMLTextAreaElement> {
  label?: string;
}

const Textarea: React.FC<TextareaProps> = ({ label, ...props }) => {
  return (
    <div className="flex flex-col">
      {label && <label className="font-medium mb-1">{label}</label>}
      <textarea className="border p-2 rounded w-full" {...props}></textarea>
    </div>
  );
};

export default Textarea;
