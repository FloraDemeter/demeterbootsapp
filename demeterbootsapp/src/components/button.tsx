import React, { useState } from "react";
import { fetchData } from "../services/api";

interface ButtonProps extends React.ButtonHTMLAttributes<HTMLButtonElement> {
    variant?: "primary" | "secondary";
}

const Button: React.FC<ButtonProps> = ({ variant = "primary",children, ...props }) => {

    const [data, setData] = useState<string | null>(null);

    const handleClick = async () => {
        console.log("Button clicked!");
        try {
            console.log("Fetching data...");
            const result = await fetchData(); // Call API
            setData(JSON.stringify(result, null, 2)); // Store API response
        } catch (error) {
            console.error("Error fetching data:", error);
        } finally {
            console.log("Data fetched!");
        }
    };

    return (
        <button className={`btn ${variant}`} onClick={handleClick} {...props}>
            {children}
        </button>
    );
};

export default Button;