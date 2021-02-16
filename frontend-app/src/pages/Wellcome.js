import React, { useContext } from 'react'
import { Link } from 'react-router-dom'

import {CredentialsContext} from "../App";
import Todos from '../components/Todos';
import AuthService from "../services/auth.service";


export default function Wellcome() {
    const [credentails, setCredentials] = useContext(CredentialsContext);
    const logout = () => {
        setCredentials(null);
        AuthService.logout();
    }
    return (
        <div>
            {credentails && <button onClick={logout}>Logout</button>}
            <br/>
            <h1>Welcome {credentails && credentails.username}</h1>
            {!credentails && <Link to="/register">Register</Link>}
            <br/>
            {!credentails && <Link to="/login">Login</Link>}
            <br/>
            {credentails && <Todos />}
        </div>
    )
}
