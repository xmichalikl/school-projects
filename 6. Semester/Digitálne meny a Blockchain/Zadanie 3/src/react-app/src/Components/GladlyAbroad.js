import React from 'react';
import $ from 'jquery';
import { useState, useEffect } from 'react';
import { Container, Button, Form, Row, Col } from 'react-bootstrap';
import Swal from 'sweetalert2';

const reservationIdRegex = new RegExp('R[0-9]+');

function GladlyAboard(props) {
    const [passengersList, setPassengerList] = useState([]);
    const [reservationsList, setReservationsList] = useState([]);
    const [actualReservation, setActualReservation] = useState(null);
    const [availableSeats, setAvailableSeats] = useState([]);

    useEffect(() => {
        initPage();
    }, []);

    // --------------- PAGE INITIALIZATION ---------------
    async function initPage() {
        const data = {org: props.org};
        try {
            await fetch('http://localhost:8080/getAllReservations', {
                method: 'POST',
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(data)
            }).then(response => response.json())
            .then(resData => { // array of {} reservations
                if (resData.error) throw 'error';
                console.log(resData);
                const reservationsListTemp = [];
                resData.forEach(reservation => {
                    reservationsListTemp.push(reservation);
                });
                setReservationsList(reservationsListTemp);
            });
        }
        catch {
            Swal.fire({
                title: 'Cannot load reservations!',
                text: 'Server problem',
                icon: 'warning',
                confirmButtonText: 'OK',
                confirmButtonColor: '#0d6efd',
            }).then(() =>{
                console.log("NOT OK");
            });
        }
    }
    
    // --------------- GET ALL RESERVATIONS FROM SERVER ---------------
    async function isReservationIdValid(reservationId) {
        const foundReservation = reservationsList.find((reservation) => {
            return reservation.reservationNr === reservationId;
        });

        if (!foundReservation) {
            setActualReservation(null);
            setPassengerList([]);
            setAvailableSeats([]);
            return;
        }
        
        setActualReservation(foundReservation);
        setPassengerList(foundReservation.customerNames);

        const data = {
            org: props.org,
            id: foundReservation.flightNr
        }
        
        try {
            await fetch('http://localhost:8080/getFlight', {
                method: 'POST',
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(data)
            }).then(response => response.json())
            .then(resData => { // {} flight
                if (resData.error) throw 'error';
                setAvailableSeats(resData.seats.reduce((prevVal, currVal, currIndex) => {
                    if (currVal === 'free') prevVal.push(currIndex);
                    return prevVal;
                }, []));
            });
        }
        catch {
            Swal.fire({
                title: 'Cannot load flight seats!',
                text: 'Server problem',
                icon: 'warning',
                confirmButtonText: 'OK',
                confirmButtonColor: '#0d6efd',
            }).then(() =>{
                console.log("NOT OK");
            });
        }
    }
    
    function resetForm() {
        $('#form-grid-reservation-id').val('');
        setActualReservation(null);
        setAvailableSeats([]);
        setPassengerList([]);
    }

    function isCheckInValid() {
        const pickedSeats = [];
        const reservationId = $('#form-grid-reservation-id').val();
        for (const [index, passenger] of passengersList.entries()) {
            if ($(`.passenger-${index} #form-grid-name`).val().length == 0) return [false, 'Name', index];
            if ($(`.passenger-${index} #form-grid-surname`).val().length == 0) return [false, 'Surname', index];
            if ($(`.passenger-${index} #form-grid-passport-id`).val().length == 0) return [false, 'Passport ID', index];
            if ($(`.passenger-${index} #form-grid-seat-num`).val() == 'Choose...') return [false, 'Seat', index];
            if (pickedSeats.indexOf($(`.passenger-${index} #form-grid-seat-num`).val()) >= 0) return [false, 'Seat', -1];
            pickedSeats.push($(`.passenger-${index} #form-grid-seat-num`).val());
        };
        if (reservationId.length == 0) return [false, 'Reservation ID', -2];
        if (actualReservation == null) return [false, 'Reservation ID', -3];
        return [true, '', 0];
    }

    function createAlertMessage(flag) {
        switch(flag) {
            case -1:
                return `All ${validCheckIn[1]} fields must be unique!`;
            case -2:
                return `${validCheckIn[1]} field missing!`;
            case -3:
                return `Invalid ${validCheckIn[1]}!`;
            default:
                return `Passenger ${validCheckIn[2]+1} - ${validCheckIn[1]} field missing!`;  
        }
    }

    async function sendData() {
        const validCheckIn = isCheckInValid();
        if (!validCheckIn[0]) {
            Swal.fire({
                title: 'Incomplete form!',
                text: createAlertMessage(validCheckIn[2]),
                icon: 'warning',
                confirmButtonText: 'OK',
                confirmButtonColor: '#0d6efd',
            });
            return;
        }

        const seats = [];
        const passportIDs = [];

        passengersList.forEach((passenger, index) => {
            seats.push($(`.passenger-${index} #form-grid-seat-num`).val());
            passportIDs.push($(`.passenger-${index} #form-grid-passport-id`).val());
        });

        const data = {
            org: props.org,
            reservationNr: actualReservation.reservationNr,
            passportIDs: passportIDs,
            seats: seats
        };

        try {
            await fetch('http://localhost:8080/checkIn', {
                method: 'POST',
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(data)
            }).then(response => response.json())
            .then(resData => { // {message: 'Random message'}
                if (resData.error) throw 'error';
                let resMsg = resData.message;
                //let x = resMsg.split(' ')[1];

                Swal.fire({
                    title: `${actualReservation.reservationNr} check in successful!`,
                    text: resMsg,
                    icon: 'success',
                    confirmButtonText: 'OK',
                    confirmButtonColor: '#0d6efd',
                }).then(resetForm());
            });
        }
        catch {
            Swal.fire({
                title: 'Cannot check in reservation!',
                text: 'Server error',
                icon: 'error',
                confirmButtonText: 'OK',
                confirmButtonColor: '#0d6efd',
            }).then(() =>{
                resetForm();
                console.log("NOT OK");
            });
        }

        


    }


    return(
        <Container id="main-container" className="d-grid w-100">
            <div id="title" className="text-center mb-4">
                <h1>Check in</h1>
            </div>

            <Form id="reservation-form" className="w-100"> 
                <Form.Group className="mb-5" controlId="form-grid-reservation-id">
                    <Form.Label>Reservation ID</Form.Label>
                    <Form.Control type="text" placeholder="Enter reservation ID"
                    onChange={(e) => {isReservationIdValid(e.target.value)}}/>
                </Form.Group>

                {passengersList.map((passenger, index) => (
                    <div className={ `passenger-${index}` } key={index}>
                        <h3 className="text-center" >Passenger {index+1}</h3>
                        <Form.Group className="mb-2" controlId="form-grid-name">
                            <Form.Label>Name</Form.Label>
                            <Form.Control type="text" placeholder="Enter name"
                            defaultValue={passenger.split(' ')[0]}/>
                        </Form.Group>

                        <Form.Group className="mb-2" controlId="form-grid-surname">
                            <Form.Label>Surname</Form.Label>
                            <Form.Control type="text" placeholder="Enter surname"
                            defaultValue={passenger.split(' ')[1]}/>
                        </Form.Group>

                        <Form.Group className="mb-2" controlId="form-grid-passport-id">
                            <Form.Label>Passport ID</Form.Label>
                            <Form.Control type="text" placeholder="Enter passport ID"/>
                        </Form.Group>

                        <Form.Group className="mb-5" controlId="form-grid-seat-num">
                            <Form.Label>Seat</Form.Label>
                            <Form.Select defaultValue="Choose...">
                            <option>Choose...</option>
                            { availableSeats.map((freeSeat, index) => (
                                <option key={index} value={freeSeat}>{freeSeat}</option>
                            )) }
                            </Form.Select>
                        </Form.Group>
                    </div>
                ))}

                <div className="d-grid">
                    <Button onClick={sendData}>
                        Submit
                    </Button>        
                </div>
            </Form>
        </Container>
    )
}
export default GladlyAboard; 