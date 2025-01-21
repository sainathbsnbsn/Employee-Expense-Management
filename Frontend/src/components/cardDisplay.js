import React,{useEffect, useState} from 'react';
import Icon from '@mdi/react';
import { Card } from './card';


export const CardDisplay =()=>{
    // const [status,setStatus] = useState()
    // const [icon,setIcon] = useState()
    // const [bg,setBg] = useState()
    return (
        <>
         <div className="row">
            <Card status="Total" icon='mdiChartLine' bg='card bg-gradient-success card-img-holder text-white'/>
            <Card status="Approved" icon='mdiChartLine' bg='card bg-gradient-info card-img-holder text-white'/>
            <Card status="Pending" icon='mdiLanPending' bg='card bg-gradient-danger card-img-holder text-white'/>
         </div>
        </>
    )
}