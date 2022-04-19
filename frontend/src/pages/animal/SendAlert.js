import 'bootstrap/dist/css/bootstrap.min.css';
import Sidebar from '../../components/Sidebar';
import AnimalNavbar from '../../components/AnimalNavbar';

// Requires npm install axios --save
import axios from 'axios';
import React, {useState, useEffect} from 'react'
import {useNavigate} from 'react-router-dom'

function SendAlert() {

    const [message, setMessage] = useState(null);
    const [staffList, setStaffList] = useState([]);
    const [selectedStaff, setSelectedStaff] = useState([]);

    const animalID = localStorage.getItem("animalID")
    const animalName = localStorage.getItem("animalName")
    const animalSpecies = localStorage.getItem("animalSpecies")
    const currUserID = localStorage.getItem("userID")

    let navigate = useNavigate();

    // Gets a list of Animal Health Technicians to alert
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

    // Sends an email alert to all selected staff members
    function sendRequest(event){
        event.preventDefault();

        let formdata = new FormData()
        formdata.append('selectedStaff', selectedStaff)
        formdata.append('message',message)
        formdata.append('subjectType', "Alert for Animal #"+animalID+" "+animalName+", "+animalSpecies)
        formdata.append("currUserID", currUserID)

        axios({
            method: "post",
            url: "http://localhost:8080/app/user/send-email/", 
            data: formdata,
            headers: { "Content-Type": "multipart/form-data" },
          })

        navigate(`/animal-info`)
        window.location.reload()
      }

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
            <h1 className="ms-5 mt-5 mb-5">Send Alert</h1>

            <div className="d-flex mx-5">

                        <div className="align-items-left">
                            <h6>Please select all health technicians you would like to alert:</h6>
                            <div className="align-items-left mx-1 mt-3"></div>
                            <select class="form-select" size='5' multiple aria-label="staffSelection" onChange={handleChange}> 
                                {staffList.map(staff =>   
                                    <option value = {staff.id} >{staff.firstName} {staff.lastName}</option>
                                )}      
                            </select> 
                        </div>
                    </div> 

                    <div class="custom-field mt-5 mb-3 mx-5">
                        <label> Message For Alert: </label> <br/>
                        <textarea className="form-control w-50" id="messageInput" onChange={getMessage} cols='100' rows='5' 
                        placeholder="Please enter the message you would like to send with this alert"> </textarea>
                    </div>
                    <div class="button mx-5">
                        <button className="btn btn-secondary mt-3" onClick={clickButton}>Submit</button>
                    </div>
                </div>
            </div>
        </div>
        
    );
}

export default SendAlert;