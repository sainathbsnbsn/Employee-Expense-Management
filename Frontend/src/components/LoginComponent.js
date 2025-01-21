import React, {createContext, useState} from 'react'
import { useNavigate } from 'react-router-dom'
import { authenticateUser } from '../services/LoginService'
import { Home } from './home'
import { Profile } from './profile'
import logo from '../images/logo.png'
import image_bg from '../images/image_bg1.png'

const LoginComponent=()=>{
    const [username,setUsername]=useState('')
    const [password,setPassword]=useState('')
    const [empId,setEmpId] = useState('')

    const bgStyle={
      backgroundImage:`url(${image_bg})`,
      backgroundSize:'cover',
      backgroundRepeat:'no-repeat',
      height:'100vh'

    };

    const [isError,setIsError] = useState(false)

    const MyContext = createContext()
    const navigate = useNavigate();

    const authenticate = (e) =>{
        e.preventDefault();
        const user={username,password}
        authenticateUser(user).then(()=>{
            const user1 = JSON.parse(localStorage.getItem('user'));
            console.log(localStorage.getItem('user'))
            console.log(user1.role)
            if(user1.role==="Manager")
            navigate('/');
            else{
                console.log("iam in else")
           navigate('/')
           
            }
            setIsError(false)
            window.location.reload(true)

        }).catch(error =>{
            setIsError(true)
            console.log(error)
        })
    }
    return (
       
          
           <div class="container-scroller" style={bgStyle}>
      <div class="container-fluid page-body-wrapper full-page-wrapper" style={bgStyle}>
        <div class="content-wrapper d-flex align-items-center auth" style={bgStyle}>
          <div class="row flex-grow">
            <div class="col-lg-4 mx-auto">
              <div class="auth-form-light text-left p-5">
                <div class="brand-logo">
                  <img  src={logo} style={{width:'100px'}}/>
                </div>
                <h4>Wellcome to EMS Portal</h4>
                <h6 class="font-weight-light">Sign in to continue.</h6>
                {(isError==true)?(<small className='text-danger'>UserID or Password is Incorrect <button className='border-none' onClick={()=>setIsError(false)}>x</button></small>):<></>}
                <form class="pt-3">
                  <div class="form-group">
                  <input
                                        type = "text"
                                        placeholder = "Enter username"
                                        name = "username"
                                        className = "form-control form-control-lg"
                                        value = {username}
                                        onChange = {(e) => setUsername(e.target.value)}
                                    >
                                    </input>
                  </div>
                  <div class="form-group">
                  <input
                                        type = "password"
                                        placeholder = "Enter password"
                                        name = "password"
                                        className = "form-control"
                                        value = {password}
                                        onChange = {(e) => setPassword(e.target.value)}
                                    >
                                    </input>
                  </div>
                  <div class="mt-3">
                    
                  <button className = "btn btn-primary" onClick = {(e) => authenticate(e)} >Submit </button>
                  </div>
                </form>
              </div>
            </div>
          </div>
        </div>
        </div>
        </div>
        
      
    
   
           
    )
}

export default LoginComponent;