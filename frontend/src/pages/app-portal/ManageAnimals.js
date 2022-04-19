import { useState } from 'react';
import '../../styling/Main.css';
import Sidebar from '../../components/Sidebar';
import ManageAnimalsNavbar from '../../components/ManageAnimalsNavbar';
import SearchAnimals from '../../components/SearchAnimals';
import {useLocation, useNavigate} from 'react-router-dom'


function ManageAnimal() {
    const urlParams = new URLSearchParams(useLocation().search)
    let navigate = useNavigate();

    let urlQuery = ""
    try {
        urlQuery = urlParams.get("query")
    }catch(error){
        console.log("--first visit to page will have no search param")
    }
    const [searchQuery, setSearchQuery] = useState(urlQuery);

    function reloadSearches(e){
        e.preventDefault();
        console.log("searchQuery = "+searchQuery)
        navigate(`/manage-animals?query=`+searchQuery)
        window.location.reload() 
    }


    return (
        <div className="d-flex w-100 h-100">
        <div className="sidebar">
            <Sidebar />
        </div>

        <div className="placeholder">
            <Sidebar />
        </div>

        <div className="main-container d-flex flex-column flex-grow-1">
            <ManageAnimalsNavbar />

            <h1 className="mt-5 mb-4 ms-5">Search Animal</h1>

            <form className="d-flex flex-row mx-5 w-75" onSubmit={(e) => reloadSearches(e)}>
                <input type="text" className="form-control me-2" size="100" placeholder="Search an animal..." value={searchQuery} onChange={e => setSearchQuery(e.target.value)} />
                <button className="btn btn-secondary" type="submit">Search</button>
            </form> 
            <div className="ex1 mt-3 mx-5 w-75 ps-5">
                {searchQuery === ""?
                    null:<SearchAnimals query={searchQuery}/>   
                }
            </div>


        </div>
    
    </div>
        );
    }

export default ManageAnimal;
