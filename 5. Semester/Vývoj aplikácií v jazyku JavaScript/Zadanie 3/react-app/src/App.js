import React from "react";
import { BrowserRouter as Router, Routes, Route, Link } from "react-router-dom";

import NavBar from "./Components/NavBar.js";
import Home from "./Components/Home.js"
import Admin from "./Components/Admin.js"
import Cart from "./Components/Cart.js"
import Ad from "./Components/Ad.js"

function App() {
    return(
        <Router>
            <NavBar/>
            <Routes>    
                <Route exact path="/" element={<Home/>}/>
                <Route exact path="/admin" element={<Admin/>}/>
                <Route exact path="/cart" element={<Cart/>}/>
                <Route exact path="/ad" element={<Ad/>}/>
            </Routes>
        </Router>
    )
}
export default App; 