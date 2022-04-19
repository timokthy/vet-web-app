import '../../styling/Main.css';
import Sidebar from '../../components/Sidebar';
import AnimalNavbar from '../../components/AnimalNavbar';
import Table from 'react-bootstrap/Table'
import Form from "react-bootstrap/Form";
import axios from 'axios';
import { useState, useEffect } from 'react';
import CloseButton from 'react-bootstrap/CloseButton'


function WeightHistory() {
    const [isLoading, setLoading] = useState(true);
    const [weight, setWeight] = useState(0);
    const [date, setDate] = useState();
    const [weightHistory, setWeightHistory] = useState();

    const[animalID, setID] = useState(localStorage.getItem("animalID"))

    // Retrieves the weight history of the selected animal
    useEffect(() => {
        axios.get("http://localhost:8080/app/animal/weight-history/"+animalID)
           .then(res => {
                const weightEntries = res.data;
                setWeightHistory([weightEntries]);
                setLoading(false);
           })
     })

    // Populates the weight history table
    function populateTable() {
        let rows = [];

        for(let i = 0; i < weightHistory[0].length; i++) {
            rows.push(<tr>
                        <td>{weightHistory[0][i].date}</td>
                        <td>{weightHistory[0][i].weight}</td>
                        <td><CloseButton onClick={() => onDelete(i)} /></td>
                      </tr>);
        }
            
        return <tbody>{rows}</tbody>
    }

    function onDelete(index) {
        // console.log({animalId: animalID, date: weightHistory[0][index].date, weight: weightHistory[0][index].weight});
        axios.delete("http://localhost:8080/app/animal/weight-history", {data: {animalId: animalID, 
                                                                         date: weightHistory[0][index].date,
                                                                         weight: weightHistory[0][index].weight,
                                                                         userId: weightHistory[0][index].userId}})
        .then(res => {
            console.log(res);
            console.log(res.data);
          })
        .catch(err => console.log(err));
        
        window.location.reload()
    }
    
    // Adds a weight entry to the database
    function handleSubmit(event) {
        event.preventDefault();

        axios.post("http://localhost:8080/app/animal/weight-history", { animalId: animalID, date: date, weight: weight, userId: 1 })
        .then(res => {
          console.log(res);
          console.log(res.data);
        })
        .catch(err => console.log(err));

        window.location.reload()
    }
    
    // Buffer that waits until table has loaded to show the page
    if (isLoading) {
        return<></>;
    }

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

                    <h1 className="mt-5 ms-5">Weight History</h1>

                    <div className="d-flex flex-column ms-5">

                        <form className="d-flex flex-column align-items-start my-5 w-100" onSubmit={handleSubmit}>
                            <div className="d-flex mb-3 w-25">
                                <h4 className="w-100">Weight:</h4>
                                <input className="form-control" type="number" value={weight} onChange={e => setWeight(e.target.value)}/>
                            </div>

                            <div className="d-flex mb-3 w-25">
                                <h4 type="date" className="w-100">Date:</h4>
                                <Form.Control type="date" value={date} onChange={e => setDate(e.target.value)}></Form.Control>
                            </div>

                            <div className="ps-5 ms-5 w-25 mb-4">
                                <button className="btn btn-secondary ms-4 px-3 py-1" type="submit">Submit</button>
                            </div>
                        </form>


                        <Table striped bordered hover center className="w-25 ms-5">
                            <thead className="w-50">
                                <tr>
                                <th>Date</th>
                                <th>Weight Recorded (lbs)</th>
                                <th></th>
                                </tr>
                            </thead>
                            {populateTable()}
                            </Table>

                        </div>

                    </div>


                </div>
        );
    }

export default WeightHistory;
