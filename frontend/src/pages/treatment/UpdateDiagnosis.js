import 'bootstrap/dist/css/bootstrap.min.css';
import Sidebar from '../../components/Sidebar';
import AnimalNavbar from '../../components/AnimalNavbar';

// Requires npm install axios --save
import axios from 'axios';
import React, {useState, useEffect} from 'react'
import {useLocation} from 'react-router-dom'
import {useNavigate} from 'react-router-dom'
import jwt_decode from "jwt-decode";

function ManageDiagnosis() {

    const urlParams = new URLSearchParams(useLocation().search)
    const diagnosisID = urlParams.get("diagnosisID")

    const [diagnosis, setDiagnosis] = useState(null)
    const [description, setDescription] = useState(null)
    
    const animalID = localStorage.getItem("animalID")
    const userID = localStorage.getItem("userID")
    const diagnosisStatus = "Ongoing"

    let token = localStorage.getItem("token")
    let decoded = jwt_decode(token)
    const [activeUserType, setType] = useState(decoded.sub);
    let allowView=false;
    // Only Animal Health Technicians are able to modify diagnoses
    if (activeUserType === "Animal Health Technician"){allowView = true}


    let navigate = useNavigate();

    // Get a specific diagnoses from the database
    function GetDiagnosisForAnimal(){
        useEffect(()=>{
            axios.get('http://localhost:8080/app/treatment/diagnosis/diagnosisID='+diagnosisID).then(
                res => {
                    var singleDiagnosis = res.data[0]
                    setDiagnosis(singleDiagnosis.diagnosis)
                    setDescription(singleDiagnosis.description)
                })
        },[])
    }

    function getDiagnosis(diagnosis){
        setDiagnosis(diagnosis.target.value)
    }

    function getDescription(diagnosisDescription){
        setDescription(diagnosisDescription.target.value)
    }

    function clickUpdateButton(event){
        event.preventDefault();
        document.getElementById("diagnosisInput").value = ""
        document.getElementById("descriptionInput").value = ""
        sendRequest(event)
    }

    function sendRequest(event){

        event.preventDefault();
        var rightNow = new Date();
        var formattedDay = rightNow.getDate() < 10 ? "0" + rightNow.getDate().toString() : rightNow.getDate()
        var formattedMonth = (rightNow.getMonth()+1) < 10 ? "0" + (rightNow.getMonth()+1).toString() : (rightNow.getMonth()+1)
        var diagnosisDate = rightNow.getFullYear() + "-" + formattedMonth +"-" + formattedDay + " 00:00:00"

        // Update the database with new information
        axios.put('http://localhost:8080/app/treatment/diagnosis/diagnosisID='+diagnosisID, {
            diagnosisID: parseInt(diagnosisID),
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
        // return to the health records page
        navigate(`/health-records`)
        window.location.reload()
    }

    // Updates the diagnosis in the database to cancelled
    function clickCancelButton(event){

        event.preventDefault();
        var rightNow = new Date();
        var formattedDay = rightNow.getDate() < 10 ? "0" + rightNow.getDate().toString() : rightNow.getDate()
        var formattedMonth = (rightNow.getMonth()+1) < 10 ? "0" + (rightNow.getMonth()+1).toString() : (rightNow.getMonth()+1)
        var diagnosisDate = rightNow.getFullYear() + "-" + formattedMonth +"-" + formattedDay + " 00:00:00"

        event.preventDefault();
        document.getElementById("descriptionInput").value = ""
        document.getElementById("diagnosisInput").value = ""

        axios.put('http://localhost:8080/app/treatment/diagnosis/diagnosisID='+diagnosisID, {
            diagnosisID: parseInt(diagnosisID),
            diagnosis: diagnosis,
            description: description,
            diagnosisDate: diagnosisDate,
            diagnosisStatus: "Cancelled",
            animalID: parseInt(animalID),
            userID: parseInt(userID)
        }).then(
          res => {
              console.log(res);
          }
        )
        // return to the health records page after submission
        navigate(`/health-records`)
        window.location.reload()
    }


  return (
      
    <div className="main-container d-flex flex-column flex-grow-1">
        {/* Only Animal Health Technicians will be able to view this page */}
        { allowView ? 

        <div className="d-flex w-100 h-100">
            {GetDiagnosisForAnimal()}
            <div className="sidebar">
                <Sidebar />
            </div>
            <div className="placeholder">
                <Sidebar />
            </div>
            <div className= "d-flex flex-column w-100">
                <AnimalNavbar />

                <h1 className="ms-5 mt-5">Update Diagnosis</h1>

                <div class="custom-field mt-4 mb-3 mx-5">
                    <label className="mb-2"> Diagnosis: </label> <br/>
                    <textarea className="form-control w-25" id="diagnosisInput" value={diagnosis} onChange={getDiagnosis} cols='100' rows='1'></textarea>
                </div>

                <div class="custom-field mt-4 mb-3 mx-5">
                    <label className="mb-2"> Description: </label> <br/>
                    <textarea className="form-control w-50" id="descriptionInput" value={description} onChange={getDescription} cols='100' rows='5'></textarea>
                </div>
                <div class="button mx-5 mt-3">
                    <button className="btn btn-secondary me-3" onClick={clickUpdateButton}>Update</button>
                    <button className="btn btn-secondary" onClick={clickCancelButton}>Cancel Diagnosis</button>
                </div>
            </div>
        </div>: <a href="/health-records">Only Animal Health Technicians may update diagnoses. Click here to return to health records.</a>}
    </div>
        
    );
}

export default ManageDiagnosis;