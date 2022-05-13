//const isOdd = require('is-odd');
import isOdd from 'is-odd';

let p = 0;
const print = function() {
    p++;
    console.log(p+' prednaska je '+(isOdd(p)?'neparna':'parna'));
};

//module.exports = print;
//exports.print = print;
export default print;