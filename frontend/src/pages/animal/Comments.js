import 'bootstrap/dist/css/bootstrap.min.css';
import Sidebar from '../../components/Sidebar';
import AnimalNavbar from '../../components/AnimalNavbar';

import axios from 'axios';
import '../../styling/Comments.css';
import CommentsList from '../../components/CommentsList'
import React, {useState} from 'react'
import {useLocation, useNavigate} from 'react-router-dom'

import jwt_decode from "jwt-decode";



function Comments() {
    const [message, setData] = useState(null);
    const [originalVal, setOriginalVal] = useState(null);

    const urlParams = new URLSearchParams(useLocation().search)
    const commentID = 1; //dummy value, commentID is updated on the backEnd before being saved
    const showStudents = urlParams.get("withStudents")==="true" ? true:false


    const animalID = localStorage.getItem("animalID")
    const authorID = localStorage.getItem("userID")
    const firstName = localStorage.getItem("userFirstName")
    const lastName = localStorage.getItem("userLastName")

    let token = localStorage.getItem("token")
    let decoded = jwt_decode(token);
    const [activeUserType, setType] = useState(decoded.sub)

    let navigate = useNavigate();
    let currLocation = useLocation();

    //update the state on change
    function getData(val){
        setData(val.target.value)
        setOriginalVal(val.target.value)
        // console.warn(val.target.value)
      }
      
      //when the enter key is hit, get the information and create new comment on database
      function handleKeyDown(event){
        
        if (event.key === 'Enter'){ 
          setData(originalVal)
          document.getElementById("commentInput").value = ""
          console.log("From hitting the enter key: "+message)
          // event.preventDefault();
          postNewComment(event)
        }
      }
    
      //when the submit button is clicked, get the information and create new comment on database
      function clickButton(event){
        event.preventDefault();
        setData(originalVal)
        document.getElementById("commentInput").value = ""
        console.log("From Clicking the button: "+message)
        postNewComment(event)
      }

      //create new comment on the database
      function postNewComment(event){
        event.preventDefault();
        var rightNow = new Date();
        var formatedDay = rightNow.getDate()<10 ? "0"+rightNow.getDate().toString():rightNow.getDate()
        var formatedMonth = (rightNow.getMonth()+1)<10 ? "0"+(rightNow.getMonth()+1).toString():(rightNow.getMonth()+1)
        console.log(rightNow.getHours())
        var hours12 = rightNow.getHours() >=12 ? rightNow.getHours()-12:rightNow.getHours()
        var AMPM = rightNow.getHours() >=12 ? "PM":"AM"
        var formattedMinutes = rightNow.getMinutes() < 10 ? "0"+rightNow.getMinutes().toString() : rightNow.getMinutes().toString()
        var dateTime = rightNow.getFullYear() + "-" + formatedMonth +"-" + formatedDay +" "+ hours12 +":"+ formattedMinutes +" "+ AMPM

        axios.post('http://localhost:8080/app/comments/', {
          animalID:  parseInt(animalID),
          commentID: commentID,
          authorID:  parseInt(authorID),
          timestamp: dateTime,
          message: message,
          firstName: firstName,
          lastName: lastName,
          userType: activeUserType  

        }).then(
          res => {
              console.log(res);
          } )
      window.location.reload()
      }

      //change the url to either include or exclude student comments
      function toggle(){
        console.log("inside toggle()")

        console.log(showStudents)
        // navigate(currLocation.search.replace("withStudents=false", "withStudents=true"))
        if (showStudents){
          navigate(currLocation.search.replace("withStudents=true", "withStudents=false"))
        }
        else {
          navigate(currLocation.search.replace("withStudents=false", "withStudents=true"))
        }
        window.location.reload()
        // <Redicret push to={window.location.href.replace("withStudents=true", "withStudents=false")}>
      }
      

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
        
        <div className="d-flex mt-5 mb-4 mx-5 w-75">
              <h1>Comments</h1>
              <div className="d-flex align-items-center mx-5 ps-5 pt-5">
                {showStudents === true ?
                <input type="checkbox" id="toggleStudents" onChange={toggle} />:
                <input type="checkbox" id="toggleStudents" onChange={toggle} checked/>}
                <label>Toggle Students</label>
              </div>
        </div>      
        
        <div className="d-flex flex-column flex-grow-1 align-items-left mx-5 ps-5 w-75">
            {/* {displays the cards within an autoscroll box} */}
            <div class="ex1">
            <CommentsList animalID={animalID} toggleStudent={showStudents} usertype={activeUserType} />
            </div>
            <label className="d-flex flex-row custom-field">
                <input className="form-control" id="commentInput" type="text" required onChange={getData} onKeyDown={handleKeyDown} size="100" placeholder="Enter a message" />
                <button className="btn btn-secondary ms-2" onClick={clickButton}>Submit</button>
            </label>
        </div>
        </div>
        </div>
    </div>
    );
}

export default Comments;