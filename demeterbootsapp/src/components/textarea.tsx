import React from "react";

interface TextareaProps extends React.TextareaHTMLAttributes<HTMLTextAreaElement> {
  label?: string;
}

const Textarea: React.FC<TextareaProps> = ({ label, ...props }) => {
  return (
    <div className="txt-area">
      <textarea className="txt-val" placeholder=' ' {...props}></textarea>
      {label && <label className="txt-lbl">{label}</label>}
    </div>
  );
};

export default Textarea;
