import React from 'react'
import { useNavigate } from 'react-router-dom';
import LoginComponent from './LoginComponent';


export const Logout = () =>{
        const navigate = useNavigate()
        localStorage.removeItem("user");
        console.log(localStorage.getItem("user"))
        
        navigate("/")
        window.location.reload(true)
        return(
            <></>
        )

}