import React, { useEffect, useState } from 'react';
import { Notification } from './notification';
import 'bootstrap/dist/css/bootstrap.min.css';

import logo from '../images/logo.png';
import Icon from '@mdi/react';
import { mdiMenu } from '@mdi/js';
import { mdiLogout } from '@mdi/js';
import { mdiAccountArrowLeftOutline } from '@mdi/js';
import { Link } from 'react-router-dom';
import profile_pic from '../images/profile_pic.jpg';
import { getEmployeeById } from '../services/employeeSecured';


export const Header = () => {
  const user = JSON.parse(localStorage.getItem('user'))
  const [empName,setEmpName] = useState()
  useEffect(()=>{
    getEmployeeById(user.username).then((response)=>{
      setEmpName(response.data.name)
    }).catch((error)=>{
      console.log(error)
    })
  },[])
    return (
        <>
        <nav className="navbar default-layout-navbar col-lg-12 col-12 p-0 fixed-top d-flex flex-row bg-danger text-light border-info">
        <div className="text-center navbar-brand-wrapper d-flex align-items-center justify-content-center">
          <a className="navbar-brand brand-logo" href="index.html"><img src={logo} alt="logo" /></a>
          <a className="navbar-brand brand-logo-mini" href="index.html"><img src={logo} alt="logo" /></a>
        </div>
        <div className="navbar-menu-wrapper d-flex align-items-stretch">
          <button className="navbar-toggler navbar-toggler align-self-center" type="button" data-toggle="minimize">
            <span className="mdi mdi-menu text-light"></span>
            {/* <Icon path={mdiMenu} size={.8} className="text-light"/> */}
          </button>
          <ul className="navbar-nav navbar-nav-right">
          {/* <Notification /> */}
            <li className="nav-item nav-profile dropdown">
            <a class="nav-link dropdown-toggle" id="profileDropdown" href="#" data-bs-toggle="dropdown" aria-expanded="false">
                <div class="nav-profile-img">
                  <img src={profile_pic} alt="image" />
                  {(user.username!=null)?(
                    <span class="availability-status online"></span>
                  ):(
                    <span class="availability-status offline"></span>
                  )}
                  
                </div>
                <div class="nav-profile-text text-light">
                  <p class="mb-1 text-light">{empName}</p>
                </div>
              </a>
              <div className="dropdown-menu navbar-dropdown" aria-labelledby="profileDropdown">
                <Link className="dropdown-item" to="profile">
                  <i className="mdi mdi-logout me-2 text-primary"></i> 
                  <Icon path={mdiAccountArrowLeftOutline } size={.8} className="text-info mx-1"/>
                  Profile </Link>
                  <Link className="dropdown-item" to="/logout">
                  <i className="mdi mdi-logout me-2 text-primary"></i> 
                  <Icon path={mdiLogout } size={.8} className="text-info mx-1"/>
                  Signout </Link>  
              </div>
            </li>
           
          </ul>
          {/* <button class="navbar-toggler navbar-toggler-right d-lg-none align-self-center" type="button" data-toggle="offcanvas">
            <span class="mdi mdi-menu"></span>
            <Icon path={mdiMenu} size={.8} className="text-light"/>
          </button> */}
        </div>
      </nav>
     
        </>
    )
}