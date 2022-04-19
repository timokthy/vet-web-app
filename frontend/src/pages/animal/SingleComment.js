import '../../styling/Main.css';
import Sidebar from '../../components/Sidebar';
import AnimalNavbar from '../../components/AnimalNavbar';
import {useLocation, useNavigate} from 'react-router-dom'
import React, {useEffect, useState} from 'react'
import axios from 'axios'

function SingleComment() {
    const urlParams = new URLSearchParams(useLocation().search)
    const commentID = urlParams.get("commentID")

    const [singleComment, setComment] = useState(null)
    const [name, setName] = useState(null)
    const [timestamp, setTimestamp] = useState(null)
    const [message, setMessage] = useState(null)

    let navigate = useNavigate();

    function GetPhotosForAnimal(){

        useEffect(()=>{
            axios.get('http://localhost:8080/app/comments/'+commentID).then(
                res => {
                    console.log(res);
                    console.log("list of comments.length = "+res.data.length)
                    var singleCom = res.data[0]
                    setComment(singleCom)
                    console.log("singleComment = ")
                    console.log(singleCom)
                    var singleComment = res.data[0]
                    setName(singleComment.firstName + " " + singleComment.lastName)
                    setTimestamp(singleComment.timestamp)
                    console.log("singleComment.message = "+singleComment.message)
                    setMessage(singleComment.message)
                    // console.log("after axios get, message = "+message)
                    
                }
            )
        }, []);
        console.log("after axios get, message = "+message)

        // setName(singleComment.firstName + " " + singleComment.lastName)
        // setTimestamp(singleComment.timestamp)
        // console.log("singleComment.message = "+singleComment.message)
        // setMessage(singleComment.message)
        // console.log("message = "+message)
    }


    function handleDelete(event) {
        event.preventDefault();

        axios.delete('http://localhost:8080/app/comments/'+  singleComment.commentID)
        .catch(err =>{
            console.log(err)
        })

        navigate("/comments?withStudents=true")
    }

    return (
            <div className="d-flex w-100 h-100">
                {GetPhotosForAnimal()}
                <div className="sidebar">
                    <Sidebar />
                </div>

                <div className="placeholder">
                    <Sidebar />
                </div>

                <div className="d-flex flex-column flex-grow-1">
                    <AnimalNavbar />

                    <h1 className="mt-5 ms-5">Comment #{commentID}</h1>
                        <div className="d-flex flex-column  align-items-start mt-5 mx-5" >
                            <div className="d-flex my-3 w-50">
                                <h4 className="w-100">Comment Author:</h4>
                                {/* <input className="form-control" value="name"/> */}
                                <input className="form-control" value={name} style = {{width: "750px"}}/>
                                {/* <input className="form-control" value={singleComment.firstName + " " + singleComment.lastName}/> */}
                            </div>
                            <div className="d-flex my-3 w-50">
                                <h4 className="w-100">Timestamp:</h4>
                                {/* <input className="form-control" value="timestamp"/> */}
                                <input className="form-control" value={timestamp} style = {{width: "750px"}}/>
                                {/* <input className="form-control" value={singleComment.timestamp}/> */}
                            </div>
                            <div className="d-flex my-3 w-50">
                                <h4 className="w-100">Message:</h4>
                                <textarea class="longInput" cols="95" rows="5" value={message} style = {{width: "750px"}}/>
                            </div>

                            
                            <div className="d-flex align-items-center mt-3 ">
                                {/* <a href="/comments?withStudents=true"> */}
                                    <button className="btn btn-secondary px-4 py-2" onClick={handleDelete}>DELETE</button>
                                {/* </a> */}
                            
                                {/* <button className="btn btn-secondary px-4 py-2" onClick={handleDelete} href="/comments?withStudents=true">DELETE</button> */}
                                {/* <Link to="/comments?withStudents=true" className="btn btn-secondary" onClick={handleDelete}>DELETE</Link> */}
                            </div>

                        </div>


                    </div>


                </div>
            
        );
    }

export default SingleComment;
