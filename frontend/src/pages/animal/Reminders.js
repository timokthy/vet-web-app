import 'bootstrap/dist/css/bootstrap.min.css';
import AnimalNavbar from '../../components/AnimalNavbar';
import Sidebar from '../../components/Sidebar';

import '../../styling/Comments.css';
import RemindersList from '../../components/RemindersList'
import React, {useState} from 'react'
import jwt_decode from "jwt-decode";

function Reminders() {
    const animalID = localStorage.getItem("animalID")

    let token = localStorage.getItem("token")
    let decoded = jwt_decode(token);
    const [activeUserType, setType] = useState(decoded.sub)

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

            <div className="d-flex mt-5 mb-2 mx-5">
                <h1>Reminders</h1>
            </div> 
            <div className="d-flex flex-column flex-grow-1 align-items-left mx-5 ps-5 w-75">
                  {(activeUserType !== "Student") ? 
                    <label className="d-flex flex-row-reverse custom-field align-items-right mb-3">
                      <a  href="/reminders/add" >
                        <button className="btn btn-secondary ms-2"style={{width:"200px"}}>Add Reminder</button>
                      </a>
                    </label>
                    :null
                  }
                {/* {Displays the reminders as cards within an autoscroll box} */}
                <div className="ex1">
                  <RemindersList animalID={animalID} usertype={activeUserType}/>
                </div>
                  
            </div>
        </div>
        </div>
    </div>
    );
}

export default Reminders;