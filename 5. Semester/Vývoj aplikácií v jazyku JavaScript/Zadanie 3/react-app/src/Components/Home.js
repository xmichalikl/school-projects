import React, { useEffect, useState } from "react";

function Home() {
    const storage = window.sessionStorage;
    const [products, setProducts] = useState([]);

    useEffect(() => {
        loadProducts();
    }, []);

    function addItemToSessionStorage(productId) {
        if (!storage.getItem('products')) {
            storage.setItem('products', JSON.stringify([]));
        }
        let products = JSON.parse(storage.getItem('products'));

        let found = false;
        for (let i = 0; i < products.length; i++) {
            if (products[i].id == productId) {
                products[i].count++;
                found = true;
                break;
            }
        }
        if (!found) {
            let newProduct = {id: productId, count: 1};
            products.push(newProduct);
        } 
        storage.setItem('products', JSON.stringify(products));
    }

    async function loadProducts() {
        await fetch('http://localhost:8080/get-products')
            .then(response => response.json())
            .then(receivedData => setProducts(receivedData));
    }

    function renderProducts() {
        return products.map((product, index) => {
            return (
                <tr key={index}>
                    <td>{product.name}</td>
                    <td><img src={product.img} alt="" width={100}/></td>
                    <td>{product.price} €</td>
                    <td><button onClick={() => addItemToSessionStorage(product.id)}>Do košíka</button></td>
                </tr>
            );
        });
    }

    return(
        <div>
            <h2>DOMOV</h2>
            <div className="products">
                <h3>Produkty</h3>
                <table border="1">
                    <thead>
                        <tr>
                            <th>NÁZOV</th>
                            <th>OBRÁZOK</th>
                            <th>CENA</th>
                            <th>AKCIA</th>
                        </tr>
                    </thead> 
                    <tbody>
                        { renderProducts() }
                    </tbody>
                </table>
            </div>
        </div>
    )
}

export default Home; 