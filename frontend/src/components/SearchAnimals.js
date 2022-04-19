import axios from 'axios';
import React, {Component} from "react"
import 'bootstrap/dist/css/bootstrap.min.css';

export default class SearchAnimals extends Component{

    state = {
        allAvailableAnimals:[],
        firstQuery:this.props.query,
        counter:0
    };

    //Get all animals
    getAnimals(){
        if (this.props.query !== null){
            axios.get('http://localhost:8080/app/animal/search?name='+this.props.query).then(
                res => {
                    console.log(res);
                    this.setState({allAvailableAnimals: res.data})
                }
            )
        }else{
            axios.get('http://localhost:8080/app/animal/').then(
                res => {
                    console.log(res);
                    this.setState({allAvailableAnimals: res.data})
                })
        }
        
    }
    
    //get all the animals from the back end api
    componentDidMount(){
        //test in console logs that the desired property was passed to this component
        console.log("mounted the searchAvailableAnimals")
        console.log(this.props.query)

        this.getAnimals()
    }


    render(){
        return(

            <div className="overflow-auto">
                {/* {Create a card for each animal} */}
                {this.state.allAvailableAnimals.map(animal =>
                    <a href = {"/animal-info?animalID="+animal.animalID} key={animal.animalID}>
                    <div className="card text-black bg-light my-3" key={animal.animalID} style={{width: "45rem"}}>
                        
                        <div className="card-header" >
                            <h6> Animal #{animal.animalID}: {animal.name}, {animal.breed} {animal.species} </h6>
                        </div>
                        <div className="card-body"  >
                        </div>
                        
                    </div>
                    </a>
                )}
            </div>
        )
    }

}