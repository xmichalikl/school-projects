import React, {useState,useEffect} from 'react';
import Head from './Components/Head.js';
import Input from './Components/Input.js';
import Table from './Components/Table.js';

function App() {
    const [products, setProducts] = useState(["a", "b"]);
    
    function addProduct(product) {
        console.log('App.addProduct: '+product);
        let current = products;
        current.push(product);
        setProducts([...current]);
    }

    function delProduct(product) {
        console.log('App.delProduct: '+product);
        let current = products;
        const index = current.indexOf(product);
        if(index > -1) current.splice(index,1);
        setProducts([...current]);
    }

    useEffect(() => {
        console.log('products', products);
    }, [products])

    return (
        <div className="App container">
            <Head />
            <Input callback={addProduct} />
            <Table products={products} callback={delProduct} />
        </div>
    );
}

export default App;