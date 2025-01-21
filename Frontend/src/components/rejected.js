// import React, { useEffect, useState } from 'react';
// import Icon from '@mdi/react';
// import { mdiBellOutline, mdiLanPending } from '@mdi/js';
// import 'bootstrap/dist/css/bootstrap.min.css';
// import { PageHeader } from './pageHeader';
// import { Link } from 'react-router-dom';
// import { deleteExpenseById, getAllExpenses, getAllExpensesByStatus, getAllExpensesByStatusByEmpId, getExpensesByStatusPagination } from '../services/expenseService';
// import { useNavigate } from 'react-router-dom';
// import { Pagination } from './pagination';

// export const RejectedExpense = ({role}) =>{

//   const navigate = useNavigate()

//   const user = JSON.parse(localStorage.getItem('user'))

//   const [expenses,setExpenses] = useState([])
//   const [pageMax,setPageMax] = useState(1)

//   const [pageNo,setPageNo] = useState(1)

//   const getPageNo = (no) =>{
//     setPageNo(no)
//   }

//   useEffect(()=>{
//    findAllExpenses();
//    if(user.role=='Manager'){
//     getAllExpensesByStatus('rejected').then((response)=>{
//      setPageMax(Math.ceil(response.data.length/5))
//     })
//    }
//   },[pageNo])

//   const findAllExpenses=()=>{
//     if(user.role=='Manager'){
//       getExpensesByStatusPagination("rejected",pageNo).then((response) => {
//         console.log(response.data)
//       setExpenses(response.data)
//       console.log(expenses)
//     }).catch(error=>{
//       console.log(error)
//     })
//   }
// else{
//   getAllExpensesByStatusByEmpId("rejected",user.username).then((response) => {
//     console.log(response.data)
//   setExpenses(response.data)
//   console.log(expenses)
// }).catch(error=>{
//   console.log(error)
// })
// }
// }

//   const editExpense =(id)=>{
//     navigate("/updateExpense/"+id)
//   }

//   const deleteExpense = (id)=>{
//     if(window.confirm("Are you sure want to delete")){
//     deleteExpenseById(id).then((response)=>{
//       console.log(response.data)
//     }).catch((error)=>{
//       console.log(error)
//     })
//   }
//   }

//   const viewExpenseAdmin =(id)=>{
//     navigate("/viewExpense/"+id)
//   }
  
//     return(
//         <>
//          <div class="col-12 grid-margin">
//                 <div className="card">
//                   <div className="card-body">
//                     <h4 className="card-title">All Rejected Expenses</h4>
//                     <p className="card-description"> 
//                     </p>
//                     <table className="table table-hover">
//                       <thead>
//                         <tr>
//                           <th>UserID</th>
//                           <th>Category</th>
//                           <th>Date</th>
//                           <th>Amount</th>
//                           <th>Controls</th>
//                           {/* <th classNameName='text-start'>Controls</th> */}
                          
//                         </tr>
//                       </thead>
//                       <tbody>
//                       {expenses.map((expense)=>
//                         (<tr>
//                           <td>{expense.associateId}</td>
//                           <td>{expense.category}</td>
//                           <td >{expense.submissionDate[0]}-{expense.submissionDate[1]}-{expense.submissionDate[2]} </td>
//                           <td>{expense.amount}</td>
//                           <td>
//                             <button className='btn btn-primary btn-sm' onClick={()=>viewExpenseAdmin(expense.expenseId)}>View</button>
//                           </td>
//                             {/* {(role=='user')?( 
//                              <tr>
//                             <td>
//                             <button className='btn btn-primary btn-sm' onClick={()=>editExpense(expense.expenseId)}>Edit</button>
//                           </td>
//                           <td>
//                             <button className='btn btn-outline-danger btn-sm' onClick={()=>deleteExpense(expense.expenseId)}>Delete</button>
//                           </td>
//                           </tr>
//                           ):( 
//                           <tr>
//                           <td>
//                             <button className='btn btn-primary btn-sm'><Link to="/viewExpense">View</Link></button>
//                           </td>
//                           </tr>
//                           )} */}
                         
//                         </tr>)
// )}
//                       </tbody>
//                     </table>
//                   </div>
//                 </div>
//               </div>
//               {(user.role=='Manager')?(<Pagination getPageNo={getPageNo} pageMax={pageMax}/>):(<></>)} 
//         </>

//     )}