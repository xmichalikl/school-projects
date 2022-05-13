import React, { useEffect, useState } from "react";

function Ad() {
    const storage = window.sessionStorage;
    const [ad, setAd] = useState({});

    useEffect(() => {
        loadAd();
    }, []);

    async function loadAd() {
        await fetch('http://localhost:8080/get-advertisement')
            .then(response => response.json())
            .then(receivedData => setAd(receivedData[0]));
    }

    function adClick() {
        fetch('http://localhost:8080/ad-clicked')
            .then(response => response.json())
            .then(receivedData => console.log(receivedData));
    }

    function renderAd () {
        storage.clear();
        if (JSON.stringify(ad) !== '{}') {
            return(
                <div>
                    <h2>Ďakujeme za Váš nákup</h2>
                    <a onClick={adClick} href={ad.link}><img src={ad.img} alt="" /></a>
                </div>
            );
        }
    }

    return(
        <div className="ad">
            { renderAd() }
        </div>
    )
}

export default Ad; 