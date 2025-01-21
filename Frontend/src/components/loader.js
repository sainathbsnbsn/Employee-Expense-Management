import React,{useEffect, useState} from 'react';
import Icon from '@mdi/react';
import { mdiBellOutline, mdiPlus } from '@mdi/js';
import 'bootstrap/dist/css/bootstrap.min.css';

export const Loader = () =>{
    return (
        <>
<div class="col-12 grid-margin">
    <div className="card align-items-center p-1">
        <div className="card-body">
        <div class="spinner-border text-danger mx-3" role="status">
        <span class="sr-only"></span>
        </div>
    </div>
</div></div>
        </>
    )
}