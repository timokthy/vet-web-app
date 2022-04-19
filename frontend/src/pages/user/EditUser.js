import '../../styling/Main.css';
import Sidebar from '../../components/Sidebar';
import UserNavbar from '../../components/UserNavbar';
import { useState } from 'react';
import axios from 'axios';

function EditUser() {
    const [userId, setUserId] = useState();
    const [firstName, setFirstName] = useState();
    const [lastName, setLastName] = useState();
    const [position, setPosition] = useState();
    const [username, setUsername] = useState();
    const [password, setPassword] = useState();
    const [email, setEmail] = useState();
    const [phoneNum, setPhoneNum] = useState();
    const [activeStatus, setActiveStatus] = useState();
    const [startDate, setStartDate] = useState();

    // Sends a request to search for a user in the database
    function handleSearch(event) {
        event.preventDefault();
        axios.get("http://localhost:8080/app/user/edit-user/" + userId)
        .then(res => {
            const info = res.data;

            if(info === "") {
                alert("User ID does not exist");
            } else {
                setFirstName(info.firstName);
                setLastName(info.lastName);
                setPosition(info.userType);
                setUsername(info.username);
                setPassword(info.password);
                setEmail(info.email);
                setPhoneNum(info.phoneNum);
                setActiveStatus(info.activeStatus)
                setStartDate(info.startDate);
            }
        })
        .catch(err => console.log(err));
    }

    // Sends a request to update a user in the database
    function handleSubmit(event) {
        event.preventDefault();
        
        axios.put("http://localhost:8080/app/user/edit-user/" + userId, { id: userId, firstName: firstName, lastName: lastName,
                                                                          username: username, password: password, 
                                                                          userType: position, email: email, phoneNum: phoneNum, 
                                                                          startDate: startDate, activeStatus: activeStatus})
        .then(res => {
          console.log(res);
          console.log(res.data);
        })
        .catch(err => console.log(err));

        alert("User updated")
        window.location.reload()
    }

    // Form to update a user
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

                <h1 className="mt-5 ms-5">Edit User</h1>

                    <form className="d-flex flex-column align-items-start mt-5" onSubmit={handleSearch}>
                        <div className="d-flex my-3 w-50">
                            <h5 className="w-100">User ID:</h5>
                            <input className="form-control" value={userId} onChange={e => setUserId(e.target.value)}/>
                        </div>
                        
                        <div className="d-flex w-50">
                                <div className="w-50 mx-5"></div>
                                <button className="btn btn-secondary px-3 py-2 mx-5" type="submit">Search</button>
                        </div>
                    </form>



                    <form className="d-flex flex-column  align-items-start mt-5" onSubmit={handleSubmit}>
                        <div className="d-flex my-3 w-50">
                            <h5 className="w-100">First Name:</h5>
                            <input className="form-control" value={firstName} onChange={e => setFirstName(e.target.value)}/>
                        </div>

                        <div className="d-flex my-3 w-50">
                            <h5 className="w-100">Last Name:</h5>
                            <input className="form-control" value={lastName} onChange={e => setLastName(e.target.value)}/>
                        </div>

                        <div className="d-flex my-3 w-50">
                                <h5 className="w-100">Position:</h5>
                                <select class="form-select ms-2" value={position} onChange={e => setPosition(e.target.value)}>
                                    <option selected value="">Select an option...</option>
                                    <option value="Admin">Admin</option>
                                    <option value="Animal Care Attendant">Animal Care Attendant</option>
                                    <option value="Animal Health Technician">Animal Health Technician</option>
                                    <option value="Student">Student</option>
                                    <option value="Teaching Technician">Teaching Technician</option>
                                </select>
                        </div>

                        <div className="d-flex my-3 w-50">
                            <h5 className="w-100">Username:</h5>
                            <input className="form-control" value={username} onChange={e => setUsername(e.target.value)}/>
                        </div>

                        <div className="d-flex my-3 w-50">
                            <h5 className="w-100">Password:</h5>
                            <input type="password" className="form-control" value={password} onChange={e => setPassword(e.target.value)}/>
                        </div>

                        <div className="d-flex my-3 w-50">
                            <h5 className="w-100">Email:</h5>
                            <input type="email" className="form-control" value={email} onChange={e => setEmail(e.target.value)}/>
                        </div>

                        <div className="d-flex my-3 w-50">
                            <h5 className="w-100">Phone Number:</h5>
                            <input className="form-control" value={phoneNum} onChange={e => setPhoneNum(e.target.value)}/>
                        </div>

                        <div className="d-flex my-3 w-50">
                            <h5 className="w-100">Status:</h5>
                            <select class="form-select ms-2" value={activeStatus} onChange={e => setActiveStatus(e.target.value)}>
                                <option selected value="">Select an option...</option>
                                <option value="true">Active</option>
                                <option value="false">Blocked</option>
                            </select>
                        </div>

                        <div className="d-flex my-3 w-50">
                                <div className="w-50 mx-5"></div>
                                <button className="btn btn-secondary px-4 py-2 mx-5" type="submit">Submit</button>
                        </div>

                    </form>


            </div>
        
        </div>  
    );
}

export default EditUser;