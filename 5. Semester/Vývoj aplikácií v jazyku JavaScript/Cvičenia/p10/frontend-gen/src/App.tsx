import React from 'react';

import Header from './Components/Header';
import Image from './Components/Image';
import Score from './Components/Score';
import Navigation from './Components/Navigation';
import { useState,useEffect } from 'react';
import { FileWatcherEventKind } from 'typescript';

type rating = {
    id: number,
    imgLink: string,
    score: number
};

let index:number = 0;

let pole:rating[] = [{
    id: 0,
    imgLink: 'https://upload.wikimedia.org/wikipedia/commons/thumb/6/6c/Pirate_Flag.svg/320px-Pirate_Flag.svg.png',
    score: 5
}];
/*,{
    id: 1,
    imgLink: 'https://upload.wikimedia.org/wikipedia/en/thumb/9/9e/Flag_of_Japan.svg/800px-Flag_of_Japan.svg.png',
    score: 5
},{
    id: 2,
    imgLink: 'https://upload.wikimedia.org/wikipedia/commons/thumb/f/f2/Civil_Ensign_of_Switzerland.svg/320px-Civil_Ensign_of_Switzerland.svg.png',
    score: 5
}];*/

function App():JSX.Element {
    const [entry, setEntry] = useState(pole[0]);

    function retrieveDB():void {
        fetch('/data').then(data => data.json()).then(data =>{
            pole = data;
            setEntry(pole[index]);
        });
    }

    useEffect(()=>{
        retrieveDB();
    },[]);

    function changeIndex(direction:string):void {
        if(direction==='+') index++;
        else if(direction==='-') index--;

        if(index<0) index = 0;
        if(index >= pole.length) index = pole.length-1;
        setEntry(pole[index]);
    }

    function changeScore(newScore:number):void {
        console.log('changeScore to: '+newScore);
        pole[index].score = newScore;
        fetch('/data',{
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({'id': pole[index].id, 'score': newScore})
        }).then(data => data.json()).then(data => {
            console.log('data ok');
            retrieveDB();
        }).catch(error => {
            console.error(error);
        });
    }

    return (
        <>
            <Header header={'My Rating App'} />
            <Image img={entry.imgLink}/>
            <Score score={entry.score} changeScore={changeScore}/>
            <Navigation changeIndex={changeIndex}/>
        </>
    );
}

export default App;