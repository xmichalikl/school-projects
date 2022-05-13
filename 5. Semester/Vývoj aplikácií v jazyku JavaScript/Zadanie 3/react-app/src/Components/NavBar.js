import React from "react";
import { Link } from "react-router-dom";

function NavBar() {
    return(
        <div className="nav">
            <ul>
                <li><Link to="/admin">Admin</Link></li>
                <li><Link to="/ad">Reklama</Link></li>
                <li><Link to="/">Domov</Link></li>
                <li><Link to="/cart">Košík</Link></li>
                
            </ul>
        </div>
    )
}

export default NavBar;

