import React from 'react';
import Icon from '@mdi/react';
import { mdiBellOutline, mdiBlockHelper, mdiLanPending, mdiRobotHappy } from '@mdi/js';
import 'bootstrap/dist/css/bootstrap.min.css';
import { Collapse } from 'bootstrap';


export const Notification = () =>{
    return (
        <>
         <li className="nav-item dropdown">
              <a className="nav-link count-indicator dropdown-toggle" id="notificationDropdown" href="#" data-bs-toggle="dropdown">
                <i className="mdi mdi-bell-outline"></i>
                <Icon path={mdiBellOutline} size={.8} className="text-light"/>
                <span className="count-symbol bg-danger border-none"></span>
              </a>
              <div className="dropdown-menu dropdown-menu-right navbar-dropdown preview-list" aria-labelledby="notificationDropdown">
                <h6 className="p-3 mb-0">Notifications</h6>
                <div className="dropdown-divider"></div>
                <a className="dropdown-item preview-item">
                  <div className="preview-thumbnail">
                    <div className="preview-icon bg-success">
                      <i className="mdi mdi-calendar"></i>
                      <Icon path={mdiLanPending} size={.8} className="text-light"/>
                    </div>
                  </div>
                  <div className="preview-item-content d-flex align-items-start flex-column justify-content-center">
                    <h6 className="preview-subject font-weight-normal mb-1">Pending Expense</h6>
                    <p className="text-gray ellipsis mb-0"> A comment from Manager </p>
                  </div>
                </a>
                <div className="dropdown-divider"></div>
                <a className="dropdown-item preview-item">
                  <div className="preview-thumbnail">
                    <div className="preview-icon bg-warning">
                      <i className="mdi mdi-settings"></i>
                      <Icon path={mdiBlockHelper} size={.8} className="text-light"/>
                    </div>
                  </div>
                  <div className="preview-item-content d-flex align-items-start flex-column justify-content-center">
                    <h6 className="preview-subject font-weight-normal mb-1">Rejected Expense</h6>
                    <p className="text-gray ellipsis mb-0"> Manager rejected expense </p>
                  </div>
                </a>
                <div className="dropdown-divider"></div>
                <a className="dropdown-item preview-item">
                  <div className="preview-thumbnail">
                    <div className="preview-icon bg-info">
                      <i className="mdi mdi-link-variant"></i>
                      <Icon path={mdiRobotHappy} size={.8} className="text-light"/>
                    </div>
                  </div>
                  <div className="preview-item-content d-flex align-items-start flex-column justify-content-center">
                    <h6 className="preview-subject font-weight-normal mb-1">Approved Expense</h6>
                    <p className="text-gray ellipsis mb-0"> Your expense have being Approved </p>
                  </div>
                </a>
        </div>
            </li>
        </>
    )
}