import { useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from 'axios';
import '../../styling/Login.css';
import ucvmLogo from "../../images/ucvm-logo-lg.png";


const Login = () => {
    // Hooks to get the username and password state
    const [usernameEntered, setUsername] = useState("");
    const [passwordEntered, setPassword] = useState("");
    window.localStorage.clear();
    
    let navigate = useNavigate();

    // Sends request to log in and authenticate the user
    function handleSubmit(event) {
        event.preventDefault();

        //get the jwt token and save it in local storage
        axios.get('http://localhost:8080/app/authenticate/', {params: { 
            userName: usernameEntered,
            password: passwordEntered
            }}).then(
                res=>{ try {
                        console.log(res.data)
                        localStorage.setItem("token", res.data)
                        window.location.reload()
                    } catch{
                        window.location.reload()
                    }
                }
            )

        //include the token in the header of all axios requests to the api
        axios.interceptors.request.use(
                config =>{
                    config.headers.authorization = `Bearer ${localStorage.getItem("token")}`
                    return config;
                }
            )



        axios.get('http://localhost:8080/app/login/', {params: { 
            userName: usernameEntered,
            password: passwordEntered
            }}).then(
              res => {
                  console.log(res.data);
                let user = res.data

                if (user.firstName === ""){ //if null
                    alert("Invalid username and/or password");
                } else{
                    localStorage.setItem("userID", user.id)
                    localStorage.setItem("userFirstName", user.firstName)
                    localStorage.setItem("userLastName", user.lastName)
                    localStorage.setItem("email", user.email)
                    localStorage.setItem("phoneNum", user.phoneNum)
                    navigate(`/home`)
                    window.location.reload()
                }
            })
    }

    // Displays the login page to the user
    return (
        <div className="d-flex flex-column vertical-center border border-4 border-dark bg-light shadow">

            <img src={ucvmLogo} alt="U of C Vet Med Logo" className="m-5"></img>

            <form className="mx-4" onSubmit={handleSubmit}>
                <h1 className="mb-4">Login</h1>
                <input type="username" className="form-control mb-2" placeholder="Username" value={usernameEntered} onChange={e => setUsername(e.target.value)} />
                <input type="password" className="form-control mb-4" placeholder="Password" value={passwordEntered} onChange={e => setPassword(e.target.value)}/>
                <button className="btn btn-secondary mb-4" type="submit">Log In</button>
            </form>
        </div>
    )
}

export default Login
