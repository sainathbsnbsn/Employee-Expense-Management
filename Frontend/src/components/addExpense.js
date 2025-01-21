import React,{useEffect, useState} from 'react';
import Icon from '@mdi/react';
import { mdiBellOutline, mdiPlus } from '@mdi/js';
import 'bootstrap/dist/css/bootstrap.min.css';
import demo_card from '../images/demo_card.pdf';
import { PageHeader } from './pageHeader';
import { addExpense, addExpenseReceipt, getExpenseById, getExpenseByIdByEmpId, getExpenseReceiptById, updateExpense } from '../services/expenseService';
import { useNavigate, useParams } from 'react-router-dom';
import { getAllCategory, getCategoryById, getCategoryByStatus } from '../services/categoryService';
import dateFormat from 'dateformat';
import { getEmployeeById, getManagerByEmpId } from '../services/employeeSecured';

export const AddExpense = () =>{

  const navigate = useNavigate()
  const {id} = useParams()

  const user = JSON.parse(localStorage.getItem('user'))

  const d = new Date()

  
  const currDate = dateFormat(d,"yyyy-mm-dd")

    const [image, setImage] = useState(demo_card)
    const [bill,setBill] = useState("null")
    const [uploadBill,setUploadBill] = useState(" (Should be Within the Max Limit)")
    const [col,setCol] = useState('text-success')
    const [billMax,setBillMax] = useState(false)

    const [categoryList,setCategoryList] = useState([])

    const [title,setTitle] = useState("Add")

    const [categoryId,setCategoryId] = useState()
    const [category,setCategory]= useState('')
    const [expenseId,setExpenseId] = useState()
    const [amount,setAmount] = useState()
    const [managerId,setManagerId] =useState()
    const [associateId,setAssociateId] =useState(user.username)
    const [status,setStatus] =useState("Pending")
    const [managerComments,setManagerComments] = useState('')
    const [employeeComments,setEmployeeComments] = useState()
    const [submissionDate,setSubmissionDate] = useState(currDate)
    const [approvedDate,setApprovedDate] = useState()
    const [formdata,setFormdata] = useState()
    const [businessPurpose,setBusinessPurpose] = useState()

    const [preExpense,setPreExpense] = useState();

    const [img,setImg] = useState(false)

    const receipt = new FormData();




    useEffect(()=>{
      
      if(id){
        getExpenseByIdByEmpId(id,user.username).then((response)=>{
          console.log(response.data)
          setSubmissionDate(response.data.submissionDate)
          setAmount(response.data.amount)
          setBusinessPurpose(response.data.businessPurpose)
          setCategoryId(response.data.categoryId)
          setCategory(response.data.category)
          setEmployeeComments(response.data.employeeComments)
          setTitle("Update")
         
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

    useEffect(()=>{
      getCategories();
      getManagerId();
    },[])

const getManagerId=()=>{
      getEmployeeById(user.username).then((response)=>{
        setManagerId(response.data.managerId)
      }).catch((error)=>{
        console.log(error)
      })
}

const getCategories = () => {
  getCategoryByStatus('active').then((response) => {
    setCategoryList(response.data)
    
  }).catch(error=>{
    console.log(error)
  })
}

const onImageChange = (event) => {
 if (event.target.files && event.target.files[0]) {
   setImage(URL.createObjectURL(event.target.files[0]));
  //  setReceipt(event)
  setFormdata(event.target.files[0])

  setImg(true)

  console.log(receipt)

   
 }


}

const handleUploadBill=(e)=>{
  if(e>bill){
    setAmount(e)
    setUploadBill("Your Amount Exceeds Maximum Limit "+bill)
    setCol('text-danger')
    setBillMax(true)
  }
  else{
    setAmount(e)
    setUploadBill(" (Should be Within the Max Limit "+bill+")")
    setCol('text-success')
    setBillMax(false)
  }
}

const saveExpense = (e) => {

  e.preventDefault()

  
  console.log(approvedDate)

  const expense ={expenseId,categoryId,amount,managerId,associateId,status,managerComments,employeeComments,submissionDate,approvedDate,businessPurpose,category}
  console.log(expense)
  if(id){
    updateExpense(id,expense).then((response)=>{
      console.log(response.data)
      receipt.append('file',formdata)
      addExpenseReceipt(response.data.expenseId,receipt).then(res=>{
        console.log(res.data)
      }).catch((error)=>{
        console.log(error)
      })
      navigate("/pendingExpense")
    }).catch((error)=>{
      console.log(error)
    })

   
  }
  else{
  addExpense(expense).then((response)=>{
    console.log(response.data)
    receipt.append('file',formdata)
    console.log(receipt)

    addExpenseReceipt(response.data.expenseId,receipt).then(res=>{
      console.log(res.data)
    }).catch((error)=>{
      console.log(error)
    })
    navigate("/pendingExpense")
  }).catch((e)=>{
    console.log(e)
  })

  }
}

const cancelExpense=()=>{
  navigate("/pendingExpense")
}

const handleAmount = (id) => {
  console.log(id)
  setCategoryId(id)
  
  getCategoryById(id).then((response)=>{
    setBill(response.data.limit)
    setCategory(response.data.name)
  }).catch((error)=>{
    console.log(error)
  })
                                        
}

    return(
        <>
        <PageHeader icon={mdiPlus} pageName='Expense' />
         <div className="col-md-6 grid-margin stretch-card">
                <div className="card">
                  <div className="card-body">
                    <h4 className="card-title">{title} Expense</h4>
                    
                    <p className="card-description"> Only Business Purpose receipts are added.</p>
                    <form className="forms-sample">
                      <div className="form-group">
                        <label for="exampleInputName1">Business Purpose</label>
                        <input type="text" className="form-control" id="exampleInputName1" placeholder="Subject" value={businessPurpose} onChange={(e)=>setBusinessPurpose(e.target.value)} />
                      </div>
                      {/* <div className="form-group">
                        <label for="exampleInputEmail3">Submission Date</label>
                        <input type="date" className="form-control" id="exampleInputEmail3" placeholder="Paid on .."  value={submissionDate} onChange={(e)=>setSubmissionDate(e.target.value)}/>
                      </div> */}
                
                      <div className="form-group">
                        <label for="exampleSelectCategory">Category</label>
                        <select className="form-control" id="exampleSelectCategory"   onChange={(e)=>handleAmount(e.target.value)} >
                          {(id)?(<option>{category}</option>):(<option>Select From Options</option>)}
                          { categoryList.map((type)=>(<option value={type.id}>{type.name}</option>))}
                          
                        </select>
                      </div>
                      <div className="form-group">
                        <label for="exampleInputPassword4">Amount <small className={col}>{uploadBill}</small></label>
                        <input type="number" className="form-control" id="exampleInputPassword4" value={amount} placeholder={"Maximum Approval Amount is "+bill}  onChange={(e)=>handleUploadBill(e.target.value)}/>
                      </div>
                      <div className="form-group">
                        <label>Upload Receipt</label>
                        <input type="file" className="file-upload-default"  onChange={onImageChange}/>
                        <div className="input-group col-xs-12">
                          <input type="file" className="form-control file-upload-info"  onChange={onImageChange} placeholder="Upload Image" accept='application/pdf' required={true}/>
                        </div>
                      </div>
                     
                      <div className="form-group">
                        <label for="exampleTextarea1">Comments</label>
                        <textarea className="form-control" id="exampleTextarea1" rows="4" value={employeeComments} onChange={(e)=>setEmployeeComments(e.target.value)}></textarea>
                      </div>
                      <button type="submit" className="btn bg-primary me-2 text-light" onClick={(e)=>saveExpense(e)} disabled={billMax} >Submit</button>
                      <button className="btn btn-light" onClick={(e)=>cancelExpense(e)}>Cancel</button><br/>
                      {(billMax)?(<small className='text-danger'>Exceeds Maximum Category Limit</small>):(<></>)}
                    </form>
                  </div>
                </div>
              </div>
              <div className="col-md-6 grid-margin stretch-card">
                <div className="card">
                
                <div className="card-body">
                    <h5 className="card-title">{(image!=demo_card)?('Your Bill Receipt'):('Demo Bill Receipt After uploading your bill will be display here')}</h5>
                    <p className="card-description"> The receipts shold be valid.</p>
                    <iframe className='card-img-top' alt="preview image" src={image} width='100%' height='550px'/>
                    </div>
                   
              
                </div>
                </div>
        </>
    )
}