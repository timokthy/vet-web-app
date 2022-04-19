import '../../styling/Main.css';
import Sidebar from '../../components/Sidebar';
import UserNavbar from '../../components/UserNavbar';
import { useState } from 'react';
import Form from "react-bootstrap/Form";
import axios from 'axios';

function AddUser() {
    const [userInfo, setUserInfo] = useState({});

    // Request to add a user to the database
    function handleSubmit(event) {
        event.preventDefault();

        axios.post("http://localhost:8080/app/user/add-user", userInfo)
        .then(res => {
          console.log(res);
          console.log(res.data);
        })
        .catch(err => console.log(err));

        alert("User added")
        window.location.reload()
    }

    // Form to add a new user
    return (
        <div className="d-flex w-100 h-100">
            <div className="sidebar">
                <Sidebar />
            </div>

            <div className="placeholder">
                <Sidebar />
            </div>

            <div className="main-container d-flex flex-column flex-grow-1">
                <UserNavbar />

                <h1 className="mt-5 ms-5">Add User</h1>
                        <form className="d-flex flex-column  align-items-start mt-5" onSubmit={handleSubmit}>


                            <div className="d-flex my-3 w-50">
                                <h5 className="w-100">First Name:</h5>
                                <input className="form-control" type="text" value={userInfo.firstName} onChange={e => setUserInfo({...userInfo, firstName: e.target.value})}/>
                            </div>

                            <div className="d-flex my-3 w-50">
                                <h5 className="w-100">Last Name:</h5>
                                <input className="form-control" type="text" value={userInfo.lastName} onChange={e => setUserInfo({...userInfo, lastName: e.target.value})}/>
                            </div>

                            <div className="d-flex my-3 w-50">
                                <h5 className="w-100">Position:</h5>
                                <select class="form-select ms-2" value={userInfo.userType} onChange={e => setUserInfo({...userInfo, userType: e.target.value})}>
                                    <option selected>Select an option...</option>
                                    <option value="Admin">Admin</option>
                                    <option value="Animal Care Attendant">Animal Care Attendant</option>
                                    <option value="Animal Health Technician">Animal Health Technician</option>
                                    <option value="Student">Student</option>
                                    <option value="Teaching Technician">Teaching Technician</option>
                                </select>
                            </div>

                            <div className="d-flex my-3 w-50">
                                <h5 className="w-100">Username:</h5>
                                <input className="form-control" type="text" value={userInfo.username} onChange={e => setUserInfo({...userInfo, username: e.target.value})}/>
                            </div>

                            <div className="d-flex my-3 w-50">
                                <h5 className="w-100">Password:</h5>
                                <input className="form-control" type="password" value={userInfo.password} onChange={e => setUserInfo({...userInfo, password: e.target.value})}/>
                            </div>

                            <div className="d-flex my-3 w-50">
                                <h5 className="w-100">Email:</h5>
                                <input className="form-control" type="email" value={userInfo.email} onChange={e => setUserInfo({...userInfo, email: e.target.value})}/>
                            </div>

                            <div className="d-flex my-3 w-50">
                                <h5 className="w-100">Phone Number:</h5>
                                <input className="form-control" type="text" value={userInfo.phoneNum} onChange={e => setUserInfo({...userInfo, phoneNum: e.target.value})}/>
                            </div>

                            <div className="d-flex my-3 w-50">
                                <h5 className="w-100">Start Date:</h5>
                                <Form.Control type="date" value={userInfo.startDate} onChange={e => setUserInfo({...userInfo, startDate: e.target.value})}></Form.Control>
                            </div>

                            <div className="d-flex w-50">
                                <div className="w-50 mx-5"></div>
                                <button className="btn btn-secondary px-4 py-2 mx-5 my-4" type="submit">Submit</button>
                            </div>
                        </form>
            </div>
        </div>  
    );
}

export default AddUser;