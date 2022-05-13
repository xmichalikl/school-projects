import React from 'react';
import { useState, useEffect } from 'react';
import { Container, Button, Form, Row, Col } from 'react-bootstrap';

import $ from 'jquery';
import Swal from 'sweetalert2';

const emailRegex = new RegExp('[a-z0-9]+@[a-z]+\.[a-z]{2,3}');
const filledPassengerIndexes = [];

function Customer(props) {
    const [flightsList, setflightsList] = useState([]);
    const [passengersList, setPassengersList] = useState([
        /*{name: "Janko", surname: "Hrasko", passportID: "123456"},
        {name: "Marienka", surname: "Hraskova", passportID: "654321"},
        {name: "", surname: "", passportID: ""}*/
    ]);

    useEffect(() => {
        initPage();
    }, []);


    // --------------- ALERT MESSAGE ---------------
    function createAlertMessage(data) {
        const alertMsgGlobal = document.createElement("div");
        const alertMsgPassengers = document.createElement("div");
        const alertMsgCustomer = document.createElement("div");

        data.passengers.forEach((passenger, index) => {
            let row = document.createElement("p");
            let passengerStr = Object.values(passenger).join(' ');
            row.innerHTML = `<b>Passenger ${index+1}:</b> ${passengerStr}`;
            alertMsgPassengers.appendChild(row);
        });

        const flightNrRow = document.createElement("p");
        flightNrRow.innerHTML = `<b>Flight ID:</b> ${data.flightNr}`;
        alertMsgCustomer.appendChild(flightNrRow);

        const emailRow = document.createElement("p");
        emailRow.innerHTML = `<b>Email:</b> ${data.email}`;
        alertMsgCustomer.appendChild(emailRow);

        alertMsgGlobal.appendChild(alertMsgCustomer)
        alertMsgGlobal.appendChild(alertMsgPassengers)

        return alertMsgGlobal;
    }

    // --------------- PAGE INITIALIZATION ---------------
    async function initPage() {
        updateFilledPassengers(0);
        setPassengersList([{name: "", surname: "", passportID: ""}]);

        const data = {org: props.org};
        try {
            await fetch('http://localhost:8080/getAllFlights', {
                method: 'POST',
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(data)
            }).then(response => response.json())
            .then(resData => { // array of {} flights
                if (resData.error) throw 'error';
                const flightsListTemp = [];
                resData.forEach(flight => {
                    flightsListTemp.push(flight.flightNr);
                });
                setflightsList(flightsListTemp);
            });
        }
        catch {
            Swal.fire({
                title: 'Cannot load flights!',
                text: 'Server problem',
                icon: 'warning',
                confirmButtonText: 'OK',
                confirmButtonColor: '#0d6efd',
            }).then(() =>{
                console.log("NOT OK");
            });
        }
    }

    // --------------- FORMAT CHECK ---------------
    function isEmailValid() {
        const email = $('#form-grid-email').val();
        return emailRegex.test(email);
    }
    function isFlightValid() {
        const flightNum = $("#form-grid-flight-num").val();
        return (flightNum != 'Choose...');
    }
    function isDataValid() {
        if (passengersList.length > 1) {
            const passengersListTemp = [...passengersList];
            const lastPassenger = passengersListTemp.splice(-1, 1)[0];
            if (isAllFilled(passengersListTemp) && isEmpty(lastPassenger)) {
                return true;
            }
        }
        return false;
    }

    // --------------- PASSENGER FIELDS CHECK ---------------
    function isAllFilled(passengersList) {
        return passengersList.every(passenger => {
            if (isFilled(passenger)) return true;
            return false;
        });
    }
    function isFilled(passenger) {
        return Object.values(passenger).every(value => {
            if (value.length > 0) return true;
            return false;
        });
    }
    function isEmpty(passenger) {
        return Object.values(passenger).every(value => {
            if (value.length == 0) return true;
            return false;
        });
    } 
    function isLast(index, length) {
        return (index == length-1);
    }

    // --------------- SEND ORDER DATA TO SERVER ---------------
    async function sendData(e) {
        e.preventDefault();

        if (!isDataValid() || !isEmailValid() || !isFlightValid()) {
            Swal.fire({
                title: 'Incomplete form!',
                text: 'Please check fields',
                icon: 'warning',
                confirmButtonText: 'OK',
                confirmButtonColor: '#0d6efd',
            })
            return;
        }
       
        const passengersListTemp = [...passengersList];
        passengersListTemp.splice(-1, 1);
        
        const data = {
            org: props.org,
            email: $('#form-grid-email').val(),
            flightNr: $("#form-grid-flight-num").val(),
            passengers: passengersListTemp
        };
        
        try {
            await fetch('http://localhost:8080/reserveSeats', {
                method: 'POST',
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(data)
            }).then(response => response.json())
            .then(resData => { // {message: 'Random message'}
                if (resData.error) throw 'error';
                let resMsg = resData.message;
                let reservationNum = resMsg.split(' ')[1];
                
                Swal.fire({
                    title: `Reservation ${reservationNum} was created!`,
                    html: createAlertMessage(data),
                    icon: 'success',
                    confirmButtonText: 'OK',
                    confirmButtonColor: '#0d6efd',
                }).then(() =>{
                    initPage();
                    resetForm();
                    console.log("OK");
                });
            });
        }
        catch {
            Swal.fire({
                title: 'Cannot create reservation!',
                text: 'Server error',
                icon: 'error',
                confirmButtonText: 'OK',
                confirmButtonColor: '#0d6efd',
            }).then(() =>{
                initPage();
                resetForm();
                console.log("NOT OK");
            });
        }
    }
    
    // --------------- UPDATE PASSENGERS DATA ---------------
    function resetForm() {
        passengersList.forEach((passenger, index) => {
            $(`.passenger-${index} #form-grid-name`).val('');
            $(`.passenger-${index} #form-grid-surname`).val('');
            $(`.passenger-${index} #form-grid-passport-id`).val('');
        });
        $('#form-grid-email').val('');
        $("#form-grid-flight-num").val('');
    }
    function fillForm(updatedPassengersList) {
        updatedPassengersList.forEach((passenger, index) => {
            $(`.passenger-${index} #form-grid-name`).val(passenger.name);
            $(`.passenger-${index} #form-grid-surname`).val(passenger.surname);
            $(`.passenger-${index} #form-grid-passport-id`).val(passenger.passportID);
        });
    }
    function updateFilledPassengers(passengersLen) {
        while (filledPassengerIndexes.length > 0) {
            filledPassengerIndexes.pop();
        }
        for (let i = 0; i < passengersLen; i++) {
            filledPassengerIndexes.push(i);
        }
    }
    function updatePassengerData(index, key, value) {
        const updatedPassengersList = [...passengersList];
        const passengersLen = passengersList.length;

        const lastPassenger = passengersList.at(passengersLen-1);
        const actualPassenger = passengersList[index];
        actualPassenger[key] = value;

        console.log(passengersList);
        console.log(filledPassengerIndexes);

        // Create passenger fileds
        if (isAllFilled(passengersList) && !filledPassengerIndexes.includes(index)) {
            updateFilledPassengers(filledPassengerIndexes.length+1);
            updatedPassengersList.push({name: "", surname: "", passportID: ""});
            setPassengersList(updatedPassengersList);
            return;
        }
        // Delete passenger fileds
        if (!isFilled(actualPassenger)) { 
            if (filledPassengerIndexes.includes(index)) {
                filledPassengerIndexes.splice(index, 1);
            }
            if (isEmpty(lastPassenger) && !isLast(index, passengersLen)) {
                updatedPassengersList.splice(-1, 1);
                setPassengersList(updatedPassengersList);
                console.log("last " + index);
                return;
            }
            if (isEmpty(actualPassenger) && !isLast(index, passengersLen)) {
                updatedPassengersList.splice(index, 1);
                updateFilledPassengers(filledPassengerIndexes.length);
                setPassengersList(updatedPassengersList);
                fillForm(updatedPassengersList);
                console.log("active " + index);
                return;
            }
            if (isEmpty(actualPassenger) && isLast(index, passengersLen)) {
                const passengersListTemp = [...passengersList];
                passengersListTemp.splice(index, 1)

                if (passengersLen > 1 && !isAllFilled(passengersListTemp)) {
                    updatedPassengersList.splice(-1, 1);
                    setPassengersList(updatedPassengersList);
                    console.log("lastt " + index);
                    return;
                }
            }
        }
    }

    return(
        <Container id="main-container" className="d-grid w-100">
            <div id="title" className="text-center mb-4">
                <h1>Create new reservation</h1>
            </div>

            <Form id="reservation-form" className="w-100"> 
                {passengersList.map((singlePassenger, index) => (
                    <div className={ `passenger-${index}` } key={index}>
                        <h3 className="text-center" >Passenger {index+1}</h3>
                        <Form.Group className="mb-2" controlId="form-grid-name">
                            <Form.Label>Name</Form.Label>
                            <Form.Control type="text" placeholder="Enter name" 
                                defaultValue={singlePassenger.name} onChange={(e) => {
                                updatePassengerData(index, 'name', e.target.value)
                            }}/>
                        </Form.Group>

                        <Form.Group className="mb-2" controlId="form-grid-surname">
                            <Form.Label>Surname</Form.Label>
                            <Form.Control type="text" placeholder="Enter surname" 
                                defaultValue={singlePassenger.surname} onChange={(e) => {
                                updatePassengerData(index, 'surname', e.target.value)
                            }}/>
                        </Form.Group>

                        <Form.Group className="mb-5" controlId="form-grid-passport-id">
                            <Form.Label>Passport ID</Form.Label>
                            <Form.Control type="text" placeholder="Enter passport ID" 
                                defaultValue={singlePassenger.passportID} onChange={(e) => {
                                updatePassengerData(index, 'passportID', e.target.value)
                            }}/>
                        </Form.Group>
                    </div>
                ))}
                
                <div className="text-center mb-4">
                    <h3>Customer</h3>
                </div>
                <Form.Group className="mb-2" controlId="form-grid-email">
                    <Form.Label>Email</Form.Label>
                    <Form.Control type="email" placeholder="Enter your email" />
                </Form.Group>

                <Form.Group className="mb-5" controlId="form-grid-flight-num">
                    <Form.Label>Flight ID</Form.Label>
                    <Form.Select defaultValue="Choose...">
                    <option>Choose...</option>
                    { flightsList.map((flightNr) => (
                        <option key={flightNr} value={flightNr}>{flightNr}</option>
                    )) }
                    </Form.Select>
                </Form.Group>
                
                <div className="d-grid">
                    <Button onClick={sendData}>
                        Submit
                    </Button>        
                </div>
            </Form>
        </Container>
    )
}
export default Customer; 