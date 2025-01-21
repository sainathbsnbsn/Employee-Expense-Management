import axios from 'axios';
import authHeader from "./auth-header";

const HTTP_REST_API_LINK = "http://localhost:8888/expense/employee"

const user = JSON.parse(localStorage.getItem('user'))

export const addCategory = async (category) => {
    const response = await axios.post(HTTP_REST_API_LINK+"/add",category)
    return response
} 

// const axiosInstance = axios.create({
//     baseURL:'http://localhost:8888/expense/employee',
//     headers:{
//         'Authorization':`Bearer ${user.token}`,
//         'Content-Type':'application/json'
//     }
// })

export const getEmployeeById = async (id) => {
    const response = await axios.get(HTTP_REST_API_LINK+"/get/"+id,{headers:authHeader()})
    return response
}

export const getManagerByEmpId = async (id) => {
    const response = await axios.get(HTTP_REST_API_LINK+"/getManagerByempId/"+id)
    return response
}