import "../styling/Navbar.css";
import DropdownButton from 'react-bootstrap/DropdownButton'
import Dropdown from 'react-bootstrap/Dropdown'
import jwt_decode from "jwt-decode";
import { useState } from 'react';

const AnimalNavbar = () => {
    let token = localStorage.getItem("token")
    let decoded = jwt_decode(token);
    const [activeUserType, setType] = useState(decoded.sub)

    // Sets the display of the health status in the navbar
    function healthStatus() {
        if (localStorage.getItem("healthStatus") === "Healthy") {
            return <p className="healthy-status-text">{localStorage.getItem("healthStatus")}</p>
        } else if (localStorage.getItem("healthStatus") === "Injured") {
            return <p className="injured-status-text">{localStorage.getItem("healthStatus")}</p>
        } else if (localStorage.getItem("healthStatus") === "Sick") {
            return <p className="sick-status-text">{localStorage.getItem("healthStatus")}</p>
        } else if (localStorage.getItem("healthStatus") === "Passed") {
            return <p className="passed-status-text">{localStorage.getItem("healthStatus")}</p>
        }
    }


    return (
        // Display of the animal navbar
        <div className="navbar d-flex justify-content-start">
            <div className="d-flex flex-column justify-content-start align-items-center py-4 animal-info mx-5">
                <h3 className="mt-2 fw-bold">{localStorage.getItem("animalName")}</h3>
                <p className="species-text">{localStorage.getItem("animalSpecies")}</p>
                {healthStatus()}
                
            </div>

            {/* Dropdown menu items */}
            <div className="px-4 dropdown" style={{paddingBottom: "35px"}}>
                <DropdownButton id="dropdown-basic-button" title="Select an option..." variant="dark">
                    <Dropdown.Item href="/animal-info">Animal Information</Dropdown.Item>
                    <Dropdown.Item href="/health-records">Health Records</Dropdown.Item>
                    <Dropdown.Item href="/reminders">Reminders</Dropdown.Item>
                    <Dropdown.Item href="/comments?withStudents=true">Comments</Dropdown.Item>
                    <Dropdown.Item href="/photos">Photos</Dropdown.Item>
                    <Dropdown.Item href="/weight-history">Weight History</Dropdown.Item>
                    {activeUserType === "Teaching Technician" ? 
                        <Dropdown.Item href="/request-animal">Request Animal</Dropdown.Item>:null
                    }
                    
                    <Dropdown.Item href="/send-alert">Send Alert</Dropdown.Item>
                </DropdownButton>
            </div>
        </div>
    )
}

export default AnimalNavbar