import "../styling/Navbar.css";

const AnimalNavbar = () => {
    // Display of a navbar with no dropdown menu
    return (
        <div className="navbar d-flex justify-content-start">
            <div className="d-flex flex-column justify-content-start align-items-center py-4 animal-info mx-5">
                <h3 className="mt-2">{localStorage.getItem("animalName")}</h3>
                <p className="species-text">{localStorage.getItem("animalSpecies")}</p>
                <p className="status-text px-5">{localStorage.getItem("animalStatus")}</p>
            </div>
            <div></div>
        </div>
    )
}

export default AnimalNavbar