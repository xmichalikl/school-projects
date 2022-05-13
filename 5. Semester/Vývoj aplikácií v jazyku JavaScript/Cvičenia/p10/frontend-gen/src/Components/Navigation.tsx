import React from 'react';

type change = {
    changeIndex: Function
};

function Navigation(props: change):JSX.Element {
    function prev(): void {
        props.changeIndex('-');
    }

    function next(): void {
        props.changeIndex('+');
    }
    return (
        <div className="row">
            <div className="col-6">
                <button onClick={prev}>Prev</button>
            </div>
            <div className="col-6">
                <button onClick={next}>Next</button>
            </div>
        </div>
    );
}

export default Navigation;