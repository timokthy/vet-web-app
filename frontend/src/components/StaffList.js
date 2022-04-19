import axios from 'axios';
import React, {Component} from "react"
import 'bootstrap/dist/css/bootstrap.min.css';

export default class StaffList extends Component{
    
    state = {
        staffList: [],
        selectedStaff: [],
        currUserType: localStorage.getItem("userType"),
        currUserID: localStorage.getItem("userID")
    };

    // Gets a list of Animal Health Technicians
    componentDidMount(){
        axios.get('http://localhost:8080/app/user/userType=Animal%20Health%20Technician').then(
            res => {
                this.setState({staffList: res.data})
            }
        )
    }

    handleChange = (e) => {
        let value = Array.from(e.target.selectedOptions, option => option.value);
        this.setState({selectedStaff: value});
    }

    // creates a select menu for Animal Health Technicians
    render(){
        return(
            <div>
                <select class="form-select" size='5' multiple aria-label="staffSelection" onChange={this.handleChange} value={this.state.selectedStaff}>
                    {this.state.staffList.map(staff => 
                        <option value = {staff.id} >{staff.firstName} {staff.lastName}</option>
                        )
                    }
                </select>
            </div>
        )
    }
}