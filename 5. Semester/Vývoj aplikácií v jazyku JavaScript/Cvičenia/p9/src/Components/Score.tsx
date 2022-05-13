import React, {useState} from 'react';

type score = {
    score: number
};

function Score(props: score):JSX.Element {
    const [score, setScore] = useState(props.score);

    function oc(): void {
        /*
        let element: HTMLElement = (<HTMLInputElement[]><any>document.getElementById('score'));
        let newScore: number = element.value;
        console.log('rated '+newScore)
        */
        console.log('rated '+score)
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

export default Score;