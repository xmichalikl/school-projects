import React from 'react';

function Table(props) {
    function deleteMe(e) {
        console.log('Table.deleteMe: '+e);
        props.callback(e);
    }

    function rows() {
        return props.products.map((element,index) => {
            return (
                <tr key={index}>
                    <td>{element}</td>
                    <td><button onClick={e=>deleteMe(element)}>X</button></td>
                </tr>);
        });
    }

    return (
        <div className="row">
            <h3>Table</h3>
            <table border="1">
                <thead><tr><th>Name</th><th>X</th></tr></thead>
                <tbody>
                {
                    rows()
                }
                </tbody>
            </table>
        </div>
    );
}

export default Table;