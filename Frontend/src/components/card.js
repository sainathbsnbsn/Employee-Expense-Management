import React,{useEffect, useState} from 'react';
import Icon from '@mdi/react';
import circle from '../images/circle.png';
import { mdiChartLine, mdiLanPending } from '@mdi/js';
import { getAmountByStatus, getAmountByStatusByEmpId, getAmountBystatusByEmpIdPresentYear } from '../services/expenseService';
import { mdiCurrencyRupee } from '@mdi/js';
import { getEmployeeById } from '../services/employeeSecured';
export const Card = ({status,icon,bg})=>{

    const [amount,setAmount] = useState(0)
    const user = JSON.parse(localStorage.getItem('user'))

    
    const date = new Date();

    if(status=='Total'){
        if(user.role!='Manager'){
        getEmployeeById(user.username).then((response)=>{
            console.log(response.data.fundsReleased)
            setAmount(response.data.fundsReleased)
            console.log(amount)
        }).catch((error)=>{
            console.log(error)
        })
    }
    else{
        getEmployeeById(user.username).then((response)=>{
            console.log(response.data.fundsAllocated)
            setAmount(response.data.fundsAllocated)
            console.log(amount)
        }).catch((error)=>{
            console.log(error)
        })
    }
    }

    if(status=='Approved'){
        if(user.role!='Manager'){
        getAmountBystatusByEmpIdPresentYear('approved',user.username).then((response)=>{
            response.data === '' ? setAmount(0) : setAmount(response.data);
        }).catch((error)=>{
            console.log(error)
        })
    }
    else{
        getAmountByStatus('approved').then((response)=>{
            response.data === '' ? setAmount(0) : setAmount(response.data);
        }).catch((error)=>{
            console.log(error)
        })
    }
    }
    if(status=='Pending'){
        if(user.role!='Manager'){
        getAmountByStatusByEmpId('Pending',user.username).then((response)=>{
            setAmount(response.data)
        }).catch((error)=>{
            console.log(error)
        })
    }
    else{
        getAmountByStatus('Pending').then((response)=>{
            setAmount(response.data)
        }).catch((error)=>{
            console.log(error)
        })
    }
    }

    return (
        
        <>
         <div className="col-md-4 stretch-card grid-margin">
                <div className={bg}>
                  <div className="card-body">
                    <img src={circle} className="card-img-absolute" alt="circle-image" />
                    <h4 className="font-weight-normal mb-3">{status} Amount<i className="mdi mdi-chart-line mdi-24px float-right"></i>
                    <Icon path={icon } size={.8} className="text-secondary"/>
                    </h4>
                    <h2 className="mb-5"><Icon path={mdiCurrencyRupee} size={1.3} className="text-light mx-1"></Icon> {amount}</h2>
                    {(status=='Total')?(
                        <h6 className="card-text">From the date of you join</h6>
                    ):(
                        <h6 className="card-text">From the last {date.getMonth()+1} months</h6>
                    )}
                    
                  </div>
                </div>
              </div>
        </>
    )
}
