import React from 'react';
import "../styling/components/components.scss";

interface DropDownProps {
    label?: string;
    options: {value: string, label: string}[];
    selectedValue: string;
    onChange: (value: string) => void;
}

const DropDown: React.FC<DropDownProps> = ({label, options, selectedValue, onChange}) => {
    return (
        <div className="dropdown-container">
            <select className="dropdown txt-val" value={selectedValue} onChange={(e) => onChange(e.target.value)}>
                {options.map((option) => (
                    <option key={option.value} value={option.value}>
                        {option.label}
                    </option>
                ))}
            </select>
            {label && <label className={`dropdown-label txt-lbl ${selectedValue ? "active" : ""}`}>
                {label}
            </label>}
        </div>
    );
};

export default DropDown;