import React from 'react';
import { Header } from './header';
import '../App.css';
import { SideBar } from './sidebar';
import { DisplayArea } from './displayArea';
import { useNavigate } from 'react-router-dom';
import LoginComponent from './LoginComponent';

export const Home = () => {
    const navigate = useNavigate()

   
    const logout = () =>{
        localStorage.removeItem("user");
        console.log(localStorage.getItem("user"))
        
        navigate("/")
        window.location.reload(true)
    }

    setInterval(logout,1000*60*60*10)

   
    return (
        <>
        <div className="container-scroller">
            <Header />
            <div className="container-fluid page-body-wrapper">
                <SideBar/>
                <DisplayArea />
                
            </div>
        </div>
        </>
    )
}