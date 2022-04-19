import axios from 'axios';
import React, {Component} from "react"
import 'bootstrap/dist/css/bootstrap.min.css';

export default class RequestViewByAdmin extends Component{

    state = {
        allRequests:[],
        viewableStatus:"Pending", //Admin will only see the Requests which have a status of "Pending"
        currentUserType:"Admin",
        updatedStatus: "Accepted" //new status message passed to api and database

    };
    
    //get all requests
    componentDidMount(){
        // console.log(this.state.toggleStudent)
        axios.get('http://localhost:8080/app/request/').then(
            res => {
                console.log(res);
                this.setState({allRequests: res.data})
            }
        )
    }


    //update the status of the request status to accepted
    clickAccept(arrData){
        console.log(">>> Inside clickAccept()")
        console.log("request.requestID = " + arrData[this.idxReq.requestID])
        console.log("request.requestStatus  = " + arrData[this.idxReq.requestStatus ])
    
        axios.put('http://localhost:8080/app/request/'+arrData[this.idxReq.requestID], {
                animalID : parseInt( arrData[this.idxReq.animalID]),
                requestID : parseInt( arrData[this.idxReq.requestID]),
                requesterID : parseInt( arrData[this.idxReq.requesterID]),
                requestDate : arrData[this.idxReq.requestDate],
                checkoutDate : arrData[this.idxReq.checkoutDate],
                returnDate : arrData[this.idxReq.returnDate],
                reason : arrData[this.idxReq.reason],
                requestStatus : "Accepted", 
                requesterFirstName : arrData[this.idxReq.requesterFirstName],
                requesterLastName : arrData[this.idxReq.requesterLastName],
                animalName : arrData[this.idxReq.animalName],
                animalSpecies : arrData[this.idxReq.animalSpecies]
            }).then(
              res => {
                  console.log(res);
              } )
        window.location.reload()
    }

    //Update the status of the request to be Rejected
    clickReject(arrData){
        console.log(">>> Inside clickREject()")
        console.log("request.requestID = " + arrData[this.idxReq.requestID])
        console.log("request.requestStatus  = " + arrData[this.idxReq.requestStatus ])
    
        axios.put('http://localhost:8080/app/request/'+arrData[this.idxReq.requestID], {
                animalID : parseInt( arrData[this.idxReq.animalID]),
                requestID : parseInt( arrData[this.idxReq.requestID]),
                requesterID : parseInt( arrData[this.idxReq.requesterID]),
                requestDate : arrData[this.idxReq.requestDate],
                checkoutDate : arrData[this.idxReq.checkoutDate],
                returnDate : arrData[this.idxReq.returnDate],
                reason : arrData[this.idxReq.reason],
                requestStatus : "Rejected", 
                requesterFirstName : arrData[this.idxReq.requesterFirstName],
                requesterLastName : arrData[this.idxReq.requesterLastName],
                animalName : arrData[this.idxReq.animalName],
                animalSpecies : arrData[this.idxReq.animalSpecies]
            }).then(
              res => {
                  console.log(res);
              } )
              window.location.reload()      
    }

    render(){
        return(
            <div className="overflow-auto">
                {/* {Load each request as a card} */}
                {this.state.allRequests.map(request => 
                (request.requestStatus === "Pending" ) ? //Admin will only see the Requests which have a status of "Pending"
                    <div class="card bg-light mx-5 my-3" key={request.requestID} style={{width: "50rem"}}>
                        <div class="card-header" >
                            <h6> Request {request.requestID} by {request.requesterFirstName} {request.requesterLastName} </h6> 
                            <h6> Animal {request.animalID}: {request.animalName}, {request.animalSpecies} </h6>
                        </div>
                        <div class="card-body"  >
                            <div className="d-flex justify-content-start">
        
                                <div className="d-flex flex-column justify-content-start px-3 pt-3">
                                    <p> Request Date: {request.requestDate.replace(" 00:00:00","")} </p> 
                                    <p> Checkout Date: {request.checkoutDate.replace(" 00:00:00","")} </p>
                                    <p> Return Date: {request.returnDate.replace(" 00:00:00","")} </p> 
                                </div>

                                <div className="px-5 py-5 reason" >
                                    <p> {request.reason} </p>
                                </div>
                                <div className="d-flex flex-column px-5 py-4 align-right">
                                        <button className="btn btn-secondary" onClick={() => this.clickAccept([request.animalID, request.requestID, request.requesterID, request.requestDate, request.checkoutDate, request.returnDate, request.reason, request.requestStatus, request.requesterFirstName, request.requesterLastName, request.animalName, request.animalSpecies])}>ACCEPT</button>
                                        <p/>
                                        <button className="btn btn-secondary" onClick={() => this.clickReject([request.animalID, request.requestID, request.requesterID, request.requestDate, request.checkoutDate, request.returnDate, request.reason, request.requestStatus, request.requesterFirstName, request.requesterLastName, request.animalName, request.animalSpecies])}>REJECT</button>
                                        
                                    </div>
                            </div>

                        </div>
                    </div>:null
                )}
            </div>
        )
    }
}