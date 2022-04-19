import '../../styling/Main.css';
import Sidebar from '../../components/Sidebar';
import AnimalNavbar from '../../components/AnimalNavbar';
import {useLocation} from 'react-router-dom';
import { useState } from 'react';
import Form from "react-bootstrap/Form";
import axios from 'axios';

function AnimalInfo() {
    // Hook to retrieve the input values
    const [isLoading, setLoading] = useState(true);
    const [name, setName] = useState();
    const [species, setSpecies] = useState();
    const [breed, setBreed] = useState();
    const [tattoo, setTattoo] = useState();
    const [cityTattoo, setCityTattoo] = useState();
    const [dob, setDob] = useState();
    const [sex, setSex] = useState();
    const [rfid, setRfid] = useState();
    const [microchip, setMicrochip] = useState();
    const [healthStatus, setHealthStatus] = useState();
    const [availabilityStatus, setAvailabilityStatus] = useState();
    const [colour, setColour] = useState();
    const [additionalInfo, setAdditionalInfo] = useState();

    const urlParams = new URLSearchParams(useLocation().search)
    var animalID = urlParams.get("animalID")

    if (animalID !== null){
        localStorage.setItem("animalID", animalID)
    } else { 
        animalID = localStorage.getItem("animalID")
    }
    
    // Loads the existing animal data
    function loadData() {
        if (isLoading) {
            axios.get("http://localhost:8080/app/animal/" + animalID)
            .then(res => {
                const info = res.data;

                setName(info.name);
                localStorage.setItem("animalName", info.name)
                setSpecies(info.species);
                localStorage.setItem("animalSpecies", info.species)
                setBreed(info.breed);
                setTattoo(info.tattoo);
                setCityTattoo(info.cityTattoo);
                setDob(info.dob);
                setSex(info.sex);
                setRfid(info.rfid)
                setMicrochip(info.microchip);
                setHealthStatus(info.healthStatus);
                localStorage.setItem("healthStatus", info.healthStatus)
                setAvailabilityStatus(info.availabilityStatus);
                setColour(info.colour);
                setAdditionalInfo(info.additionalInfo);
            })
            .catch(err => console.log(err));

            setLoading(false);
        }
    }

    // Sends request to update the animal
    function handleSubmit(event) {
        event.preventDefault();
        
        axios.put("http://localhost:8080/app/animal/" + animalID, { name: name, species: species, breed: breed, tattoo: tattoo, 
                                                                    cityTattoo: cityTattoo, dob: dob, sex: sex, rfid: rfid,
                                                                    microchip: microchip, healthStatus: healthStatus,
                                                                    availabilityStatus: availabilityStatus, colour: colour,
                                                                    additionalInfo: additionalInfo})
        .then(res => {
          console.log(res);
          console.log(res.data);
        })
        .catch(err => console.log(err));

        alert("Animal updated")
        window.location.reload();
    }

    loadData();

    // Form to update the animal information
    return (
            <div className="d-flex w-100 h-100">
                <div className="sidebar">
                    <Sidebar />
                </div>

                <div className="placeholder">
                    <Sidebar />
                </div>


                <div className="d-flex flex-column flex-grow-1">
                    <AnimalNavbar />

                    <h1 className="mt-5 ms-5">Animal Information</h1>
                        <form className="d-flex flex-column  align-items-start mt-5" onSubmit={handleSubmit}>
                            <div className="d-flex my-3 w-50">
                                <h5 className="w-100">Name:</h5>
                                <input className="form-control" value={name} onChange={e => setName(e.target.value)} />
                            </div>

                            <div className="d-flex my-3 w-50">
                                <h5 className="w-100">Species:</h5>
                                <input className="form-control" value={species} onChange={e => setSpecies(e.target.value)} />
                            </div>

                            <div className="d-flex my-3 w-50">
                                <h5 className="w-100">Breed:</h5>
                                <input className="form-control" value={breed} onChange={e => setBreed(e.target.value)} />
                            </div>

                            <div className="d-flex my-3 w-50">
                                <h5 className="w-100">Tattoo:</h5>
                                <input className="form-control" value={tattoo} onChange={e => setTattoo(e.target.value)}/>
                            </div>

                            <div className="d-flex my-3 w-50">
                                <h5 className="w-100">City Tattoo:</h5>
                                <input className="form-control" value={cityTattoo} onChange={e => setCityTattoo(e.target.value)}/>
                            </div>

                            <div className="d-flex my-3 w-50">
                                <h5 className="w-100">Birth Date:</h5>
                                <Form.Control type="date" value={dob} onChange={e => setDob(e.target.value)}></Form.Control>
                            </div>

                            <div className="d-flex my-3 w-50">
                                <h5 className="w-100">Sex:</h5>
                                <select class="form-select ms-2" value={sex} onChange={e => setSex(e.target.value)}>
                                    <option selected>Select an option...</option>
                                    <option value="M">Male</option>
                                    <option value="F">Female</option>
                                </select>
                            </div>

                            <div className="d-flex my-3 w-50">
                                <h5 className="w-100">RFID:</h5>
                                <input className="form-control" value={rfid} onChange={e => setRfid(e.target.value)}/>
                            </div>

                            <div className="d-flex my-3 w-50">
                                <h5 className="w-100">Microchip:</h5>
                                <input className="form-control" value={microchip} onChange={e => setMicrochip(e.target.value)}/>
                            </div>

                            <div className="d-flex my-3 w-50">
                                <h5 className="w-100">Health Status:</h5>
                                <select class="form-select ms-2" value={healthStatus} onChange={e => setHealthStatus(e.target.value)}>
                                    <option selected>Select an option...</option>
                                    <option value="Healthy">Healthy</option>
                                    <option value="Injured">Injured</option>
                                    <option value="Sick">Sick</option>
                                    <option value="Passed">Passed</option>
                                </select>
                            </div>

                            <div className="d-flex my-3 w-50">
                                <h5 className="w-100">Availability Status:</h5>
                                <select class="form-select ms-2" value={availabilityStatus} onChange={e => setAvailabilityStatus(e.target.value)}>
                                    <option selected>Select an option...</option>
                                    <option value="true">Available</option>
                                    <option value="false">Unavailable</option>
                                </select>
                            </div>

                            <div className="d-flex my-3 w-50">
                                <h5 className="w-100">Colour:</h5>
                                <input className="form-control" value={colour} onChange={e => setColour(e.target.value)}/>
                            </div>

                            <div className="d-flex my-3 w-50">
                                <h5 className=" w-100">Additional Information:</h5>
                                <textarea className="form-control" value={additionalInfo} onChange={e => setAdditionalInfo(e.target.value)}/>
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

export default AnimalInfo;
