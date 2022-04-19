import 'bootstrap/dist/css/bootstrap.min.css';
import Sidebar from '../../components/Sidebar';
import AnimalNavbar from '../../components/AnimalNavbar';

// Requires npm install axios --save
import axios from 'axios';
import React, {useState} from 'react'
import {useNavigate} from 'react-router-dom'
import jwt_decode from "jwt-decode";

function AddDiagnosis() {
    
    const [diagnosis, setDiagnosis] = useState(null);
    const [description, setDescription] = useState(null);
    const diagnosisStatus = "Ongoing";

    const animalID = localStorage.getItem("animalID")
    const userID = localStorage.getItem("userID")

    let token = localStorage.getItem("token")
    let decoded = jwt_decode(token)
    const [activeUserType, setType] = useState(decoded.sub);
    let allowView=false;
    // Only allow Animal Health Technicians to access this page
    if (activeUserType === "Animal Health Technician"){allowView = true}

    let navigate = useNavigate();

    function getDiagnosis(diagnosis){
        setDiagnosis(diagnosis.target.value)
    }

    function getDescription(diagnosisDescription){
        setDescription(diagnosisDescription.target.value)
    }

    function clickButton(event){
        event.preventDefault();
        document.getElementById("descriptionInput").value = ""
        document.getElementById("diagnosisInput").value = ""
        console.log("From Clicking the button: " + diagnosis + " " + description)
        sendRequest(event)
    }

    function sendRequest(event){

        event.preventDefault();
        var rightNow = new Date();
        var formattedDay = rightNow.getDate() < 10 ? "0" + rightNow.getDate().toString() : rightNow.getDate()
        var formattedMonth = (rightNow.getMonth()+1) < 10 ? "0" + (rightNow.getMonth()+1).toString() : (rightNow.getMonth()+1)
        var diagnosisDate = rightNow.getFullYear() + "-" + formattedMonth +"-" + formattedDay + " 00:00:00"


        axios.post('http://localhost:8080/app/treatment/diagnosis/animalID=' + animalID, {
            diagnosisID: null, //dummy, backend assigns a new diagnosisID
            diagnosisDate: diagnosisDate,
            diagnosis: diagnosis,
            description: description,
            diagnosisStatus: diagnosisStatus,
            animalID: parseInt(animalID),
            userID: parseInt(userID)
        }).then(
          res => {
              console.log(res);
          }
        )
        // navigate to health records page when done
        navigate(`/health-records`)
        window.location.reload()
      }


  return (
      
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
            {/* Only allow Animal Health Technicians to add diagnoses */}
            { allowView ? 
            <div>
                <h1 className="ms-5 mt-5">Add Diagnosis</h1>

                <div class="custom-field mt-4 mb-3 mx-5">
                    <label className="mb-2"> Diagnosis: </label> <br/>
                    <textarea className="form-control w-25" id="diagnosisInput" onChange={getDiagnosis} cols='100' rows='1' placeholder="Please enter the diagnosis.">
                    </textarea>
                </div>

                <div class="custom-field mt-4 mb-3 mx-5">
                    <label className="mb-2"> Description: </label> <br/>
                    <textarea className="form-control w-50" id="descriptionInput" onChange={getDescription} cols='100' rows='5' placeholder="Please enter the description of the diagnosis.">
                    </textarea>
                    <button className="mt-4 btn btn-secondary" onClick={clickButton}>Submit</button>
                </div>
            </div>: <a href="/health-records">Only Animal Health Technicians may add diagnoses. Click here to return to health records.</a>}
            </div>
            </div>
        </div> 
    );
}

export default AddDiagnosis;