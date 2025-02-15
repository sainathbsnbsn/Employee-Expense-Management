import axios from 'axios';

const EMPLOYEE_BASE_REST_API_URL = 'https://employee-expense-management-jwt-production.up.railway.app/expense';
// const EMPLOYEE_BASE_REST_API_URL = 'http://localhost:8888/expense';


export const authenticateUser = async (user) => {
  return axios.post(EMPLOYEE_BASE_REST_API_URL+'/authenticate', user)
    .then((response) =>{
        console.log(response)
        if(response){
            localStorage.setItem("user",JSON.stringify(response.data));
            localStorage.setItem("notNew",JSON.stringify(response.data));
        }
    
    console.log(response);
    return response;
    });
};