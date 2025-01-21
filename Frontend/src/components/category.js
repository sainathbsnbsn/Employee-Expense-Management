import { mdiController } from '@mdi/js';
import React, { useEffect, useState } from 'react';
import { getAllCategory, getCategoryById, updateCategory } from '../services/categoryService';
import { PageHeader } from './pageHeader';

export const Category = () =>{

    const [status,setStatus] = useState("active")
    const [category,setCategory] = useState([])
    const [name,setName] = useState("")
    const [limit,setLimit] = useState(0)
   

    useEffect(()=>{
       getAll()
    },[status])

    const getAll = ()=>{
        getAllCategory().then((response)=>{
            setCategory(response.data)
        }).catch((error)=>{
            console.log(error)
        })
    }

    const handleStatus=(item)=>{
       

        const id = item
        var cat = {id,name,limit,status}
        getCategoryById(id).then(response=>{
               
            if(response.data.status=='active'){
                
                cat={id,name:response.data.name,limit:response.data.limit,status:'inactive'}
             }
             else{
                 cat={id,name:response.data.name,limit:response.data.limit,status:'active'}
             }
             updateCategory(id,cat).then((response)=>{
                console.log(response.data)
                getAll()  
             }).catch((error)=>{
                 console.log(error)
             })
        }).catch(error=>{
            console.log(error)
        })
         
    }
    

    return (
        <>
        <PageHeader icon={mdiController} pageName='Category Controller' />
        <div class="col-12 grid-margin">
                <div className="card">
                  <div className="card-body">
        
         <table className="table table-hover">
                      <thead>
                        <tr>
                          <th>CategoryId</th>
                          <th>Category Name</th>
                          <th>Limit</th>
                          <th classNameName='text-start'>Controls</th>
                          
                        </tr>
                      </thead>
                      <tbody>
                      {category.map((item)=>
                        (
                          
                        <tr>
                          <td>{item.id}</td>
                          <td >{item.name}</td>
                          <td >{item.limit} </td>
                          <td>
                            <button className='btn btn-primary btn-sm' onClick={()=>handleStatus(item.id)}>{item.status}</button>
                          </td>
                         </tr>
                          ))}
                      </tbody>
                    </table>

        </div>

        </div>
        </div>
        </>
    )
}