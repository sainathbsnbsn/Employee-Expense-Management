import axios from 'axios';
import authHeader from './auth-header';

const HTTP_REST_API_LINK = 'https://cbc8-2405-201-c007-41dd-3c11-fa3d-b1a5-7950.ngrok-free.app/category';
// const HTTP_REST_API_LINK = "http://localhost:8888/category"

export const addCategory = async (category) => {
    const response = await axios.post(HTTP_REST_API_LINK+"/add",category,{headers:authHeader()})
    return response
} 

export const getCategoryById = async (id) => {
    const response = await axios.get(HTTP_REST_API_LINK+"/get/"+id,{headers:authHeader()})
    return response
}

export const getAllCategory = async () => {
    const response = await axios.get(HTTP_REST_API_LINK+"/getall",{headers:authHeader()})
    return response
}

export const updateCategory = async (id,category) => {
    const response = await axios.put(HTTP_REST_API_LINK+"/update/"+id,category,{headers:authHeader()})
    return response
}

export const deleteCategory = async (id) => {
    const response = await axios.get(HTTP_REST_API_LINK+"/delete/"+id,{headers:authHeader()})
    return response
}

export const getCategoryByStatus = async (status) => {
    const response = await axios.get(HTTP_REST_API_LINK+"/status/"+status,{headers:authHeader()})
    return response
}