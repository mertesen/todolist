import React, {useContext, useRef, useState} from 'react';
import Form from "react-validation/build/form";
import Input from "react-validation/build/input";
import CheckButton from "react-validation/build/button";
import { useHistory } from 'react-router-dom';
import {CredentialsContext} from "../App";
import AuthService from "../services/auth.service";

const required = (value) => {
    if (!value) {
      return (
        <div className="alert alert-danger" role="alert">
          This field is required!
        </div>
      );
    }
  };

  export const handleErrors = async (response) => {
    if (!response.ok) {
        const {message} = await response.json();
        throw Error(message);
    }
    return response.json;
}

export default function Login() {
    const[username, setUsername] = useState("");
    const[password, setPassword] = useState("");
    const[error, setError] = useState("");
    const [credentails, setCredentials] = useContext(CredentialsContext);

    const history = useHistory();
    const form = useRef();

    

    const login = (e) => {
        e.preventDefault();        
        AuthService.login(username, password).then(() => {
            setCredentials({username,password,})
            history.push("/");
        }).catch((error) => {
            setError(error.message)
        });
    }

    return (
        <div>
            <div>
                <h1>Login</h1>
                {error && <span style={{ color: "red" }}>{error}</span>}
                <form onSubmit={login}>
                    <input 
                        onChange={(e) => setUsername(e.target.value)}
                        placeholder="username"
                        validations={[required]}
                        />
                    <br/>
                    <input
                        type="password"
                        onChange={(e) => setPassword(e.target.value)}
                        placeholder="password"
                        validations={[required]}/>
                    <br/>
                    <br/>
                    <button type="submit">Login</button>
                </form>
            </div>
        </div>
    )
}
