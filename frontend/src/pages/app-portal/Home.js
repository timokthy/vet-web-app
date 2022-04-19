import 'react-pro-sidebar/dist/css/styles.css';
import '../../styling/Main.css';
import Sidebar from "../../components/Sidebar";
import ucvmLogo from "../../images/ucvm-logo-lg.png";
import { useState } from 'react';
import jwt_decode from "jwt-decode";

function Home() {
    const [datetime, setDatetime] = useState(new Date());
    const months= ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"];

    let token = localStorage.getItem("token")
    let decoded = jwt_decode(token)
    
    let userInfo = {
        "userID":localStorage.getItem("userID"),
        "userFirstName":localStorage.getItem("userFirstName"),
        "userLastName":localStorage.getItem("userLastName"),
        "userType":decoded.sub,
    }
    

    // Displays the time on the screen
    function time() {
        time = "";

        if (datetime.getHours() == 0) {
            time += "12:";
        } else if (datetime.getHours() > 12) {
            time += (datetime.getHours() - 12) + ":";
        } else {
            time += datetime.getHours() + ":";
        }

        if (datetime.getMinutes() < 10) {
            time += "0" + datetime.getMinutes();
        } else {
            time += datetime.getMinutes();
        }

        if (datetime.getHours() < 12) {
            time += " am";
        } else {
            time += " pm";
        }

        return time;
    }

    // Displays the homepage to the user
    return (
        <div className="main-container d-flex flex-column flex-grow-1">
            <div className="d-flex w-100 h-100">
                <div className="sidebar">
                    <Sidebar />
                </div>

                <div className="placeholder">
                    <Sidebar />
                </div>

                <div className="vertical-center d-flex flex-column align-items-center">
                    <img src={ucvmLogo} alt="U of C Vet Med Logo" className="pb-5"></img>

                    <h1>Welcome, {userInfo.userFirstName} {userInfo.userLastName}!</h1>
                    <h4 className="fst-italic pb-5">{userInfo.userType}</h4>

                    <h1 className="pt-2">{time()}</h1>
                    <h4>{`${months[datetime.getMonth()]} ${datetime.getDate()}, ${datetime.getFullYear()}`}</h4>

                </div>
            </div>
        </div>
            
        );
}

export default Home;

