import { Route, Routes } from "react-router-dom";
import { AddExpense } from "./components/addExpense";
import { ApprovedExpense } from "./components/approved";
import { DashBoard } from "./components/dashboard";
import { ExpenseReport } from "./components/expenseReport";
import { Home } from "./components/home";
import LoginComponent from "./components/LoginComponent";
import { Logout } from "./components/logout";
import { Profile } from "./components/profile";
import { RejectedExpense } from "./components/rejected";
import { ViewExpense } from "./components/viewExpense";

function App() {


  if(localStorage.getItem('user')==null){
    // alert("Please Login Before Accessing the site")
    return (
      <div className="App">
        <LoginComponent/>
        </div>
    )
}


    return (
      <div className="App">
        <Home/>
       {/* <Routes>
            <Route path="*" element={<Home/>}>
                </Route>
            </Routes> */}
       
      </div>
    );
  
 
}

export default App;
