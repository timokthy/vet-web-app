import 'bootstrap/dist/css/bootstrap.min.css';
import Sidebar from '../../components/Sidebar';
import  '../../styling/Scrollbox.css';

// Requires npm install axios --save
import React from 'react'

// different view of the page depending on the user's type
import RequestViewByAdmin from '../../components/RequestViewByAdmin.js';
import RequestViewByHealthTechnician from '../../components/RequestViewByHealthTechnician.js';
import RequestViewByTeachingTechnician from '../../components/RequestViewByTeachingTechnician.js';
import jwt_decode from "jwt-decode";
import BlankNavbar from '../../components/BlankNavbar';


function ViewRequests() {
    
    let token = localStorage.getItem("token")
    let decoded = jwt_decode(token)
    let userType = decoded.sub
    let allowView=false;
    if (userType === "Admin" || userType === "Animal Health Technician"  || userType === "Teaching Techncian"){allowView = true}

    // Only allows Admins, Animal Health Technicians, or Teaching Technicians to access this page as per the hackathon
    return(
    
    <div className="main-container d-flex flex-column flex-grow-1">
        
        <div className="d-flex w-100 h-100">
                <div className="sidebar">
                    <Sidebar />
                </div>

                <div className="placeholder">
                    <Sidebar />
                </div>

            
            <div className="d-flex flex-column flex-grow-1">
                <BlankNavbar />
                { allowView ? 
                <div>
                    <div className="d-flex ms-5 mt-5">
                        <h1>View Requests</h1>
                    </div>
                    
                    <div class="ex1 mt-4 ms-5 w-75">
                        {userType === "Admin" ?
                            <RequestViewByAdmin/>: null
                        }
                        {userType === "Animal Health Technician" ?
                            <RequestViewByHealthTechnician/>: null
                        }
                        {userType === "Teaching Technician" ?
                            <RequestViewByTeachingTechnician/>: null
                        }
                    </div> 
                </div>: <a href="/">You are not authorized to view this page. Return to Login</a>} 
            </div> 
        </div>        
        </div>
    );
}

export default ViewRequests;