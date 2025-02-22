import React from "react";

interface TextFieldProps extends React.InputHTMLAttributes<HTMLInputElement> {
  label?: string;
}

const TextField: React.FC<TextFieldProps> = ({ label, ...props }) => {
  return (
    <div className="txt-fld">
         <input type="text" className="txt-val" placeholder=' ' {...props} />
        {label && <label className="txt-lbl">{label}</label>}
    </div>
  );
};

export default TextField;
