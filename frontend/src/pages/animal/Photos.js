import 'bootstrap/dist/css/bootstrap.min.css';
import Sidebar from '../../components/Sidebar';
import AnimalNavbar from '../../components/AnimalNavbar';

import axios from 'axios';
import '../../styling/Comments.css';
import React, {useEffect, useState} from 'react'



function Photos() {


    const [currentFile, setFile] = useState(null)
    const [userID, setUserID] = useState(localStorage.getItem("userID"))
    const [animalID, setAnimalID] = useState(localStorage.getItem("animalID")) 
    const [animalPhotos, setAnimalPhotos] = useState([]);
    const [photoIDs, setPhotoIDs] = useState({});


    function GetPhotosForAnimal(){

        useEffect(()=>{
            axios.get('http://localhost:8080/app/photos/animal/'+animalID).then(
                res => {
                    console.log(res);
                    setAnimalPhotos(res.data);
                    console.log("animalPhotos.length = "+res.data.length)
                    console.log(
                        res.data
                        .map( (photo) => photo.photoID )
                      )
                    var currIDs = {}
                    res.data.map( (photo)=> {
                        currIDs["Photo"+photo.photoID] = false;})    
                    setPhotoIDs(currIDs);    
                    console.log(
                        Object.entries(photoIDs)
                        .map( ([key, value]) => `My key is ${key} and my value is ${value}` )
                      )      
                }
            )

            console.log("photoIDs keys:"+Object.keys(photoIDs))
            console.log(
                Object.entries(photoIDs)
                .map( ([key, value]) => `My key is ${key} and my value is ${value}` )
              )

        }, [])
    }

    function deletePhotos(){
        console.log("inside deletePhotos --> current photoIDs selected : ")
        console.log(photoIDs)
        console.log(Object.keys(photoIDs).filter((x) => photoIDs[x]))

        var selectedPhotos = Object.keys(photoIDs).filter((x) => photoIDs[x])
        selectedPhotos.map((photoName) => {
            axios.delete('http://localhost:8080/app/photos/'+photoName.replace("Photo",""))
            .catch(err =>{
                console.log(err)
            })
        })
        window.location.reload()        
    }
    

    
    

    function handleFile(e){
        let file = e.target.files[0];
        console.log(file)
        setFile(file)
    }

    function handleUpload(e){
        let file = currentFile;

        let formdata = new FormData()
        formdata.append('file', file)
        formdata.append('userID',userID)

        console.log(formdata)

        axios({
            method: "post",
            url: "http://localhost:8080/app/photos/animal/"+animalID+"/uploadphoto/", 
            data: formdata,
            headers: { "Content-Type": "multipart/form-data" },
          })

        window.location.reload()
    }

    function chkclick(e) {
        var selectedCheckboxName = e.target.name
        var updatedCheckedState = {}
        console.log("inside chkclick, photoIDs.length = "+Object.keys(photoIDs).length)
        console.log(photoIDs)
        console.log("selectedCheckboxName = "+selectedCheckboxName)
        for (var i=0; i<Object.keys(photoIDs).length; i++){
            let currKeyName = Object.keys(photoIDs).at(i)
            if (currKeyName === selectedCheckboxName){
                updatedCheckedState[currKeyName]=!photoIDs[currKeyName]
            }else{
                updatedCheckedState[currKeyName]=photoIDs[currKeyName]
            }
        }
        setPhotoIDs(updatedCheckedState);
        console.log(photoIDs)
    }




    return (
        
        <div className="main-container d-flex flex-column flex-grow-1">
            {GetPhotosForAnimal()}
            <div className="d-flex w-100 h-100">
                <div className="sidebar">
                    <Sidebar />
                </div>
                <div className="placeholder">
                    <Sidebar />
                </div>
                <div className= "d-flex flex-column w-100">
                    <AnimalNavbar />
                    <h1 className="ms-5 mt-5">Photos</h1>

                    <div className="ms-5">
                    <div className="d-flex flex-row w-75 mb-3 ms-5 justify-content-end">  
                        {/* <button className="btn btn-secondary p-2 ms-3" >Upload Photo</button> */}
                        <input className="btn btn-secondary px-2 ms-3" type="file" name="image" accept="image/png, image/jpeg" onChange={(e) => handleFile(e)}/>
                        <button className="btn btn-secondary px-2 ms-3" onClick={(e) => handleUpload(e)}>Upload Photo</button>
                        <button className="btn btn-secondary px-2 ms-3" onClick={()=> deletePhotos()}>Delete Photo</button>
                    </div>

                    <div class="ex2 mx-5 w-75">
                        <div className="d-flex flex-wrap mx-3 my-5 overflow-auto">
                            {animalPhotos.map(photo =>     
                                <div className = "mx-3"  key={"Photo"+photo.photoID}> 
                                    <input type="checkbox" name={"Photo"+photo.photoID} onChange={(e) => chkclick(e)}/>
                                    <div className = "mx-3" key={"Photo"+photo.photoID}>
                                        <img src={photo.filepath} alt="" style={{width:'150px',height:'125px'}}></img>
                                        {/* {console.log(photo.filepath)} */}
                                        <p>{photo.filepath.split("/").at(-1)}</p>
                                        {/* {console.log(photo.filepath.split("/").at(-1))} */}
                                    </div>
                                </div>
                            )}        
                        </div>
                        </div>


                        {/* <PhotosList animalID={animalID}/> */}
                        {/* <div class="d-flex flex-wrap mx-3 my-5">
                            <div class = "mx-3">
                                {console.log("animal image path = "+"\"../../images/photos/"+animalID+"/shiba1a.jpg\"")}
                                <img src={"/photos/"+animalID+"/shiba1a.jpg"} alt=""></img>
                                <p>Shiba1a.jpg</p>
                            </div>
                            <div class = "mx-3">
                                <img src="/photos/101/shiba1a.jpg" alt=""></img>
                                <p>Shiba1a.jpg</p>
                            </div>
                            <div class = "mx-3">
                            <img src={"/photos/"+animalID+"/shiba2a.jpg"} alt=""></img>
                                <p>shiba2a.jpg</p>
                            </div>
                            
                        </div> */}

                    </div>
                    




                </div>
            </div>   
        </div> 
    )
}
export default Photos;