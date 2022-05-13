import React from 'react';
import { BrowserRouter as Router, Routes, Route, Link } from "react-router-dom";

import '../App.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import { Nav, Navbar } from 'react-bootstrap';

import logo from '../logo.png';
import Customer from './Customer';
import EconFly from './EconFly';
import BusiFly from './BusiFly';
import GladlyAbroad from './GladlyAbroad';


function Navigation() {
    return (
        <Router>
            <div className="Nav">
                <Navbar bg="dark" variant="dark" sticky="top">
                    <Navbar.Brand>
                        <img src={logo} />{' '}FlyNet
                    </Navbar.Brand>
                <Nav>
                    <Nav.Link as={Link} to="customer">Customer</Nav.Link>
                    <Nav.Link as={Link} to="econfly">EconFly</Nav.Link>
                    <Nav.Link as={Link} to="busifly">BusiFly</Nav.Link>
                    <Nav.Link as={Link} to="gladlyabroad">GladlyAbroad</Nav.Link>
                </Nav>
                </Navbar>
            </div>
            <Routes>
                <Route exact path="/customer" element={<Customer org="GA"/>}/>
                <Route exact path="/econfly" element={<EconFly org="EF"/>}/>
                <Route exact path="/busifly" element={<BusiFly org="BF"/>}/>
                <Route exact path="/gladlyabroad" element={<GladlyAbroad org="GA"/>}/>
            </Routes>
        </Router>
    );
}

export default Navigation;