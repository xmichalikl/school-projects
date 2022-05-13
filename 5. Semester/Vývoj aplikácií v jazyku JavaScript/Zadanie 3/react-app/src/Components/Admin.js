import React, { useEffect, useState } from "react";

function Admin() {
    const [orders, setOrders] = useState([]);
    const [counter, setCounter] = useState(0);

    useEffect(() => {
        loadOrders();
        loadCounter();
    }, []);

    async function loadOrders() {
        await fetch('http://localhost:8080/get-orders')
            .then(response => response.json())
            .then(receivedData => setOrders(receivedData));
    }

    async function loadCounter() {
        await fetch('http://localhost:8080/get-counter')
            .then(response => response.json())
            .then(receivedData => setCounter(receivedData[0].counter));
    }

    function changeOrderState(orderId) {
        fetch('http://localhost:8080/update-order',{
            method: 'POST',
            body: JSON.stringify({id: orderId})
        }).then(res => res.json())
        .then(response => loadOrders());
    }

    function renderOrders() {
        return orders.map((order, index) => {
            return (
                <tr key={index}>
                    <td>{order.id}</td>
                    <td>{order.products}</td>
                    <td>{order.price} €</td>
                    <td>{order.customer_id}</td>
                    <td>{order.state}</td>
                    <td><button onClick={() => changeOrderState(order.id)}>Zaplatit</button></td>
                </tr>
            );
        });
    }

    return(
        <div>
            <h2>ADMIN</h2>
            <div>
                <h3>Kliknutí na reklamu = {counter}</h3>
            </div>
            <div>
                <h3>Zmena reklamy</h3>
                <form action="http://localhost:8080/update-ad" method="POST">
                    <label>Obrazok</label><br/>
                    <input type="text" name="img" placeholder="Link na obrázok"/><br/>
                    <label>Link</label><br/>
                    <input type="text" name="link" placeholder="Link na odkaz"/><br/><br/>
                    <input type="submit" value="Zmeniť"/>
                </form>
            </div>
            <div className="orders">
                <h3>Objednávky</h3>
                <table border="1">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>OBSAH</th>
                            <th>CENA</th>
                            <th>ZÁKAZNÍK</th>
                            <th>STAV</th>
                            <th>AKCIA</th>
                        </tr>
                    </thead> 
                    <tbody>
                        { renderOrders() }
                    </tbody>
                </table>
            </div>
        </div>
    )
}

export default Admin; 