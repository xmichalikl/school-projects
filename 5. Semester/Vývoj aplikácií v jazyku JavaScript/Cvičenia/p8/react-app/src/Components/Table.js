import React from 'react';

function Table(props) {
    function delElement(element) {
        props.callback(element);
    }

    function rows() {
        // input:
        // props.data = ['a','b'];
        // output:
        // <tr><td>a</td></tr>
        // <tr><td>a</td></tr>
        return props.data.map((element,index) => {
            return (
                <tr key={index}>
                    <td>{element}</td>
                    <td><button onClick={e => {delElement(element)}}>X</button></td>
                </tr>
            );
        });
    }

    return (
        <div className="row">
            <table border="1">
                <thead>
                    <tr>
                        <th>Nazov</th>
                        <th>DEL</th>
                    </tr>
                </thead>
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