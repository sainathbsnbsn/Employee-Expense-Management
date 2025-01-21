import React,{useEffect, useState} from 'react';
import Icon from '@mdi/react';
import { mdiBellOutline, mdiPlus } from '@mdi/js';
import 'bootstrap/dist/css/bootstrap.min.css';
import demo_card from '../images/demo_card.pdf';
import { PageHeader } from './pageHeader';
import { addExpense, deleteExpenseById, getExpenseById, getExpenseByIdByEmpId, getExpenseReceiptById, updateExpense } from '../services/expenseService';
import { json, useNavigate, useParams } from 'react-router-dom';
import { getAllCategory, getCategoryById } from '../services/categoryService';
import dateFormat from 'dateformat';
import {Document,Page} from 'react-pdf'


export const ViewExpense = () =>{
    const navigate = useNavigate()
  const {id} = useParams()

  const d = new Date()

  const currDate = dateFormat(d,"yyyy-mm-dd")
  



  const user = JSON.parse(localStorage.getItem('user'))

    const [image, setImage] = useState(demo_card)
    const [bill,setBill] = useState("null")
    const [uploadBill,setUploadBill] = useState(" (Should be Within the Max Limit)")
    const [col,setCol] = useState('text-success')

    const [category,setCategory] = useState([])

    const [categoryId,setCategoryId] = useState()
    const [expenseId,setExpenseId] = useState()
    const [amount,setAmount] = useState()
    const [managerId,setManagerId] =useState()
    const [associateId,setAssociateId] =useState(user.username)
    const [status,setStatus] =useState("Pending")
    const [managerComments,setManagerComments] = useState("")
    const [employeeComments,setEmployeeComments] = useState()
    const [submissionDate,setSubmissionDate] = useState()
    const [approvedDate,setApprovedDate] = useState(currDate)
    const [receipt,setReceipt] = useState()
    const [businessPurpose,setBusinessPurpose] = useState()


    useEffect(()=>{
        
        if(id!=null && user.role=='Manager'){
          getExpenseById(id).then((response)=>{
            console.log(response.data)
            setExpenseId(response.data.expenseId)
            setSubmissionDate(response.data.submissionDate)
            setAmount(response.data.amount)
            setBusinessPurpose(response.data.businessPurpose)
            setCategoryId(response.data.categoryId)
            setCategory(response.data.category)
            setEmployeeComments(response.data.employeeComments)
            setStatus(response.data.status)
            setManagerComments(response.data.managerComments)
          }).catch((error)=>{
            console.log(error)
          })
          
        }
       if(id){
          getExpenseByIdByEmpId(id,user.username).then((response)=>{
            console.log(response.data)
            setExpenseId(response.data.expenseId)
            setSubmissionDate(response.data.submissionDate)
            setAmount(response.data.amount)
            setBusinessPurpose(response.data.businessPurpose)
            setCategoryId(response.data.categoryId)
            setCategory(response.data.category)
            setEmployeeComments(response.data.employeeComments)
            setStatus(response.data.status)
            setManagerComments(response.data.managerComments)
          }).catch((error)=>{
            console.log(error)
          })
          
        }
        if(id){
        getExpenseReceiptById(id).then((response)=>{
          const file = new Blob(
            [response.data], 
            {type: 'application/pdf'});
          const fileURL = URL.createObjectURL(file);
         setImage(fileURL)
        })

          }
  
      },[])

      const editExpense =()=>{
        navigate(`/updateExpense/${id}`)
      }
    

      const deleteExpense = (id)=>{
        if(window.confirm("Are you sure want to delete")){
        deleteExpenseById(id).then((response)=>{
         navigate('/pendingExpense')
          console.log(response.data)
        }).catch((error)=>{
          console.log(error)
        })
      }
      }

      const saveExpense = (e) => {
        var expense ={}
        e.preventDefault()
        if(e.target.value == "approved")
        expense ={expenseId,categoryId,amount,managerId,associateId,status:"approved",managerComments,employeeComments,submissionDate,approvedDate,receipt,businessPurpose,category}
        else
        expense ={expenseId,categoryId,amount,managerId,associateId,status:"rejected",managerComments,employeeComments,submissionDate,approvedDate,receipt,businessPurpose,category}
        console.log(expense)
        if(id){
          updateExpense(id,expense).then((response)=>{
            console.log(response.data)
            navigate("/")
          }).catch((error)=>{
            console.log(error)
          })
        }
    }

   

    return(
        <>
        <PageHeader icon={mdiPlus} pageName='Expense' />
        
         <div className="col-md-6 grid-margin stretch-card">
                <div className="card">
                  <div className="card-body">
                    <h4 className="card-title">Pending Expense</h4>
                    <p className="card-description"> Employee Id : {associateId}.</p>
                    <form className="forms-sample">
                      <div className="form-group">
                        <label for="exampleInputName1">Purpose : {businessPurpose}</label>
                       
                      </div>
                      <div className="form-group">
                        <label for="exampleInputEmail3">Submission Date : {submissionDate}</label>
                
                      </div>
                      <div className="form-group">
                        <label for="exampleSelectCategory">Category : {categoryId}</label>
                       
                      </div>
                      <div className="form-group">
                        <label for="exampleInputPassword4">Amount : {amount}</label>
                       
                      </div>
                     
                      <div className="form-group">
                        <label for="exampleTextarea1">User Comments </label>
                        <p className="form-control-plaintext" value={employeeComments} id="exampleTextarea1" rows="1">{employeeComments}</p>
                      </div>
                      {/* <div className="form-group">
                        <label for="exampleInputEmail3">Approve Date</label>
                        <input type="date" className="form-control" id="exampleInputEmail3" placeholder="Approved on .."  onChange={(e)=>setApprovedDate(e.target.value)} required='true'/>
                      </div> */}
                      <div className="form-group">
                        <label for="exampleTextarea1">Reply</label>
                       
                        <textarea className="form-control" id="exampleTextarea1" rows="4" value={managerComments} onChange={(e)=>setManagerComments(e.target.value)} disabled={status!='Pending'}></textarea>
                      </div>
                      
                      {(user.role=='Manager')?(
                        <>
                        {(status=='approved' || status=='rejected')?<>
                        <button type="submit" className="btn bg-primary me-2 text-light" value="approved" disabled={true}>{status}</button>
                        </>:
                         <>
                         <button type="submit" className="btn bg-primary me-2 text-light" value="approved" onClick={(e)=>saveExpense(e)}>Approve</button>
                         <button className="btn btn-outline-danger" value="rejected" onClick={(e)=>saveExpense(e)}>Reject</button>
                         </>
                        }
                        </>
                      ):(
                        <>
                         {(status=='approved' || status=='rejected')?<>
                        <button type="submit" className="btn bg-primary me-2 text-light" value="approved" disabled={true}>{status}</button>
                        </>:
                         <>
                        <button type="submit" className="btn bg-primary me-2 text-light" onClick={()=>editExpense()}>Update</button>
                      <button className="btn btn-outline-danger" onClick={()=>deleteExpense(expenseId)}>Delete</button>
                      </>
                       }
                       </>
                      )}
                      
                    </form>
                  </div>
                </div>
              </div>
              <div className="col-md-6 grid-margin stretch-card">
                <div className="card">
                
                <div className="card-body">
                    <h4 className="card-title">{(image!=demo_card)?('Your Bill Receipt'):('Demo Bill Receipt')}</h4>
                    <p className="card-description"> The receipts shold be valid.</p>
                    
                       {/* <Document file={image} >
                        <Page pageNumber={1} />
                      </Document>  */}
                    <iframe src={image} width='100%' height='550px'></iframe>
                    
                    </div>
                   
              
                </div>
                </div>
        </>
    )
}