document.querySelector('.btn').addEventListener('click',function(){
    fetch('http://localhost:3000/')
        .then(function(res){ return res.text();})
        .then(function(data){
            console.log(data);
            var cislo = data.split(': ')[1];
            document.getElementById('counter').innerHTML = cislo;
        });
});