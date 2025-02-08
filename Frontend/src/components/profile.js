import React,{useEffect, useState} from 'react';
import Icon from '@mdi/react';
import { mdiAccount, mdiBellOutline, mdiPlus } from '@mdi/js';
import 'bootstrap/dist/css/bootstrap.min.css';
import { PageHeader } from './pageHeader';
import { getEmployeeById, getManagerByEmpId } from '../services/employeeSecured';
import { useNavigate } from 'react-router-dom';

export const Profile = () =>{
  const [emp,setEmp] = useState({})
  const user = JSON.parse(localStorage.getItem('user'))
  const [id,setId] = useState()
  const [mng,setMng] = useState({})

  const navigate = useNavigate()

  useEffect(()=>{
    setId(user.username)
    getEmployeeDetails();
    getManagerDetails();
  },[])

  const getEmployeeDetails =()=>{
    getEmployeeById(user.username).then((response)=>{
     
      setEmp(response.data)
      console.log(response.data)
    }).catch((error)=>{
      console.log(error)
      console.log(emp)
    })
  }
  const getManagerDetails =()=>{
    getManagerByEmpId(user.username).then((response)=>{
     
      setMng(response.data)
      console.log(response.data)
    }).catch((error)=>{
      console.log(error)
      console.log(emp)
    })
  }
  if(localStorage.getItem('user')==null){
    alert("Please loging to access the page")
    navigate("/")
    return (
      <>
      
      Please Login in to access the Page
      </>
    )
  }
    return(
        <>
        <PageHeader icon={mdiAccount} pageName='Profile Page' />
        <div class="col-md-7 grid-margin stretch-card">
                <div class="card">
                  <div class="card-body">
                  <h4 class="card-title">Employee Details</h4>
                    <h4 class="card-title">Id : {emp.associateId}</h4>
                    <p class="card-description">Role : {emp.designation}</p>
                    <ul class="list-ticked">
                    <li>{emp.name}</li>
                      <li>{emp.email}</li>
                      <li>Allowed to Upload Bills</li>
                      <li>Bank Account is Active</li>
                    </ul>
                  </div>
                </div>
              </div>
              <div class="col-md-5 grid-margin stretch-card">
                <div class="card">
                  <div class="card-body">
                  <h4 class="card-title">Manager</h4>
                    <h4 class="card-title">Id : {mng.associateId}</h4>
                    <p class="card-description">Role : {mng.designation}</p>
                    <ul class="list-ticked">
                      <li>{mng.name}</li>
                      <li>{mng.email}</li>
                      
                    </ul>
                  </div>
                </div>
              </div>
        </>
    )
}