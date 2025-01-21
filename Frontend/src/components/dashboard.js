import React,{useState} from 'react';
import Icon from '@mdi/react';
import { mdiBellOutline, mdiHome } from '@mdi/js';
import 'bootstrap/dist/css/bootstrap.min.css';
import demo_card from '../images/demo_card.jpg';
import { LineGraph } from './graph';
import { PendingExpense } from './pending';
import { PageHeader } from './pageHeader';
import { CardDisplay } from './cardDisplay';
import { EmployeeReport } from './employeeReport';

export const DashBoard = () =>{
    const [role,setRole] = useState("user")
    const user=JSON.parse(localStorage.getItem('user'))
    return(
        <>
        <PageHeader icon={mdiHome} pageName='DashBoard'/>
        <CardDisplay />
       
        <div className='row'>
        <PendingExpense role='Admin'/>
        </div>
        <EmployeeReport />
        {(user.role=='Manager')?(
             <LineGraph />
        ):(
            <></>
        )}

        
       
        </>

    )}