import React, { useEffect, useState } from 'react';
import Icon from '@mdi/react';
import { mdiBellOutline, mdiLanPending } from '@mdi/js';
import 'bootstrap/dist/css/bootstrap.min.css';
import { PageHeader } from './pageHeader';
import { Link } from 'react-router-dom';
import { deleteExpenseById, getAllExpenses, getAllExpensesByStatus, getAllExpensesByStatusByEmpId, getExpensesByStatusByCategory, getExpensesByStatusByCategoryByEmpId, getExpensesByStatusPagination } from '../services/expenseService';
import { useNavigate } from 'react-router-dom';
import { getAllCategory, getCategoryById, getCategoryByStatus } from '../services/categoryService';
import { Pagination } from './pagination';
import { Loader } from './loader';

export const PendingExpense = ({role,status='Pending'}) =>{

  const navigate = useNavigate()

  const [expenses,setExpenses] = useState([])

  const user= JSON.parse(localStorage.getItem('user'))

  const [isEmployee,setIsEmployee] = useState(false)

  const [controls,setControls]= useState('Manager')

  const [pageMax,setPageMax] = useState(1)

  const [pageNo,setPageNo] = useState(1)

  const [isLoading,setIsLoading] = useState(false)

  const [categoryId,setCategoryId]=useState(1)

  const [categoryList,setCategoryList] = useState([])

  const getPageNo = (no) =>{
    setPageNo(no)
  }


  useEffect(()=>{
    if(user.role!="Manager" || isEmployee){
      if(categoryId!=1){
        findAllExpensesByCategoryByEmpId();
      }
      else{
      findAllExpensesByEmpId();
      }
    }
    else{
      if(categoryId!=1){
        findAllExpensesByCategory();
      }else{
      findAllExpenses();
      getAllExpensesByStatus(status).then((response)=>{
       setPageMax(Math.ceil(response.data.length/5))
      })
    }
    }
 
    setControls(isEmployee?'Employee':'Manager')
  },[isEmployee,pageNo,categoryId,status])

  useEffect(()=>{
    getCategories();
  },[])

const getCategories = () => {
getCategoryByStatus('active').then((response) => {
  setCategoryList(response.data)
  
}).catch(error=>{
  console.log(error)
})
}

  const findAllExpensesByCategory=()=>{
    setIsLoading(true)
    getExpensesByStatusByCategory(status,categoryId).then(response=>{
      setIsLoading(false)
    setExpenses(response.data)
      console.log(expenses)
      
    }).catch(error=>{
      setIsLoading(false)
      console.log(error)
     
    })
  }

  const findAllExpensesByCategoryByEmpId=()=>{
    setIsLoading(true)
    getExpensesByStatusByCategoryByEmpId(status,categoryId,user.username).then(response=>{
      setIsLoading(false)
    setExpenses(response.data)
      console.log(expenses)
      
    }).catch(error=>{
    
      setIsLoading(false)
      console.log(error)
     
     
    })
  }

  const findAllExpenses=()=>{
   
    getExpensesByStatusPagination(status,pageNo).then((response) => {
      
      setExpenses(response.data)
      console.log(expenses)
      
    }).catch(error=>{
      console.log(error)
    
     
    })
  }

  const findAllExpensesByEmpId=()=>{
    setIsLoading(true)
    getAllExpensesByStatusByEmpId(status,user.username).then((response) => {
      setExpenses(response.data)
      console.log(expenses)
      console.log(response.data)
      setIsLoading(false)
    }).catch(error=>{
      console.log(error)
      console.log(user.username)
      setIsLoading(false)
    })
  }

  

  const editExpense =(id)=>{
    navigate(`/updateExpense/${id}`)
  }

  const viewExpenseAdmin =(id)=>{
    navigate(`/viewExpense/${id}`)
  }

  const deleteExpense = (id)=>{
    if(window.confirm("Are you sure want to delete")){
    deleteExpenseById(id).then((response)=>{
      findAllExpensesByEmpId();
      console.log(response.data)
    }).catch((error)=>{
      console.log(error)
    })
  }

  
  }
  
    return(
        <>
         <div className='col-md-4'>
         {(user.role=='Manager')?(
          <div class="form-group  mb-2">
          <label for="inputPassword1" class="sr-only">Category</label>
            <select className="form-control" id="exampleSelectCategory"  onChange={(e)=>setCategoryId(e.target.value)}>
              <option value="1">All</option>
              { categoryList.map((type)=>(<option value={type.id}>{type.name}</option>))}
            </select>
          </div>
         ):(<></>)}
        </div>
        {isLoading?(<Loader/>):(
          <>
         
          <div class="col-12 grid-margin">
                <div className="card">
                  <div className="card-body">
                    <h4 className="card-title">All {status} Expenses {pageNo} - {pageMax}</h4>
                   {(user.role!='Manager')?(<p className="card-description" > Edit / Delete 
                    </p>):(<></>)} 
                    {(user.role=='Manager')?(<button className='btn btn-outline-primary btn-sm' onClick={()=>setIsEmployee(isEmployee?false:true)}>{controls} Controls</button>):(<></>)}
                    <div className='table-content'>
                    <table className="table table-hover">
                      <thead>
                        <tr>
                          <th>UserID</th>
                          <th>Category</th>
                          <th>Date</th>
                          <th>Amount</th>
                          <th classNameName='text-start'>Controls</th>
                          
                        </tr>
                      </thead>
                      <tbody>
                      {expenses.map((expense)=>
                        (
                          
                        <tr>
                          <td>{expense.associateId}</td>
                          <td >{expense.category}</td>
                          <td >{expense.submissionDate[0]}-{expense.submissionDate[1]}-{expense.submissionDate[2]} </td>
                          <td>{expense.amount}</td>
                         
                            {((user.role!='Manager' || isEmployee) && status=='Pending')?( 
                             <tr>
                            <td>
                            <button className='btn btn-primary btn-sm' onClick={()=>editExpense(expense.expenseId)}>Edit</button>
                          </td>
                          <td>
                            <button className='btn btn-outline-danger btn-sm' onClick={()=>deleteExpense(expense.expenseId)}>Delete</button>
                          </td>
                          </tr>
                          ):( 
                          <tr>
                          <td>
                            <button className='btn btn-primary btn-sm' onClick={()=>viewExpenseAdmin(expense.expenseId)}>View</button>
                          </td>
                          </tr>
                          )}
                         
                        </tr>)
                          )}
                      </tbody>
                    </table>
                    </div>
                  </div>
                </div>
              </div>
              {(user.role=='Manager' && categoryId==1)?(<Pagination getPageNo={getPageNo} pageMax={pageMax}/>):(<></>)} 
          </>
        )}
          
        </>

                            )}
