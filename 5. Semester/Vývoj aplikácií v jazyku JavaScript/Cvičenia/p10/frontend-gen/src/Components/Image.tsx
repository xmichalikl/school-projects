import React from 'react';

type img = {
    img: string
};

function Image(props: img):JSX.Element {
    return (
        <img width="80" src={props.img} />
    );
}

export default Image;