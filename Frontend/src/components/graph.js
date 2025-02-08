import React,{useEffect, useState} from 'react';
import Icon from '@mdi/react';
import { mdiBellOutline } from '@mdi/js';
import 'bootstrap/dist/css/bootstrap.min.css';
import demo_card from '../images/demo_card.jpg';
import { Chart as ChartJS} from "chart.js/auto";
import { Bar,Pie } from "react-chartjs-2";
import { getAmountByCategoryByStatusMonthWise } from '../services/expenseService';
import { getCategoryByStatus } from '../services/categoryService';

export const LineGraph = () =>{
    const [resultAmount,setResultAmount] = useState([0,0,0,0,0,0,0,0,0,0,0,0])
    const [year,setYear] = useState(2025)
    const [status,setStatus] = useState('Pending')
    const [categoryId,setCategoryId]=useState(0)

    const [categoryList,setCategoryList] = useState([])

    

   const [dataset,setDateset] = useState({
    labels: ["Jan", "Feb", "Mar", "Apl", "May", "Jun","Jul","Aug","Sep","Oct","Nov","Dec"],
    datasets: [{
      label: 'Total Amount Per Month',
      data: resultAmount,
      backgroundColor: [
        'rgba(255, 99, 132, 0.2)',
        'rgba(54, 162, 235, 0.2)',
        'rgba(255, 206, 86, 0.2)',
        'rgba(75, 192, 192, 0.2)',
        'rgba(153, 102, 255, 0.2)',
        'rgba(255, 159, 64, 0.2)'
      ],
      borderColor: [
        'rgba(255,99,132,1)',
        'rgba(54, 162, 235, 1)',
        'rgba(255, 206, 86, 1)',
        'rgba(75, 192, 192, 1)',
        'rgba(153, 102, 255, 1)',
        'rgba(255, 159, 64, 1)'
      ],
      borderWidth: 1
    }]
  })
   

    useEffect(()=>{
      console.log(year)
      getResult();
  
    },[year,status,categoryId])

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



    const getResult = () =>{
      console.log(year)
      console.log(status)
      const obj = {categoryId,status}
      getAmountByCategoryByStatusMonthWise(year,obj).then((response)=>{
        console.log(response.data)
        setResultAmount(response.data)
       
      }).catch((error)=>{
        console.log(error)
      })
    }

    
     
    return(
        <>
        <div class="row">
        <div className='col'>
        <div class="form-group  mb-2">
    <label for="inputPassword3" class="sr-only">Year</label>
    <select className="form-control" id="inputPassword3" onChange={(e)=>setYear(e.target.value)}>
            <option>Select Year</option>
            <option>2024</option>
            <option>2025</option>
            <option>2026</option>
          </select>
  </div>
  </div>
  <div className='col'>
        <div class="form-group  mb-2">
    <label for="inputPassword1" class="sr-only">Category</label>
    <select className="form-control" id="exampleSelectCategory"  onChange={(e)=>setCategoryId(e.target.value)}>
                          <option>Select From Options</option>
                          { categoryList.map((type)=>(<option value={type.id}>{type.name}</option>))}
                          
                        </select>
  </div>
  </div>
  <div className='col'>
  <div class="form-group  mb-2">
    <label for="inputPassword2" class="sr-only">Status</label>
    <select className="form-control" id="inputPassword2" onChange={(e)=>setStatus(e.target.value)}>
            <option value="Pending">Pending</option>
            <option value="approved">Approved</option>
            <option value="rejected">Rejected</option>
           
          </select>
  </div></div>
 
          
        <div class="col-lg-12 grid-margin stretch-card">
        
                <div class="card">
                  <div class="card-body">
                    <h4 class="card-title">Expense Report</h4>
                   
                    <Bar data={{
    labels: ["Jan", "Feb", "Mar", "Apl", "May", "Jun","Jul","Aug","Sep","Oct","Nov","Dec"],
    datasets: [{
      label: 'Total Amount Per Month',
      data: resultAmount,
      backgroundColor: [
        'rgba(255, 99, 132, 0.2)',
        'rgba(54, 162, 235, 0.2)',
        'rgba(255, 206, 86, 0.2)',
        'rgba(75, 192, 192, 0.2)',
        'rgba(153, 102, 255, 0.2)',
        'rgba(255, 159, 64, 0.2)'
      ],
      borderColor: [
        'rgba(255,99,132,1)',
        'rgba(54, 162, 235, 1)',
        'rgba(255, 206, 86, 1)',
        'rgba(75, 192, 192, 1)',
        'rgba(153, 102, 255, 1)',
        'rgba(255, 159, 64, 1)'
      ],
      borderWidth: 1
    }]
  }} />
                  
                  </div>
                </div>
              </div>
              
              </div>
        </>
    )}