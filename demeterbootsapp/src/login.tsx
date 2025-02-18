import React from "react";
import TextField from "./components/textfield";
import { Button } from "./components/button";

const Login: React.FC = () => {
    return (
        <div>
            <h1>Login</h1>
            <form>
                <TextField label="Username" />
                <TextField label="Password" type="password" />
                <Button variant="primary" type="submit" >Login</Button>
            </form>
        </div>
    );
};

export default Login;