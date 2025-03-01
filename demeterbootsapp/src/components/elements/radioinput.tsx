import React, { useState } from "react";

interface RadioButtonProps {
  name: string;
  options: { label: string; value: string }[];
}

const RadioButtonGroup: React.FC<RadioButtonProps> = ({ name, options }) => {
  const [selectedValue, setSelectedValue] = useState<string | null>(null);

  const handleChange = (value: string) => {
    setSelectedValue(value);
  };

  return (
    <div className="radio-group">
      {options.map((option) => (
        <label key={option.value} className={`radio-field ${selectedValue === option.value ? "selected" : ""}`}>
          <input
            type="radio"
            name={name}
            value={option.value}
            checked={selectedValue === option.value}
            onChange={() => handleChange(option.value)}
            className="radio-input"
          />
          <span className="radio-custom"></span>
          {option.label}
        </label>
      ))}
    </div>
  );
};

export default RadioButtonGroup;
