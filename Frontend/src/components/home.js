import React, { useEffect, useState } from 'react';
import { Header } from './header';
import '../App.css';
import { SideBar } from './sidebar';
import { DisplayArea } from './displayArea';
import { useNavigate } from 'react-router-dom';
import LoginComponent from './LoginComponent';

export const Home = () => {
    const navigate = useNavigate();
    const [isSidebarOpen, setIsSidebarOpen] = useState(true);

    const toggleSidebar = () => {
        setIsSidebarOpen(!isSidebarOpen);
    };

    const logout = () => {
        localStorage.removeItem("user");
        console.log(localStorage.getItem("user"));
        
        navigate("/");
        window.location.reload();
    };

    useEffect(() => {
        const mediaQuery = window.matchMedia("(max-width: 991px)");

        const handleMediaQueryChange = (e) => {
            if (e.matches) {
                setIsSidebarOpen(false);
                console.log("The screen width is less than or equal to 991px");
            } else {
                setIsSidebarOpen(true);
                console.log("The screen width is greater than 991px");
            }
        };

        // Add event listener for changes in the media query
        mediaQuery.addEventListener('change', handleMediaQueryChange);

        // Initial check
        handleMediaQueryChange(mediaQuery);

        // Cleanup on component unmount
        return () => {
            mediaQuery.removeEventListener('change', handleMediaQueryChange);
        };
    }, []);  // Empty dependency array to run once when the component mounts

    useEffect(() => {
        const interval = setInterval(logout, 1000 * 60 * 60 * 10);
        
        return () => clearInterval(interval); // Cleanup interval on component unmount
    }, []);

    return (
        <>
            <div className="container-scroller">
                <Header toggleSidebar={toggleSidebar} isSidebarOpen={isSidebarOpen} />
                <div className="container-fluid page-body-wrapper">
                    <SideBar isSidebarOpen={isSidebarOpen} toggleSidebar = {toggleSidebar}/>
                    <DisplayArea />
                </div>
            </div>
        </>
    );
};
