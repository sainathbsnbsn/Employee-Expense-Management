import React,{useState} from 'react';
import Icon from '@mdi/react';
import { mdiBellOutline } from '@mdi/js';
import 'bootstrap/dist/css/bootstrap.min.css';

export const PageHeader = ({icon,pageName}) => {
    return (
        <>
        <div class="page-header">
              <h3 class="page-title">
                <span class="page-title-icon bg-danger text-white me-2">
                  <i class="mdi mdi-home"></i>
                  <Icon path={icon} size={.8} className="text-light"/>
                </span> {pageName}
              </h3>
              <nav aria-label="breadcrumb">
                <ul class="breadcrumb">
                  <li class="breadcrumb-item active" aria-current="page">
                    <span></span>Overview <i class="mdi mdi-alert-circle-outline icon-sm text-primary align-middle"></i>
                  </li>
                </ul>
              </nav>
            </div>
        </>
    )
}