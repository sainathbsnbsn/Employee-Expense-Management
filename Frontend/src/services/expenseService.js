import axios from 'axios';
import authHeader from "./auth-header";


const HTTP_REST_API_LINK = "https://cbc8-2405-201-c007-41dd-3c11-fa3d-b1a5-7950.ngrok-free.app/expense/report"
// const HTTP_REST_API_LINK = "http://localhost:8888/expense/report"

const user = JSON.parse(localStorage.getItem('user'))

export const addExpense = async (expense) => {
    const response = await axios.post(HTTP_REST_API_LINK+"/add",expense,{headers:authHeader()})
    return response
} 

export const addExpenseReceipt = async (id,file) => {
    const response = await axios.put(HTTP_REST_API_LINK+"/addexpensereceipt/"+id,file,{headers:authHeader()})
    return response
} 

export const getExpenseReceiptById=async(id)=>{
     
       const response = await axios('http://localhost:8888/expense/report/getreceipt/'+id, {
    
    
        method: 'GET',
        responseType: 'blob' 
    })
    return response;
}
    
    
     
export const getExpenseById = async (id) => {
    const response = await axios.get(HTTP_REST_API_LINK+"/get/"+id,{headers:authHeader()})
    return response
}

export const getExpenseByIdByEmpId = async (id,empId) => {
    const response = await axios.get(HTTP_REST_API_LINK+"/get/"+id+"/"+empId,{headers:authHeader()})
    return response
}


export const getAllExpenses = async () => {
    const response = await axios.get(HTTP_REST_API_LINK+"/allexpenses",{headers:authHeader()})
    return response
}

export const deleteExpenseById = async (id) => {
    const response = await axios.delete(HTTP_REST_API_LINK+"/delete/"+id,{headers:authHeader()})
    return response
}

export const updateExpense = async (id,expense) =>{
    const response = await axios.put(HTTP_REST_API_LINK+"/update/"+id,expense,{headers:authHeader()})
    return response
}

export const approveExpense = async (id) =>{
    const response = await axios.put(HTTP_REST_API_LINK+"/accept/"+id,{headers:authHeader()})
    return response
}

export const getAllExpensesByEmpId = async (id) =>{
    const response = await axios.put(HTTP_REST_API_LINK+"/getExpenses/"+id,{headers:authHeader()})
    return response
}

export const getAllExpensesByStatus = async (status) =>{
    const response = await axios.get(HTTP_REST_API_LINK+"/getExpensesByStatus/"+status,{headers:authHeader()})
    return response
}

export const getAllExpensesByStatusByEmpId = async (status,id) =>{
    const response = await axios.get(HTTP_REST_API_LINK+"/getExpensesByStatusByEmpId/"+status+"/"+id,{headers:authHeader()})
    return response
}

export const getAmountByCategoryByStatusMonthWise = async (year,payload) =>{
    const response = await axios.post(HTTP_REST_API_LINK+"/getAmountByCategoryByStatusMonthWise/"+year,payload,{headers:authHeader()})
    return response
}

export const getAmountByStatusByEmpId = async (status,id) =>{
    const response = await axios.get(HTTP_REST_API_LINK+"/getAmountByStatusByEmpId/"+status+"/"+id,{headers:authHeader()})
    return response
}

export const getAmountBystatusByEmpIdPresentYear= async (status,id) =>{
    const response = await axios.get(HTTP_REST_API_LINK+"/getAmountBystatusByAssIdPresentYear/"+status+"/"+id,{headers:authHeader()})
    return response
}

export const getAmountByStatus= async (status) =>{
    const response = await axios.get(HTTP_REST_API_LINK+"/getAmountByStatus/"+status,{headers:authHeader()})
    return response
}

export const getApprovedOfLastFiveYearsByEmpId= async (id) =>{
    const response = await axios.get(HTTP_REST_API_LINK+"/getAmountByStatusLastFiveYearWise/"+id,{headers:authHeader()})
    return response
}

export const getExpensesByStatusPagination= async (status,no) =>{
    const response = await axios.get(HTTP_REST_API_LINK+"/getExpensesByCategoryIdPagenation/"+status+"/"+no+"/5",{headers:authHeader()})
    return response
}

export const getExpensesByStatusByCategory= async (status,id) =>{
    const response = await axios.get(HTTP_REST_API_LINK+"/bystatusbycategoryId/"+status+"/"+id,{headers:authHeader()})
    return response
}

export const getExpensesByStatusByCategoryByEmpId= async (status,id,empId) =>{
    const response = await axios.get(HTTP_REST_API_LINK+"/getExpenseStatusCategoryEmpId/"+status+"/"+id+"/"+empId,{headers:authHeader()})
    return response
}


