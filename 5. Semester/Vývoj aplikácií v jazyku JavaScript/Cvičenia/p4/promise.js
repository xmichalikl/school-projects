var q = await fnBigCompute(1);
console.log(q);

var fnBigCompute = function(p) {
    return new Promise((resolve,reject)=>{
        setTimeout(()=>{
            console.log('result processed');
            if(p!==4) resolve(p+1);
            else reject('boom');
        },5000);
    });
};


/*
fnBigCompute(3).then((res)=>{
    console.log(res)
}).catch((err)=>{
    console.error(err);
})
*/

/*
doSomething()
.then((res)=>{
    return doSomethingElse(res)
})
.then((res)=>{
    return doThirdThing(res)
})
...
.catch(err=>console.log(err););
*/

/*
var bigCompute = new Promise((resolve,reject)=>{
    var r = 4;
    // long processing
    // setInterval
    setTimeout(()=>{
        console.log('result processed');
        if(r===4) resolve(r);
        else reject('boom');
    },5000);
    console.log('bigCompute finished');
});

bigCompute.then((result)=>{
    console.log(result);
}).catch((err)=> {
    console.error(err);
});
*/

/*
pending, resolved, rejected
*-*-*-*, .then(),  .catch()
*/