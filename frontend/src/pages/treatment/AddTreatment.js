import 'bootstrap/dist/css/bootstrap.min.css';
import Sidebar from '../../components/Sidebar';
import AnimalNavbar from '../../components/AnimalNavbar';

// Requires npm install axios --save
import axios from 'axios';
import React, {useState} from 'react'
import {useNavigate} from 'react-router-dom';
import jwt_decode from "jwt-decode";

function AddTreatment() {

    const [treatment, setTreatment] = useState(null);
    const [description, setDescription] = useState(null);
    const treatmentStatus = "Ongoing";
    
    const animalID = localStorage.getItem("animalID")
    const userID = localStorage.getItem("userID")

    let token = localStorage.getItem("token")
    let decoded = jwt_decode(token)
    const [activeUserType, setType] = useState(decoded.sub);
    let allowView=false;
    // Only allow Animal Health Technicians to view this page
    if (activeUserType === "Animal Health Technician"){allowView = true}

    let navigate = useNavigate();

    function getTreatment(treatment){
        setTreatment(treatment.target.value)
    }

    function getDescription(treatmentDescription){
        setDescription(treatmentDescription.target.value)
    }

    function clickButton(event){
        event.preventDefault();
        document.getElementById("treatmentInput").value = ""
        document.getElementById("descriptionInput").value = ""
        console.log("From Clicking the button: " + treatment)
        sendRequest(event)
    }

    function sendRequest(event){

        event.preventDefault();
        var rightNow = new Date();
        var formattedDay = rightNow.getDate() < 10 ? "0" + rightNow.getDate().toString() : rightNow.getDate()
        var formattedMonth = (rightNow.getMonth()+1) < 10 ? "0" + (rightNow.getMonth()+1).toString() : (rightNow.getMonth()+1)
        var treatmentDate = rightNow.getFullYear() + "-" + formattedMonth +"-" + formattedDay + " 00:00:00"

        axios.post('http://localhost:8080/app/treatment/protocol/animalID=' + animalID, {
            treatmentID: null, //dummy, backend assigns a new treamentID
            treatmentDate: treatmentDate,
            treatment: treatment,
            description: description,
            treatmentStatus: treatmentStatus,
            animalID: parseInt(animalID),
            userID: parseInt(userID)
        }).then(
          res => {
              console.log(res);
          }
        )
        // navigate to health records when done
        navigate(`/health-records`)
        window.location.reload()
      }


  return (
      

    <div className="d-flex w-100 h-100">
        

    <div className="main-container d-flex flex-column flex-grow-1">
    <div className="d-flex w-100 h-100">
        <div className="sidebar">
            <Sidebar />
        </div>
        <div className="placeholder">
            <Sidebar />
        </div>
        <div className= "d-flex flex-column w-100">
            <AnimalNavbar />
            {/* Only allow Animal Health Technicians to view this page */}
            { allowView ? 
            <div>
                <h1 className="ms-5 mt-5">Add Treatment</h1>

                <div class="custom-field mt-4 mb-3 mx-5">
                    <label className="mb-2"> Treatment Protocol: </label> <br/>
                    <textarea className="form-control w-25" id="treatmentInput" onChange={getTreatment} cols='100' rows='1' placeholder="Please enter the treatment protocol.">
                    </textarea>
                </div>

                <div class="custom-field mt-4 mb-3 mx-5">
                    <label className="mb-2"> Description: </label> <br/>
                    <textarea className="form-control w-50" id="descriptionInput" onChange={getDescription} cols='100' rows='5' placeholder="Please enter the description of the treatment protocol.">
                    </textarea>
                </div>
                <div class="button mx-5">
                    <button class="mt-4 btn btn-secondary" onClick={clickButton}>Submit</button>
                </div>
            </div>: 
            <a href="/health-records">Only Animal Health Technicians may request treatments. Click to return to health records.</a>}
        </div>
        </div>

        </div>
    </div>    
    );
}

export default AddTreatment;