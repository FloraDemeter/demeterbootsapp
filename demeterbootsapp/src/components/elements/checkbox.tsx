import React, { useState, useRef, useEffect} from "react";

interface CheckboxProps extends React.InputHTMLAttributes<HTMLInputElement> {
    label: string;
    defaultChecked?: boolean;
}

const Checkbox: React.FC<CheckboxProps> = ({ label, defaultChecked=false, ...props }) => {
    const [isChecked, setIsChecked] = useState(defaultChecked);

    useEffect(() => {
        setIsChecked(defaultChecked);
    }, [defaultChecked]);

    const handleClick = () => {
        setIsChecked((prev) => !prev);
    };

    return (
        <div className={`checkbox-field ${isChecked ? "checked" : ""}`} onClick={handleClick}>
            <input 
                type="checkbox" 
                className="checkbox-input"
            />
            <label className="checkbox-label">{label}</label>
        </div>
    );
};

export default Checkbox;
