import React from 'react';

type score = {
    score: number,
    changeScore: Function
};

function Score(props: score):JSX.Element {
    let current:number = -1;
    function oc(): void {
        props.changeScore(current);
    }

    function changeScore(ev:any):void {
        current = ev.target.value;
    }

    return (
        <div className="row">
            <div className="form-group">
                <label htmlFor="score">Score</label>
                <input id="score" type="range" min="1" max="5" step="1" onChange={changeScore}/>
            </div>
            <div className="form-group">
                <span>{props.score}</span>
                <button onClick={oc}>Rate</button>
            </div>
        </div>
    );
}

export default Score;



















































































/*
function Score(props: score):JSX.Element {
    const [score, setScore] = useState(props.score);

    function oc(): void {
        //
        //let element: HTMLElement = (<HTMLInputElement[]><any>document.getElementById('score'));
        //let newScore: number = element.value;
        //console.log('rated '+newScore)
        //
        console.log('rated '+score);
        props.changeScore(score);
    }

    function handleChange(ev: any): void {
        setScore(ev.target.value);
    }

    return (
        <div className="row">
            <div className="form-group">
                <label htmlFor="score">Score</label>
                <input id="score" value={score} type="range" min="1" max="5" step="1" onChange={handleChange} />
            </div>
            <div className="form-group">
                <span>{score}</span>
                <button onClick={oc}>Rate</button>
            </div>
        </div>
    );
}
*/
