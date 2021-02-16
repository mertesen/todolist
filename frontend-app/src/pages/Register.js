import React, {useContext, useState} from 'react';
import { useHistory } from 'react-router-dom';
import {CredentialsContext} from "../App";
import { handleErrors } from "./Login";
import Form from "react-validation/build/form";
import Input from "react-validation/build/input";
import CheckButton from "react-validation/build/button";

export default function Register() {
    const[email, setEmail] = useState("");
    const[username, setUsername] = useState("");
    const[password, setPassword] = useState("");
    const[error, setError] = useState("");
    const [credentails, setCredentials] = useContext(CredentialsContext);

    const history = useHistory();

    

    const register = (e) => {
        e.preventDefault();
        fetch("http://localhost:8080/api/user/register",{
            method:"POST",
            headers: {
                "Content-Type":"application/json",
            },
            body: JSON.stringify({
                email,
                username,
                password,
            }),
        })
        .then(handleErrors)
        .then(() => {
            history.push("/");
        })
        .catch((error) => {
            setError(error.message)
        })
    }
    return (
        <div>
            <div>
                <h1>Register</h1>
                {error && <span style={{ color: "red" }}>{error}</span>}
                <form onSubmit={register}>
                    <input 
                        onChange={(e) => setEmail(e.target.value)} 
                        placeholder="email"/>
                    <br/>
                    <input 
                        onChange={(e) => setUsername(e.target.value)}
                        placeholder="username"/>
                    <br/>
                    <input
                        type="password"
                        onChange={(e) => setPassword(e.target.value)}
                        placeholder="password"/>
                    <br/>
                    <button type="submit">Register</button>
                </form>
            </div>
        </div>
    )
}
