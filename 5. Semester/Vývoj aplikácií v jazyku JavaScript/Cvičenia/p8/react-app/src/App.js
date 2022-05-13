import React, {useEffect, useState} from 'react';
import Header from './Components/Header.js';
import Input from './Components/Input.js';
import Table from './Components/Table.js';

function App() {
    const [data, setData] = useState(['a','b']);

    function getDataFromServer() {
        return fetch('http://localhost:8080/data')
            .then(data=>data.json())
            .then(data => setData(data.map(e=>e.nazov)));
    }

    function addElement(element) {
        console.log('element: '+element);
        fetch('http://localhost:8080/data',{
            method: 'POST',
            headers: {
              'Content-Type': 'application/json',
            },
            body: JSON.stringify({input: element})
        }).then(e=>{
            getDataFromServer();
        });

        //setData([...data,element]);
    }

    function delElement(element) {
        console.log('element: '+element);
        let newData = [...data];
        const index = newData.indexOf(element);
        if(index >= 0) newData.splice(index,1);
        setData(newData);
    }

    useEffect(()=>{
        getDataFromServer();
    },[]);

    return (
        <div className="container">
            <Header heading="React APP Table" />
            <Input callback={addElement} />
            <Table data={data} callback={delElement} />
        </div>
    );
}

export default App;