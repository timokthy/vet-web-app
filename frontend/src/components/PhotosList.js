import axios from 'axios';
import React, {Component} from "react"
import 'bootstrap/dist/css/bootstrap.min.css';

export default class PhotosList extends Component{
    state = {
        photoPaths: [],
        photoIDs: {},
        animalID : this.props.animalID
    };

    //Get all the photos for one animal
    componentDidMount(){
        console.log(this.state.animalID)
        console.log('http://localhost:8080/app/photos/animal/'+this.state.animalID)
        axios.get('http://localhost:8080/app/photos/animal/'+this.state.animalID).then(
            res => {
                console.log(res);
                this.setState({photoPaths: res.data})
            }
        )
    }

    //update the state when a photo is select
    chkclick = (e) => {
        var {name, checked} = e.target;

        this.setState( (e)=>{
            var Selectedsphoto = e.photoIDs;
            return Selectedsphoto[name]= checked;
            // Selectedsphoto[name]= checked;
        });
    }


    render(){
        return(
            <div className="d-flex flex-wrap mx-3 my-5 overflow-auto">
            {/* {Load in each photo} */}
            {this.state.photoPaths.map(photo =>     
                <div class = "mx-3"> 
                    <input type="checkbox" name={"Photo"+photo.photoID} onChange={this.chkclick}/>
                    <div class = "mx-3">
                        <img src={photo.filepath} alt=""></img>
                        {console.log(photo.filepath)}
                        <p>{photo.filepath.split("/").at(-1)}</p>
                        {console.log(photo.filepath.split("/").at(-1))}
                    </div>
                </div>
            )}        
            </div>
        )}

}