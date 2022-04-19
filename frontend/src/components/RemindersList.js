import axios from 'axios';
import React, {Component} from "react"
import 'bootstrap/dist/css/bootstrap.min.css';


export default class RemindersList extends Component{
    
    state = {
        reminders: [],
        animalID : this.props.animalID,
        currUserType: this.props.usertype,
    };

    //get all the reminders for one animal
    componentDidMount(){
        console.log(this.state.toggleStudent)
        axios.get('http://localhost:8080/app/reminders/animal/'+this.state.animalID).then(
            res => {
                console.log(res);
                this.setState({reminders: res.data})
            }
        )
    }

    //delete one reminder when the button is clicked
    deleteReminder(event){
        event.preventDefault();
        var currReminderID = event.target.getAttribute('reminderid')
        // console.log(event.target.getAttribute('reminderID'))

        axios.delete('http://localhost:8080/app/reminders/'+ currReminderID)
        .catch(err =>{
            console.log(err)
        })
        
        window.location.reload()
    }


    render(){
        return(
            <div className="overflow-auto">
                {/* {Load all the reminders as cards} */}
            {this.state.reminders.map(reminder => 
                <div class="card bg-light mx-5 my-3" key={reminder.commentId} style={{width: "50rem"}}>
                <div class="card-header" >
                    <div class="d-flex justify-content-between">
                        <div>
                            {reminder.firstName} {reminder.lastName}, {reminder.userType}
                        </div>
                        {/* {Delete button on the reminder card only appears for users who are not Students} */}
                        { this.state.currUserType !== "Student" ?
                            <div class="d-flex mx-5 ">
                                <button class="btn btn-warning" reminderid={reminder.reminderID} onClick={(e) => this.deleteReminder(e)} >Delete</button>
                            </div>:null
                        }        
                    </div>    

                </div>
                <div class="card-body"  >
                    <p class="card-text">
                        {reminder.note}
                    </p>
                    <div class="d-flex w-100">
                        <div > 
                            Date Created: {reminder.dateCreated.replace(" 00:00:00","")}
                        </div>
                    </div>
                </div>
            </div>
            )}
            </div>
        )

    }


}