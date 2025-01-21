import React from 'react';
import { AddExpense } from './addExpense';
import {Routes,Route} from 'react-router-dom';
import { PendingExpense } from './pending';
import { DashBoard } from './dashboard';
import { Profile } from './profile';
import { ExpenseReport } from './expenseReport';
import { ViewExpense } from './viewExpense';
import { ApprovedExpense } from './approved';
import { RejectedExpense } from './rejected';
import { Home } from './home';
import { Logout } from './logout';
import { LineGraph } from './graph';
import { Category } from './category';


export const DisplayArea = () =>{
    return(
        <>
        <div className="main-panel">
          <div className="content-wrapper">
          <div class="row">
          
            <Routes>
              
            <Route exact path="/profile" element={<Profile />} />
            <Route exact path="/" element={<DashBoard />} />
                <Route exact path="/addExpense" element={<AddExpense />} />
                <Route exact path="/updateExpense/:id" element={<AddExpense />} />
                <Route exact path="/pendingExpense" element={<ExpenseReport status='pending'/>} />
                <Route exact path="/approvedExpense" element={<ExpenseReport status='approved'/>} />
                <Route exact path="/rejectedExpense" element={<ExpenseReport status='rejected'/>} />
                <Route exact path="/viewExpense/:id" element={<ViewExpense />} />
                <Route exact path="/category" element={<Category />} />
                <Route path="/logout" element={<Logout/>}></Route>
            </Routes>
           
            </div>
          </div>
          </div>
        </>
    )
}