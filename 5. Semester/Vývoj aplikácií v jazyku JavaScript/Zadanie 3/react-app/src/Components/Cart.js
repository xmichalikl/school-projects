import React, { useEffect, useState } from "react";

function Cart() {
    const storage = window.sessionStorage;
    const [cart, setCart] = useState([]);

    useEffect(() => {
        loadCart();
    }, []);

    function editSessionStorage(productId) {
        if (storage.getItem('products')) {
            let products = JSON.parse(storage.getItem('products'));

            for (let i = 0; i < products.length; i++) {
                if (products[i].id == productId) {
                    if (products[i].count > 1) {
                        products[i].count--;
                    }
                    else {
                        products.splice(i, 1);
                    }
                    break;
                }
            }
            storage.setItem('products', JSON.stringify(products));
        }
    }

    async function loadCart() {
        await fetch('http://localhost:8080/get-products')
            .then(response => response.json())
            .then(receivedData => {
                if (storage.getItem('products')) { 
                    let storageProducts = JSON.parse(storage.getItem('products'));
                    let tempCart = [];

                    storageProducts.forEach(product => {
                        let tempProduct = receivedData.find(obj => {
                            return obj.id === product.id;                    
                        });
                        tempProduct.count = product.count;
                        tempCart.push(tempProduct);
                    });
                    setCart(tempCart);
                }
            });
    }

    function renderCart() {
        return cart.map((product, index) => {
            return (
                <tr key={index}>
                    <td>{product.name}</td>
                    <td><img src={product.img} alt="" width={100}/></td>
                    <td>{product.count}</td>
                    <td>{(product.price*product.count).toFixed(2)} €</td>
                    <td><button onClick={() => deleteProduct(product)}>Vymazať</button></td>
                </tr>
            );
        });
    }    

    function renderOrderForm() {
        if (cart.length > 0) {
            return (
                <div className="form">
                    <h2>Objednávka</h2>
                    <h3>Formulár</h3>
                    <form action="http://localhost:8080/create-order" method="POST">
                        <label>Meno</label><br/>
                        <input type="text" name="name" required/><br/>

                        <label>Priezvisko</label><br/>
                        <input type="text" name="surname" required/><br/>

                        <label>Email</label><br/>
                        <input type="email" name="email" required/><br/>

                        <label>Tel. číslo</label><br/>
                        <input type="tel" name="number" required/><br/>

                        <label>Adresa</label><br/>
                        <input type="text" name="address" required/><br/>

                        <label>Mesto</label><br/>
                        <input type="text" name="city" required/><br/>

                        <label>PSČ</label><br/>
                        <input type="number" name="postcode" required/><br/><br/>

                        <input type="hidden" name="order" value={storage.getItem('products')}/>
                        <input type="hidden" name="price" value={getTotalPrice()}/>
                        <input type="submit" value="Objednať"/>
                    </form>
                </div>
            )
        }
    }

    function deleteProduct(product) {
        let tempCart = [...cart];
        const productIndex = tempCart.indexOf(product);
        tempCart[productIndex].count--;
        editSessionStorage(tempCart[productIndex].id);
        
        if (tempCart[productIndex].count == 0) {
            tempCart.splice(productIndex, 1);
        }
        setCart(tempCart);
    }

    function getTotalPrice() {
        let price = 0;
        cart.forEach(product => {
            price += (product.price*product.count);
        });
        return price.toFixed(2);
    }

    return(
        <div>
            <h2>KOŠÍK</h2>
            <div className="cart">
                <h3>Obsah košíka</h3>
                <table border="1">
                    <thead>
                        <tr>
                            <th>NÁZOV</th>
                            <th>OBRÁZOK</th>
                            <th>MNOŽSTVO</th>
                            <th>CENA</th>
                            <th>AKCIA</th>
                        </tr>
                    </thead> 
                    <tbody>
                        { renderCart() }
                        <tr>
                            <th colSpan="3">CENA SPOLU</th>
                            <th colSpan="2">{getTotalPrice()} €</th>
                        </tr>
                    </tbody>
                </table>
                { renderOrderForm() }
            </div>
        </div>
    )
}

export default Cart;