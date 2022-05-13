import React from 'react';

type id = {
    id: number
};

function Navigation(props: id):JSX.Element {
    function prev(): void {
        console.log('prev');
    }

    function next(): void {
        console.log('next');
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