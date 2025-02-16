import React from "react";

interface CheckboxProps extends React.InputHTMLAttributes<HTMLInputElement> { 
    label: string;
}

const Checkbox: React.FC<CheckboxProps> = ({ label, ...props }) => {
    return (
        <label className="chk-container">
            <span className="chk-label">{label}</span>
            <input type="checkbox" className="chkbox" {...props} />
        </label>
    );
};

export default Checkbox;