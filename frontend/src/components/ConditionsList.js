import axios from 'axios';
import React, {Component} from "react"
import 'bootstrap/dist/css/bootstrap.min.css';

export default class ConditionsList extends Component{
    
    state = {
        diagnosisList: [],
        animalID : localStorage.getItem("animalID"),
        currUserType: localStorage.getItem("userType"),
        currUserID: localStorage.getItem("userID")
    };

    // get all the diagnoses associated with an animal
    componentDidMount(){
        axios.get('http://localhost:8080/app/treatment/diagnosis/animalID='+this.state.animalID).then(
            res => {
                this.setState({diagnosisList: res.data})
            }
        )
    }

    // Provide access to individual diagnoses for editing
    cardLink(diagnosis){
        var diagnosisID = diagnosis.diagnosisID.toString()

        return <a href={"/update-diagnosis/single?diagnosisID="+diagnosisID}>{diagnosis.diagnosis}</a>
    }

    // Update the diagnosis in the database to Complete
    clickCompleteButton(diagnosis){

        var rightNow = new Date();
        var formattedDay = rightNow.getDate() < 10 ? "0" + rightNow.getDate().toString() : rightNow.getDate()
        var formattedMonth = (rightNow.getMonth()+1) < 10 ? "0" + (rightNow.getMonth()+1).toString() : (rightNow.getMonth()+1)
        var diagnosisDate = rightNow.getFullYear() + "-" + formattedMonth +"-" + formattedDay + " 00:00:00"

        axios.put('http://localhost:8080/app/treatment/diagnosis/diagnosisID='+diagnosis.diagnosisID, {
            diagnosisID: parseInt(diagnosis.diagnosisID), 
            diagnosisDate: diagnosisDate,
            diagnosis: diagnosis.diagnosis,
            description: diagnosis.description,
            diagnosisStatus: "Complete",
            animalID: parseInt(this.state.animalID),
            userID: parseInt(this.state.currUserID)
        }).then(
          res => {
              console.log(res);
          }
        )
        window.location.reload()
    }

    render(){
        return(
            <div className="overflow-auto">
                    {/* display a message if an animal has no ongoing conditions */}
                    {(this.state.diagnosisList.length === 0) ? 
                        <div class="card text-black bg-basic mx-5 my-3"  style={{width: "30rem"}}>
                            <div class="card-header" >
                                No ongoing conditions.
                            </div>
                        </div>
                        :this.state.diagnosisList.map(diagnosis => 
                            <div class="card text-black bg-basic mx-5 my-3"  style={{width: "30rem"}}>
                                <div class="card-header" >
                                    {this.cardLink(diagnosis)}
                                </div>
                                
                                <div class="card-body"  >
                                    {diagnosis.description}
                                </div>
                                <div>
                                    <button className="btn btn-success mx-2 my-2" onClick={ () => this.clickCompleteButton(diagnosis) }>Mark as Complete</button> 
                                </div>
                            </div>)
                        
                    }
                </div>
           
        )
    }
}