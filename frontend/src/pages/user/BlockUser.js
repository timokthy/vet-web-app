import '../../styling/Main.css';
import Sidebar from '../../components/Sidebar';
import UserNavbar from '../../components/UserNavbar';
import { useState } from 'react';
import axios from 'axios';

function BlockUser() {
    const [userId, setUserId] = useState();

    // Sends request to block a user in the database
    function handleSubmit(event) {
        event.preventDefault();
        axios.put("http://localhost:8080/app/user/block-user/" + userId);

        alert("User blocked")
        window.location.reload()
    }
    
    // Form to block a user
    return (
        <div className="d-flex w-100 h-100">
            <div className="sidebar">
                <Sidebar />
            </div>

            <div className="placeholder">
                <Sidebar />
            </div>

            <div className="main-container d-flex flex-column flex-grow-1">
                <UserNavbar />

                <h1 className="mt-5 ms-5">Block User</h1>
                    <form className="d-flex flex-column  align-items-start mt-5" onSubmit={handleSubmit}>
                        <div className="d-flex my-3 w-50">
                            <h5 className="w-100">User ID:</h5>
                            <input className="form-control" value={userId} onChange={e => setUserId(e.target.value)}/>
                        </div>

                        <div className="d-flex w-50">
                                <div className="w-50 mx-5"></div>
                                <button className="btn btn-secondary px-4 py-2 mx-5 my-3" type="submit">Submit</button>
                        </div>
                    </form>
            </div>
        </div>  
    );
}

export default BlockUser;