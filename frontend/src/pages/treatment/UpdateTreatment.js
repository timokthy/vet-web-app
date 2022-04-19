import 'bootstrap/dist/css/bootstrap.min.css';
import Sidebar from '../../components/Sidebar';
import AnimalNavbar from '../../components/AnimalNavbar';

// Requires npm install axios --save
import axios from 'axios';
import React, {useState, useEffect} from 'react'
import {useLocation} from 'react-router-dom'
import {useNavigate} from 'react-router-dom'
import jwt_decode from "jwt-decode";

function ManageTreatment() {

    const urlParams = new URLSearchParams(useLocation().search)
    const treatmentID = urlParams.get("treatmentID")

    const [treatment, setTreatment] = useState(null);
    const [description, setDescription] = useState(null);
    
    const animalID = localStorage.getItem("animalID")
    const userID = localStorage.getItem("userID")
    const treatmentStatus = "Ongoing"

    let token = localStorage.getItem("token")
    let decoded = jwt_decode(token)
    const [activeUserType, setType] = useState(decoded.sub);
    let allowView=false;
    if (activeUserType === "Animal Health Technician"){allowView = true}


    let navigate = useNavigate();

    // Get all the treatments associated with one animal 
    function GetTreatmentForAnimal(){
        useEffect(()=>{
            axios.get('http://localhost:8080/app/treatment/protocol/treatmentID='+treatmentID).then(
                res => {
                    var singleTreatment = res.data[0]
                    setTreatment(singleTreatment.treatment)
                    setDescription(singleTreatment.description)
                })
        },[])
    }

    function getTreatment(treatment){
        setTreatment(treatment.target.value)
    }

    function getDescription(treatmentDescription){
        setDescription(treatmentDescription.target.value)
    }

    function clickUpdateButton(event){
        event.preventDefault();
        document.getElementById("treatmentInput").value = ""
        document.getElementById("descriptionInput").value = ""
        sendRequest(event)
    }

    function sendRequest(event){

        event.preventDefault();
        var rightNow = new Date();
        var formattedDay = rightNow.getDate() < 10 ? "0" + rightNow.getDate().toString() : rightNow.getDate()
        var formattedMonth = (rightNow.getMonth()+1) < 10 ? "0" + (rightNow.getMonth()+1).toString() : (rightNow.getMonth()+1)
        var treatmentDate = rightNow.getFullYear() + "-" + formattedMonth +"-" + formattedDay + " 00:00:00"

        axios.put('http://localhost:8080/app/treatment/protocol/treatmentID='+treatmentID, {
            treatmentID: parseInt(treatmentID), 
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
        // naviagate back to heatlh records page when done
        navigate(`/health-records`)
        window.location.reload()
    }

    function clickCancelButton(event){

        event.preventDefault();
        var rightNow = new Date();
        var formattedDay = rightNow.getDate() < 10 ? "0" + rightNow.getDate().toString() : rightNow.getDate()
        var formattedMonth = (rightNow.getMonth()+1) < 10 ? "0" + (rightNow.getMonth()+1).toString() : (rightNow.getMonth()+1)
        var treatmentDate = rightNow.getFullYear() + "-" + formattedMonth +"-" + formattedDay + " 00:00:00"

        event.preventDefault();
        document.getElementById("descriptionInput").value = ""
        document.getElementById("treatmentInput").value = ""

        axios.put('http://localhost:8080/app/treatment/protocol/treatmentID='+treatmentID, {
            treatmentID: parseInt(treatmentID), 
            treatmentDate: treatmentDate,
            treatment: treatment,
            description: description,
            treatmentStatus: "Cancelled",
            animalID: parseInt(animalID),
            userID: parseInt(userID)
            
        }).then(
          res => {
              console.log(res);
          }
        )
        // naviagate back to heatlh records page when done
        navigate(`/health-records`)
        window.location.reload()
    }


  return (
      
    <div className="main-container d-flex flex-column flex-grow-1">

        <div className="d-flex w-100 h-100">
            {GetTreatmentForAnimal()}
            <div className="sidebar">
                <Sidebar />
            </div>
            <div className="placeholder">
                <Sidebar />
            </div>
            <div className= "d-flex flex-column w-100">
                <AnimalNavbar />
                {/* Only allow Animal Health Technicians to update treatments */}
                { allowView ? 
                <div>
                <h1 className="ms-5 mt-5">Update Treatment</h1>
                <div class="custom-field mt-4 mb-3 mx-5">
                    <label className="mt-4 mb-2"> Treatment Protocol: </label> <br/>
                    <textarea className="form-control w-25" id="treatmentInput" value={treatment} onChange={getTreatment} cols='100' rows='1'></textarea>
                </div>

                <div class="custom-field mt-4 mb-3 mx-5">
                    <label className="mb-2"> Description: </label> <br/>
                    <textarea className="form-control w-50" id="descriptionInput" value={description} onChange={getDescription} cols='100' rows='5'></textarea>
                </div>
                <div class="button mx-5 mt-3">
                    <button className="btn btn-secondary me-3" onClick={clickUpdateButton}>Update</button>
                    <button className="btn btn-secondary" onClick={clickCancelButton}>Cancel Treatment</button>
                </div>
            </div>: <a href="/health-records">Only Animal Health Technicians may update treatments. Click here to return to health records.</a>}
            </div>
        </div>
    </div>);
}

export default ManageTreatment;