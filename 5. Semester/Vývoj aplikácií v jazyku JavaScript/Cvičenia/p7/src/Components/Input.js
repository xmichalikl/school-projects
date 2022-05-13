import React from 'react';

function Input(props) {
    function save() {
        let cal = document.getElementById('in');
        props.callback(cal.value);
    }

    return (
        <div className="row">
            <h3>Input</h3>
            <div className="form-group">
                <input id="in"></input>
                <button onClick={save}>Save</button>
            </div>
        </div>
    );
}

export default Input;