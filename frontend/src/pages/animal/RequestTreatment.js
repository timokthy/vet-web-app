import 'bootstrap/dist/css/bootstrap.min.css';
import Sidebar from '../../components/Sidebar';
import AnimalNavbar from '../../components/AnimalNavbar';

// Requires npm install axios --save
import axios from 'axios';
import React, {useState, useEffect} from 'react'
import {useNavigate} from 'react-router-dom'

import jwt_decode from "jwt-decode";

function RequestTreatment() {

    const [message, setMessage] = useState();
    const [staffList, setStaffList] = useState([]);
    const [selectedStaff, setSelectedStaff] = useState([]);
    
    const animalID = localStorage.getItem("animalID")
    const animalName = localStorage.getItem("animalName")
    const animalSpecies = localStorage.getItem("animalSpecies")
    const currUserID = localStorage.getItem("userID")

    let token = localStorage.getItem("token")
    let decoded = jwt_decode(token)
    const [activeUserType, setType] = useState(decoded.sub);
    let allowView=false;
    // Only Animal Care Attendants can request treatments
    if (activeUserType === "Animal Care Attendant"){allowView = true}

    let navigate = useNavigate();

    // Get a list of all Animal Health Technicians from the database
    function GetStaffList(){
        useEffect(()=>{
            axios.get('http://localhost:8080/app/user/userType=Animal%20Health%20Technician').then(
                res => {
                    setStaffList(res.data)
                })
        },[])
    }
        
    function getMessage(message){
        setMessage(message.target.value)
    }

    function clickButton(event){
        event.preventDefault();
        document.getElementById("messageInput").value = ""
        console.log(selectedStaff.map( (staff) => staff ) + " " + message)
        sendRequest(event)
    }

    function sendRequest(event){
        event.preventDefault();

        let formdata = new FormData()
        formdata.append('selectedStaff', selectedStaff)
        formdata.append('message',message)
        formdata.append('subjectType', "Request for Treatment for Animal #"+animalID+" "+animalName+", "+animalSpecies)
        formdata.append("currUserID", currUserID)

        axios({
            method: "post",
            url: "http://localhost:8080/app/user/send-email/", 
            data: formdata,
            headers: { "Content-Type": "multipart/form-data" },
          })


        navigate(`/health-records`)
        window.location.reload()
      }

    // Get the selected Animal Health Technicians from the list
    function handleChange(e){
        let value = Array.from(e.target.selectedOptions, option => option.value);
        setSelectedStaff(value);
        console.log(selectedStaff)
    }


  return (
    
    <div className="main-container d-flex flex-column flex-grow-1"> 
        {GetStaffList()}
        <div className="d-flex w-100 h-100">
            <div className="sidebar">
                <Sidebar />
            </div>
            <div className="placeholder">
                <Sidebar />
            </div>
            
            <div className= "d-flex flex-column w-100">
                <AnimalNavbar />
                {/* Only allow Animal Care Attendants to view this page */}
                { allowView ? 
                <div>
                    <h1 className="ms-5 mt-5 mb-5">Request Treatment</h1>
                    <div className="d-flex align-items-left">
                        <div className="mx-5">
                            <h6>Please select all health technicians to send this request to:</h6>
                            <div className="align-items-left mx-1 mt-3">
                            <select class="form-select" size='5' multiple aria-label="staffSelection" onChange={handleChange}> 
                            {staffList.map(staff =>   
                                <option value = {staff.id} >{staff.firstName} {staff.lastName}</option>
                            )}      
                            </select>  
                            </div> 
                        </div>
                    </div>
                    <div class="custom-field mt-4 mx-5">
                        <label className="mb-2"> Message: </label> <br/>
                        <textarea className="form-control w-50" id="messageInput" onChange={getMessage} cols='100' rows='5' placeholder="Please enter a message">
                        </textarea>
                    </div>
                    <div className="mt-4 mx-5 button">
                        <button className="btn btn-secondary" onClick={clickButton}>Submit</button>
                    </div>
                </div>: <a href="/health-records">Only Animal Care Attendants may request treatments. Click to return to health records.</a>}

            </div>
        </div>
    </div>);
}

export default RequestTreatment;