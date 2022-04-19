import "../styling/Navbar.css";
import DropdownButton from 'react-bootstrap/DropdownButton'
import Dropdown from 'react-bootstrap/Dropdown'

const UserNavbar = () => {
    let userInfo = {
        "userFirstName": localStorage.getItem("userFirstName"),
        "userLastName": localStorage.getItem("userLastName"),
    }

    // Displays navbar for admins accessing user management
    return (
        <div className="navbar d-flex justify-content-start ps-5 pe-4 pt-4 pb-4">
            <div className="d-flex flex-column justify-content-start mt-3">
                <h3>User Management</h3>
                <p className="mt-3">{userInfo.userFirstName} {userInfo.userLastName}</p>
                <p className="admin-text">System Administrator</p>
            </div>

            {/* Dropdownmenu to add, edit, or block users */}
            <div className="px-3 py-2">
                <DropdownButton id="dropdown-basic-button" title="Select an option..." variant="dark">
                    <Dropdown.Item href="/add-user">Add User</Dropdown.Item>
                    <Dropdown.Item href="/edit-user">Edit User</Dropdown.Item>
                    <Dropdown.Item href="/block-user">Block User</Dropdown.Item>
                </DropdownButton>
            </div>
        </div>
    )
}

export default UserNavbar