import React,{useState} from 'react';
import Icon from '@mdi/react';
import { mdiBellOutline, mdiCancel, mdiHome, mdiLanPending, mdiSafe } from '@mdi/js';
import 'bootstrap/dist/css/bootstrap.min.css';
import { PendingExpense } from './pending';
import { PageHeader } from './pageHeader';
import { Pagination } from './pagination';

export const ExpenseReport =({status}) =>{
    if(status=='pending'){
        return (
            <>
            <PageHeader icon={mdiLanPending} pageName='Pending Expense' />
            <div class="row">
            <PendingExpense role='user' status='Pending'/>
            </div>
            </>
        )
    }
    else if(status=='approved'){
        return (
            <>
           
            <PageHeader icon={mdiSafe} pageName='Approved Expense' />
            <div class="row">
               
            <PendingExpense role='user' status='approved'/>
            </div>
            </>
        )
    }
    return (
        <>
        <PageHeader icon={mdiCancel} pageName='Rejected Expense' />
        <div class="row">
        <PendingExpense role='user' status='rejected'/>
        </div>
        </>
    )
}