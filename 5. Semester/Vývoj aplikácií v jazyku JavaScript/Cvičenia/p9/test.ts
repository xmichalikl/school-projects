console.log('TS');

// number, string, boolean, Date, ?function/object?
// number[], string[]
// Array<number>

type firstName = string;

let misosFirstName: firstName = 'miso';

type Person = {
    firstName: string,
    lastName: string,
    dateOfBirth: Date
};

interface person {
    firstName: string,
    lastName: string,
    dateOfBirth: Date
};

let misko:person = {
    firstName: 'Michal',
    lastName: 'Justice',
    dateOfBirth: new Date(1992,11,1)
};

let Misko:Person = {
    firstName: 'Michal',
    lastName: 'Justice',
    dateOfBirth: new Date(1992,11,1)
};

function areSame(t:Person, i:person): boolean {
    if(t.firstName !== i.firstName) return false;
    else if(t.lastName !== i.lastName) return false;
    else if(t.dateOfBirth.getTime() !== i.dateOfBirth.getTime()) return false;
    return true;
}

if(areSame(Misko,misko)) console.log('su rovnaki');
else console.log('su odlisni');

console.log(JSON.stringify(misko));
console.log(JSON.stringify(Misko));

let b: any = 'lll';
let a: number | string = 4;
a = '4';
