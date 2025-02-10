import axios from 'axios';

const EMPLOYEE_BASE_REST_API_URL = 'https://cbc8-2405-201-c007-41dd-3c11-fa3d-b1a5-7950.ngrok-free.app/expense';
// const EMPLOYEE_BASE_REST_API_URL = 'http://localhost:8888/expense';


export const authenticateUser = async (user) => {
  return axios.post(EMPLOYEE_BASE_REST_API_URL+'/authenticate', user)
    .then((response) =>{
        console.log(response)
        if(response){
            localStorage.setItem("user",JSON.stringify(response.data));
        }
    
    console.log(response);
    return response;
    });
};