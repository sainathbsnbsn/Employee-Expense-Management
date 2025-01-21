import React,{useEffect, useState} from 'react';
import Icon from '@mdi/react';
import { mdiBellOutline } from '@mdi/js';
import 'bootstrap/dist/css/bootstrap.min.css';
import demo_card from '../images/demo_card.jpg';
import { ArcElement, Chart as ChartJS} from "chart.js/auto";
import { Bar,Doughnut,Line,Pie, PolarArea } from "react-chartjs-2";
import { getAmountByStatusByEmpId, getApprovedOfLastFiveYearsByEmpId } from '../services/expenseService';

export const EmployeeReport = () =>{

const [dataPie,setDataPie] = useState([])

const [dataBar,setDataBar] = useState([])

const user = JSON.parse(localStorage.getItem('user'))

const [pending,setPending] = useState(0)
const [approved,setApproved] = useState(0)
const [rejected,setRejected] = useState(0)

useEffect(()=>{
    getDataPie()
    getDataBar();
},[])

const getDataBar = ()=>{
    getApprovedOfLastFiveYearsByEmpId(user.username).then((response)=>{
        setDataBar(response.data)
        
    }).catch((error)=>{
        console.log(error)
    })
}

const getDataPie = () =>{

    getAmountByStatusByEmpId('approved',user.username).then((response)=>{
       setApproved(response.data)
       
    }).catch((error)=>{
        console.log(error)
    })
    getAmountByStatusByEmpId('Pending',user.username).then((response)=>{
        setPending(response.data)
    }).catch((error)=>{
        console.log(error)
    })
    getAmountByStatusByEmpId('rejected',user.username).then((response)=>{
        setRejected(response.data)
    }).catch((error)=>{
        console.log(error)
    })
   
}

    return (
        <>
        <div class="row">
              <div class="col-lg-7 grid-margin stretch-card">
                <div class="card">
                  <div class="card-body">
                    <h4 class="card-title">Approved Expenses</h4>
                    <Bar data={{
                        labels: ["2019", "2020", "2021", "2022", "2023"],
                        datasets: [{
                          label: 'Year wise Approved Expenses',
                          data: dataBar.reverse(),
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
                          borderWidth: 1,
                          fill: true, // 3: no fill
                        }]
                    }} options={{
                        plugins: {
                            filler: {
                              propagate: true
                            }
                          }
                    }}/>
                  </div>
                </div>
              </div>
              <div class="col-lg-5 grid-margin stretch-card">
                <div class="card">
                  <div class="card-body">
                    <h4 class="card-title">Employee Expense</h4>
                    <Doughnut style={{height:'250px'}} data={{
                            datasets: [{
                              data: [rejected,approved,pending],
                              backgroundColor: [
                                'rgba(255, 99, 132, 0.5)',
                                'rgba(54, 162, 235, 0.5)',
                                'rgba(255, 206, 86, 0.5)',
                                'rgba(75, 192, 192, 0.5)',
                                'rgba(153, 102, 255, 0.5)',
                                'rgba(255, 159, 64, 0.5)'
                              ],
                              borderColor: [
                                'rgba(255,99,132,1)',
                                'rgba(54, 162, 235, 1)',
                                'rgba(255, 206, 86, 1)',
                                'rgba(75, 192, 192, 1)',
                                'rgba(153, 102, 255, 1)',
                                'rgba(255, 159, 64, 1)'
                              ],
                            }],
                        
                            // These labels appear in the legend and in the tooltips when hovering different arcs
                            labels: [
                              'Rejected',
                              'Approved',
                              'Pending',
                            ]
                         
                    }} options={{
                        responsive: true,
                            animation: {
                            animateScale: true,
                            animateRotate: true
                            }
                    }}/>
                  </div>
                </div>
              </div>
            </div>
        </>
    )
}