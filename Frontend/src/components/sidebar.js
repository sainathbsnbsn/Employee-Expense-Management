import React from 'react';
import { Notification } from './notification';
import 'bootstrap/dist/css/bootstrap.min.css';
import {BrowserRouter as Routes, Link, Route } from 'react-router-dom';
import logo from '../images/logo.png';
import Icon from '@mdi/react';
import { mdiCancel, mdiController, mdiControllerClassic, mdiControllerClassicOutline, mdiGoodreads, mdiHome, mdiLanPending, mdiPlus, mdiSafe } from '@mdi/js';
import { mdiLogout } from '@mdi/js';


export const SideBar = ({isSidebarOpen, toggleSidebar}) => {
  const user = JSON.parse(localStorage.getItem('user'))
    return (
        <>
        <nav className={`sidebar sidebar-offcanvas ${isSidebarOpen ? 'open' : 'close'}`} id="sidebar">
          <ul className="nav">
            <li className="nav-item">
              <Link className="nav-link" to="/" onClick={toggleSidebar}>
                <span className="menu-title">Dashboard</span>
                <i className="mdi mdi-home menu-icon"></i>
                <Icon path={mdiHome } size={.8} className="text-secondary mx-1"/>
              </Link>
            </li>
            <li className="nav-item">
              <Link to="/addExpense" className="nav-link" onClick={toggleSidebar}>
                <span className="menu-title">Add Expense</span>
                <i className="mdi mdi-arrow-down-drop-circle-outline menu-icon"></i>
                <Icon path={mdiPlus } size={.8} className="text-secondary mx-1"/>
              </Link>
            </li>
            <li className="nav-item">
              <Link to="/pendingExpense" className="nav-link" onClick={toggleSidebar}>
                <span className="menu-title">Pending Expense</span>
                <i className="mdi mdi-arrow-down-drop-circle-outline menu-icon"></i>
                <Icon path={mdiLanPending } size={.8} className="text-secondary mx-1"/>
              </Link>
            </li>
            <li className="nav-item">
              <Link to="/approvedExpense" className="nav-link" onClick={toggleSidebar}>
                <span className="menu-title">Approved Expense</span>
                <i className="mdi mdi-arrow-down-drop-circle-outline menu-icon"></i>
                <Icon path={mdiSafe } size={.8} className="text-secondary mx-1"/>
              </Link>
            </li>
            <li className="nav-item">
              <Link to="/rejectedExpense" className="nav-link" onClick={toggleSidebar}>
                <span className="menu-title">Rejected Expense</span>
                <i className="mdi mdi-arrow-down-drop-circle-outline menu-icon"></i>
                <Icon path={mdiCancel } size={.8} className="text-secondary mx-1"/>
              </Link>
            </li>
            {(user.role=='Manager')?(
              <li className="nav-item">
              <Link to="/category" className="nav-link" onClick={toggleSidebar}>
                <span className="menu-title">Category Control</span>
                <i className="mdi mdi-arrow-down-drop-circle-outline menu-icon"></i>
                <Icon path={mdiController } size={.8} className="text-secondary mx-1"/>
              </Link>
            </li>
            ):(
              <></>
            )}
          </ul>
        </nav>
        </>
    )
}