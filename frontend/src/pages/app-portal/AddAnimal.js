import '../../styling/Main.css';
import Sidebar from '../../components/Sidebar';
import AddAnimalNavbar from '../../components/AddAnimalNavbar';
import { useState } from 'react';
import Form from "react-bootstrap/Form";
import axios from 'axios';

function AddAnimal() {
    const [animalInfo, setAnimalInfo] = useState({});

    // Sends request to add animal to the database
    function handleSubmit(event) {
        event.preventDefault();

        console.log(animalInfo);

        axios.post("http://localhost:8080/app/animal/add-animal", animalInfo)
        .then(res => {
          console.log(res);
          console.log(res.data);
        })
        .catch(err => console.log(err));

        alert("Animal added")
        window.location.reload()
    }

    // Form to add an animal
    return (
            <div className="d-flex w-100 h-100">
                <div className="sidebar">
                    <Sidebar />
                </div>

                <div className="placeholder">
                    <Sidebar />
                </div>

                <div className="d-flex flex-column flex-grow-1">
                    <AddAnimalNavbar />

                    <h1 className="mt-5 ms-5">Add Animal</h1>
                        <form className="d-flex flex-column  align-items-start mt-5" onSubmit={handleSubmit}>
                            <div className="d-flex my-3 w-50">
                                <h5 className="w-100">Name:</h5>
                                <input className="form-control" value={animalInfo.name} onChange={e => setAnimalInfo({...animalInfo, name: e.target.value})} />
                            </div>

                            <div className="d-flex my-3 w-50">
                                <h5 className="w-100">Species:</h5>
                                <input className="form-control" value={animalInfo.species} onChange={e => setAnimalInfo({...animalInfo, species: e.target.value})}/>
                            </div>

                            <div className="d-flex my-3 w-50">
                                <h5 className="w-100">Breed:</h5>
                                <input className="form-control" value={animalInfo.breed} onChange={e => setAnimalInfo({...animalInfo, breed: e.target.value})}/>
                            </div>

                            <div className="d-flex my-3 w-50">
                                <h5 className="w-100">Tattoo Number:</h5>
                                <input className="form-control" value={animalInfo.tattoo} onChange={e => setAnimalInfo({...animalInfo, tattoo: e.target.value})}/>
                            </div>

                            <div className="d-flex my-3 w-50">
                                <h5 className="w-100">City Tattoo:</h5>
                                <input className="form-control" value={animalInfo.cityTattoo} onChange={e => setAnimalInfo({...animalInfo, cityTattoo: e.target.value})}/>
                            </div>

                            <div className="d-flex my-3 w-50">
                                <h5 className="w-100">Birth Date:</h5>
                                <Form.Control type="date" value={animalInfo.dob} onChange={e => setAnimalInfo({...animalInfo, dob: e.target.value})}></Form.Control>
                            </div>

                            <div className="d-flex my-3 w-50">
                                <h5 className="w-100">Sex:</h5>
                                <select class="form-select ms-2" value={animalInfo.sex} onChange={e => setAnimalInfo({...animalInfo, sex: e.target.value})}>
                                    <option selected>Select an option...</option>
                                    <option value="M">Male</option>
                                    <option value="F">Female</option>
                                </select>
                            </div>

                            <div className="d-flex my-3 w-50">
                                <h5 className="w-100">RFID:</h5>
                                <input className="form-control" value={animalInfo.rfid} onChange={e => setAnimalInfo({...animalInfo, rfid: e.target.value})}/>
                            </div>

                            <div className="d-flex my-3 w-50">
                                <h5 className="w-100">Microchip:</h5>
                                <input className="form-control" value={animalInfo.microchip} onChange={e => setAnimalInfo({...animalInfo, microchip: e.target.value})}/>
                            </div>

                            <div className="d-flex my-3 w-50">
                                <h5 className="w-100">Health Status:</h5>
                                <select class="form-select ms-2" value={animalInfo.healthStatus} onChange={e => setAnimalInfo({...animalInfo, healthStatus: e.target.value})}>
                                    <option selected>Select an option...</option>
                                    <option value="Healthy">Healthy</option>
                                    <option value="Injured">Injured</option>
                                    <option value="Sick">Sick</option>
                                    <option value="Passed">Passed</option>
                                </select>
                            </div>

                            <div className="d-flex my-3 w-50">
                                <h5 className="w-100">Availability Status:</h5>
                                <select class="form-select ms-2" value={animalInfo.availabilityStatus} onChange={e => setAnimalInfo({...animalInfo, availabilityStatus: e.target.value})}>
                                    <option selected>Select an option...</option>
                                    <option value="true">Available</option>
                                    <option value="false">Unavailable</option>
                                </select>
                            </div>

                            <div className="d-flex my-3 w-50">
                                <h5 className="w-100">Colour:</h5>
                                <input className="form-control" value={animalInfo.colour} onChange={e => setAnimalInfo({...animalInfo, colour: e.target.value})}/>
                            </div>

                            <div className="d-flex my-3 w-50">
                                <h5 className=" w-100">Additional Information:</h5>
                                <textarea className="form-control" value={animalInfo.additionalInfo} onChange={e => setAnimalInfo({...animalInfo, additionalInfo: e.target.value})}/>
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

export default AddAnimal;
