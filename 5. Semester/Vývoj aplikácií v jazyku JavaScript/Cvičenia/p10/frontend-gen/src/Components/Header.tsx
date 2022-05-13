import React from 'react';
import { ProgressPlugin } from 'webpack';

type header = {
    header: string
};

function Header(props: header):JSX.Element {
    return (
        <h2>{props.header}</h2>
    );
}

export default Header;