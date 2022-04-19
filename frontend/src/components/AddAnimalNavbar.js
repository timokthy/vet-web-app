import "../styling/Navbar.css";

const AddAnimalNavbar = () => {
    return (
        // Contents of the animal navbar
        <div className="navbar-no-margin d-flex justify-content-start ps-5 pe-4 pt-4 pb-3">
            <div className="d-flex flex-column justify-content-start mt-3 animal-info">
                <h3 className="mt-4 pb-3">Welcome, New Friend!</h3>
                <p className="admin-text pb-4">Please enter the animal's information below...</p>
            </div>


        </div>
    )
}

export default AddAnimalNavbar
