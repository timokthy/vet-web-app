import "../styling/Sidebar.css";
import { Link } from "react-router-dom";
import { ProSidebar, Menu, MenuItem, SidebarHeader, SidebarContent, SidebarFooter } from "react-pro-sidebar";
import { FaHome, FaDog, FaHeart, FaUser, FaPaperPlane } from "react-icons/fa";
import { IoLogOut } from "react-icons/io5";
import { AiFillPlusCircle, AiFillFolder } from "react-icons/ai";
import ucvmLogo from "../images/ucvm-logo-sm.png";
import jwt_decode from "jwt-decode";
import { useState } from 'react';

function Sidebar() {
    const headerStyle = {
        padding: "24px",
        textTransform: "uppercase",
        fontWeight: "bold",
        letterSpacing: "1px",
        overflow: "hidden",
        whiteSpace: "noWrap"
    };

    // JWT token settings
    let type = ""

    try {
        let token = localStorage.getItem("token")
        let decoded = jwt_decode(token);
        type = decoded.sub
    } catch {
        window.location.reload()
        let token = localStorage.getItem("token")
        let decoded = jwt_decode(token);
        type = decoded.sub
    }
    
    const [userType, setType] = useState(type)
  
    // Sidebar options that display based on user type
    function manageUser(){
        if (userType === "Admin") {
            return <MenuItem icon={<FaUser />}>
                    Manage Users
                    <Link to="/add-user" />
                </MenuItem>
        }
    }

    function showRequests(){
        if (userType === "Admin" || userType === "Animal Health Technician"  || userType === "Teaching Techncian"){
        return <MenuItem icon={<FaPaperPlane />}>
                View Requests
                <Link to="/view-requests" />
            </MenuItem>
        }
    }

    return (
        // Sidebar and associated elements
        <ProSidebar>
            <SidebarHeader style={headerStyle}>
                <img src={ucvmLogo} alt="U of C Vet Med Logo"></img>
            </SidebarHeader>

            <SidebarContent>
                <Menu iconShape="circle">
                <MenuItem icon={<FaHome />}>
                    Home
                    <Link to="/home" />
                </MenuItem>
                <MenuItem icon={<AiFillPlusCircle />}>
                    Add Animal
                    <Link to="/add-animal" />
                </MenuItem>
                <MenuItem icon={<FaDog />}>
                    Manage Animals
                    <Link to="/manage-animals" />
                </MenuItem>
                {showRequests()}
                {manageUser()}
                <MenuItem icon={<IoLogOut />}>
                    Sign Out
                    <Link to="/" />
                </MenuItem>

                </Menu>
            </SidebarContent>

            <SidebarFooter style={{ textAlign: "center" }}>
                <p class="my-1" style={{fontSize: 14}}>Made with <FaHeart /> by</p>
                <p class="my-0" style={{fontSize: 10}}>Timothy Mok</p>
                <p class="my-0" style={{fontSize: 10}}>Aron Saengchan</p>
                <p class="mb-1" style={{fontSize: 10}}>Jimmy Zhu</p>
            </SidebarFooter>
        </ProSidebar>
  );
}

export default Sidebar;