import React from 'react';

import Header from './Components/Header';
import Image from './Components/Image';
import Score from './Components/Score';
import Navigation from './Components/Navigation';

type rating = {
    id: number,
    imgLink: string,
    score: number
};

function App():JSX.Element {
    let first:rating = {
        id: 0,
        imgLink: 'https://upload.wikimedia.org/wikipedia/commons/thumb/d/d9/Flag_of_Canada_%28Pantone%29.svg/800px-Flag_of_Canada_%28Pantone%29.svg.png',
        score: 5
    };

    return (
        <>
            <Header header={'My Rating App'} />
            <Image img={first.imgLink}/>
            <Score score={first.score} />
            <Navigation id={first.id} />
        </>
    );
}

export default App;