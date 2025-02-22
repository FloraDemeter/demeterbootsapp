import React from "react";
import { useNavigate } from "react-router-dom";
import TextField from "../../components/elements/textfield";
import { Button } from "../../components/elements/button";

const Login: React.FC = () => {
    const navigate = useNavigate();

    const handleLogin = (e: React.FormEvent) => {
        e.preventDefault();
        navigate("/landing");
    }
    return (
        <div className="login">
            <h1>Login</h1>
            <form onSubmit={handleLogin}>
                <TextField label="Username" />
                <TextField label="Password" type="password" />
                <Button variant="primary" type="submit" >Login</Button>
            </form>
        </div>
    );
};

export default Login;