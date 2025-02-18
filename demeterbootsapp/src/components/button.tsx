import React, { useState } from "react";
import { fetchData } from "../services/api";

interface ButtonProps extends React.ButtonHTMLAttributes<HTMLButtonElement> {
    variant?: "primary" | "secondary";
}

interface LinkProps extends React.AnchorHTMLAttributes<HTMLAnchorElement> {
    variant?: "primary" | "secondary";
}

export const Button: React.FC<ButtonProps> = ({ variant = "primary",children, ...props }) => {
    return (
        <button className={`btn ${variant}`} {...props}>
            {children}
        </button>
    );
};

export const RedirectButton: React.FC<LinkProps> = ({variant = "primary", children, ...props}) => {
    return (
        <a className={`redirect ${variant}`} {...props}>
            {children}
        </a>
    );
};