import React from "react";
import { useState, useEffect } from 'react';
import { Modal, Container, Button, Form, Row, Col } from 'react-bootstrap';

import $ from 'jquery';
import Swal from 'sweetalert2';

function EconFly(props) {
    const [reservationsList, setReservationsList] = useState([]);

    useEffect(() => {
        initPage();
        resetForm();
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

    function resetForm() {
        $('#form-grid-from').val('')
        $('#form-grid-to').val('')
        $('#form-grid-date').val('')
        $('#form-grid-time').val('')
        $('#form-grid-seats').val('')
        $('#form-grid-reservation-id').val('Choose...');
    }

    // --------------- ACTUAL DATE & TIME --------------
    function actualDate() {
        const today = new Date();

        let dd = today.getDate();
        let mm = today.getMonth()+1; 
        let yyyy = today.getFullYear();

        if (dd < 10) dd = '0' + dd;
        if (mm < 10) mm = '0' + mm;

        return (yyyy+"-"+mm+"-"+dd);
    }
    function actualTime() {
        const today = new Date();

        let hh = today.getHours();
        let mm = today.getMinutes(); 

        if (hh < 10) hh = '0' + hh;
        if (mm < 10) mm = '0' + mm;

        return (hh+":"+mm);
    }

    // --------------- FORMAT CHECK ---------------
    function formatDateTime(date, time) {
        return date+"-"+time.split(':').join('-');
    }
    function isFlightValid() {
        if ($('#form-grid-from').val().length == 0) return [false, 'Origin'];
        if ($('#form-grid-to').val().length == 0) return [false, 'Destination'];
        if ($('#form-grid-date').val().length == 0) return [false, 'Date'];
        if ($('#form-grid-time').val().length == 0) return [false, 'Time'];
        if ($('#form-grid-seats').val().length == 0) return [false, 'Seats'];
        return [true, ''];
    }
    
    // --------------- SEND ORDER DATA TO SERVER ---------------
    async function sendData(e) {
        const validFlight = isFlightValid();
        if (!validFlight[0]) {
            Swal.fire({
                title: 'Incomplete form!',
                text: validFlight[1]+" field missing",
                icon: 'warning',
                confirmButtonText: 'OK',
                confirmButtonColor: '#0d6efd',
            })
            return;
        }

        const date = $('#form-grid-date').val();
        const time = $('#form-grid-time').val();
    
        const data = {
            org: props.org,
            flyFrom: $('#form-grid-from').val(),
            flyTo: $('#form-grid-to').val(),
            dateTime: formatDateTime(date, time),
            availablePlaces: $('#form-grid-seats').val()
        };

        try {
            await fetch('http://localhost:8080/createFlight', {
                method: 'POST',
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(data)
            }).then(response => response.json())
            .then(resData => { // {message: 'Random message'}
                if (resData.error) throw 'error';
                let resMsg = resData.message;
                let flightNum = resMsg.split(' ')[1];
    
                Swal.fire({
                    title: `Flight ${flightNum} was created!`,
                    html: `
                        <p><b>From</b>: ${data.flyFrom} -> <b>To:</b> ${data.flyTo}</p>
                        <p><b>Date:</b> ${date}</p><p><b>Time:</b> ${time}</p>
                        <p><b>Seats:</b> ${data.availablePlaces}</p>
                    `,
                    icon: 'success',
                    confirmButtonText: 'OK',
                    confirmButtonColor: '#0d6efd',
                }).then(resetForm());
            });
        }
        catch {
            Swal.fire({
                title: 'Cannot create flight!',
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

    async function bookSeats() {
        const reservationId = $('#form-grid-reservation-id').val();

        if (reservationId == 'Choose...') {
            Swal.fire({
                title: 'Incomplete form!',
                text: "Reservation ID field missing",
                icon: 'warning',
                confirmButtonText: 'OK',
                confirmButtonColor: '#0d6efd',
            })
            return;
        }

        const data = {
            org: props.org, 
            reservationNr: reservationId
        }

        try {
            await fetch('http://localhost:8080/bookSeats', {
                method: 'POST',
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(data)
            }).then(response => response.json())
            .then(resData => { // {message: 'Random message'}
                if (resData.error) throw 'error';
                let resMsg = resData.message;
                //let x = resMsg.split(' ')[1];

                Swal.fire({
                    title: 'Book successful!',
                    text: resMsg,
                    icon: 'success',
                    confirmButtonText: 'OK',
                    confirmButtonColor: '#0d6efd',
                }).then(() => {
                    resetForm();
                    initPage();
                });
            });
        }
        catch (err) {
            Swal.fire({
                title: 'Cannot book seats!',
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
                <h1>EconFly</h1>
            </div>
            <Form id="reservation-form" className="w-100">
                <h3 className="text-center">Create new flight</h3>
                <Form.Group className="mb-2" controlId="form-grid-from">
                    <Form.Label>Origin</Form.Label>
                    <Form.Control type="text" placeholder="Where from?" />
                </Form.Group>

                <Form.Group className="mb-2" controlId="form-grid-to">
                    <Form.Label>Destination</Form.Label>
                    <Form.Control type="text" placeholder="Where to?" />
                </Form.Group>

                <Row className="mb-2">
                    <Form.Group as={Col} controlId="form-grid-date">
                        <Form.Label>Date</Form.Label>
                        <Form.Control type="date"/>
                    </Form.Group>

                    <Form.Group as={Col} controlId="form-grid-time">
                        <Form.Label>Time</Form.Label>
                        <Form.Control type="time"/>
                    </Form.Group>
                </Row>

                <Form.Group className="mb-5" controlId="form-grid-seats">
                    <Form.Label>Seats</Form.Label>
                    <Form.Control type="number" min="0" placeholder="Seats available?" />
                </Form.Group>

                <div className="d-grid mb-5">
                    <Button onClick={sendData}>
                        Submit
                    </Button>        
                </div>
            </Form>

            <Form id="reservation-form" className="w-100">
                <h3 className="text-center">Book seats</h3>
                <Form.Group className="mb-5" controlId="form-grid-reservation-id">
                    <Form.Label>Reservation ID</Form.Label>
                    <Form.Select defaultValue="Choose...">
                    <option>Choose...</option>
                    { reservationsList.map((reservation) => (
                        <option key={reservation.reservationNr} 
                            value={reservation.reservationNr}>
                            {reservation.reservationNr}
                        </option>
                    )) }
                    </Form.Select>
                </Form.Group>
                <div className="d-grid mb-5">
                    <Button onClick={bookSeats}>
                        Submit
                    </Button>        
                </div>
            </Form>
        </Container>
    )
}
export default EconFly; 