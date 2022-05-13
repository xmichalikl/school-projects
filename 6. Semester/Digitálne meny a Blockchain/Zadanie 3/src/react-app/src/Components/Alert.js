import React from "react";
import { Modal, Button } from 'react-bootstrap';

function Alert(props) {
    return(
        <Modal {...props} size="sm" aria-labelledby="contained-modal-title-vcenter" centered>
            <Modal.Header closeButton>
                <Modal.Title id="contained-modal-title-vcenter">
                    {props.title}
                </Modal.Title>
            </Modal.Header>

            <Modal.Body>
                <h5>{props.message}</h5>

                { Object.entries(props.data).map(([key, value]) => (
                    <p>{key}: {value}</p>
                )) }

            </Modal.Body>
                
            <Modal.Footer>
                <Button onClick={props.onHide}>OK</Button>
            </Modal.Footer>
        </Modal>
    )
}
export default Alert; 
//
/*{ [...props.message].map((msg) => (
    <p>{msg}</p>
)) }*/

/*{ Object.entries(props.message).map((msg) => (
    <p>{msg[0]}: {msg[1]}</p>
)) }*/