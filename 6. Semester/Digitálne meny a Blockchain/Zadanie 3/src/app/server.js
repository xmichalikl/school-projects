const express = require('express')
const bodyParser = require('body-parser')
const cors = require('cors')
const app = express()
const port = 8080

app.use(bodyParser.json())
app.use(cors())
app.use(express.static('public'))

const channelName = 'mychannel'
const chaincodeName = 'basic'

const mspOrg1 = 'Org1MSP'
const mspOrg2 = 'Org2MSP'
const mspOrg3 = 'Org3MSP'

const org1UserId = 'EconFly'
const org2UserId = 'BusiFly'
const org3UserId = 'GladlyAbroad'
var actOrg, actUser

var wallet

var ccpOrg1, caClient1
var ccpOrg2, caClient2
var ccpOrg3, caClient3

const path = require('path')
const walletPath = path.join(__dirname, 'wallet')

const { Gateway, Wallets } = require('fabric-network')
const FabricCAServices = require('fabric-ca-client')

const { buildCAClient, registerAndEnrollUser, enrollAdmin } = require('./test-application/javascript/CAUtil.js')
const { buildCCPOrg1, buildCCPOrg2, buildCCPOrg3, buildWallet, prettyJSONString } = require('./test-application/javascript/AppUtil.js')
const req = require('express/lib/request')
const { MSPID_SCOPE_ALLFORTX } = require('fabric-network/lib/impl/event/defaulteventhandlerstrategies')


// sets global variables actOrg and actUser for gateway.connect
function setOrg(org){
    switch(org) {
        case 'EF': 
            actOrg = ccpOrg1
            actUser = org1UserId
            break
        case 'BF':
            actOrg = ccpOrg2
            actUser = org2UserId
            break
        case 'GA':
            actOrg = ccpOrg3
            actUser = org3UserId
            break
        default:
            actOrg = null
            actUser = null
            break
    }
}


app.post('/createFlight', async (req, res) => {

    console.log(req.body)

    var org = req.body.org
    setOrg(org) 

    let from = req.body.flyFrom
    let to = req.body.flyTo
    let dateTime = req.body.dateTime
    let seats = req.body.availablePlaces

    try {
        const gateway = new Gateway()

        try {
            await gateway.connect(actOrg, {
                wallet,
                identity: actUser,
                discovery: { enabled: true, asLocalhost: true }
            })

            const network = await gateway.getNetwork(channelName)
            const contract = network.getContract(chaincodeName)

            let result = await contract.submitTransaction('createFlight', from, to, dateTime, seats)
            console.log('*** Result: ', result.toString())

            res.json({message: result.toString()}).end()
        } finally {
            gateway.disconnect()
        }
    } catch (error) {
        console.error('******** FAILED to run the transaction: ', error)
        res.json({error: error})
    }
})

app.post('/reserveSeats', async (req, res) => {

    var org = req.body.org
    setOrg(org) 

    var flightNumer = req.body.flightNr
    var numberOfSeats = JSON.stringify(Object.keys(req.body.passengers).length)
    var email = req.body.email
    var passengers = req.body.passengers

    var names = []
    
    for( var passenger in passengers){
        names.push(passengers[passenger]['name'] + " " + passengers[passenger]['surname'] )
    }

    try {
        const gateway = new Gateway()
        try {
            await gateway.connect(actOrg, {
                wallet,
                identity: actUser,
                discovery: { enabled: true, asLocalhost: true }
            })

            const network = await gateway.getNetwork(channelName)
            const contract = network.getContract(chaincodeName)

            let result = await contract.submitTransaction('reserveSeats', flightNumer, numberOfSeats, names, email)
            console.log('*** Result: ', result)

            res.json({message: result.toString()}).end()
        } finally {
            gateway.disconnect()
        }
    } catch (error) {
        console.error('******** FAILED to run the transaction: ', error)
        res.json({error: error})
    }
})

app.post('/bookSeats', async (req, res) => {

    var org = req.body.org
    setOrg(org) 

    var reservationNr = JSON.stringify(req.body.reservationNr)

    try {
        const gateway = new Gateway()

        try {
            await gateway.connect(actOrg, {
                wallet,
                identity: actUser,
                discovery: { enabled: true, asLocalhost: true }
            })

            const network = await gateway.getNetwork(channelName)
            const contract = network.getContract(chaincodeName)

            let result = await contract.submitTransaction('bookSeats', reservationNr)
            console.log('*** Result: ', result)

            res.json({message: result.toString()}).end()
        } finally {
            gateway.disconnect()
        }
    } catch (error) {
        console.error('******** FAILED to run the transaction: ', error)
        res.json({error: error})
    }
})

app.post('/getFlight', async function (req, res) {

    var org = req.body.org
    setOrg(org) 

    var flightId = JSON.stringify(req.body.id)
    
    try {
        const gateway = new Gateway()

        try {
            await gateway.connect(ccpOrg1, {
                wallet,
                identity: 'admin',
                discovery: { enabled: true, asLocalhost: true }
            })

            const network = await gateway.getNetwork(channelName)
            const contract = network.getContract(chaincodeName)

            console.log("--------------", network, '\n', contract)

            let result = await contract.submitTransaction('getFlight', flightId)
            console.log('*** Result: ', prettyJSONString(result.toString()))

            result = prettyJSONString(result.toString())

            res.send(result).end()
        } finally {
            gateway.disconnect()
        }
    } catch (error) {
        console.error('******** FAILED to run the transaction: ', error)
        res.json({error: error})
    }
})

app.post('/getAllFlights', async (req, res) => {

    var org = req.body.org
    setOrg(org) 

    try {
        const gateway = new Gateway()

        try {
            await gateway.connect(actOrg, {
                wallet,
                identity: actUser,
                discovery: { enabled: true, asLocalhost: true }
            })

            const network = await gateway.getNetwork(channelName)
            const contract = network.getContract(chaincodeName)

            let result = await contract.submitTransaction('getAllFlights')
            console.log('*** Result: ', prettyJSONString(result.toString()))

            result = prettyJSONString(result.toString())

            res.send(result).end()
        } finally {
            gateway.disconnect()
        }
    } catch (error) {
        console.error('******** FAILED to run the transaction: ', error)
        res.json({error: error})
    }
})

app.post('/getAllReservations', async (req, res) => {

    console.log("reserver ", req.body)
    var org = req.body.org
    setOrg(org) 

    try {
        const gateway = new Gateway()

        try {
            await gateway.connect(actOrg, {
                wallet,
                identity: actUser,
                discovery: { enabled: true, asLocalhost: true }
            })

            const network = await gateway.getNetwork(channelName)
            const contract = network.getContract(chaincodeName)

            let result = await contract.submitTransaction('getAllReservations')
            console.log('*** Result: ', prettyJSONString(result.toString()))

            console.log('------', result)
            result = prettyJSONString(result.toString())

            res.send(result).end()
        } finally {
            gateway.disconnect()
        }
    } catch (error) {
        console.error('******** FAILED to run the transaction: ', error)
        res.json({error: error})
    }
})

app.post('/checkIn', async (req, res) => {

    var org = req.body.org
    setOrg(org) 

    let reservationNr = req.body.reservationNr
    let passportIDs = req.body.passportIDs
    let seats = req.body.seats

    try {
        const gateway = new Gateway()

        try {
            await gateway.connect(actOrg, {
                wallet,
                identity: actUser,
                discovery: { enabled: true, asLocalhost: true }
            })
            

            const network = await gateway.getNetwork(channelName)
            const contract = network.getContract(chaincodeName)

            let result = await contract.submitTransaction('checkIn', reservationNr, passportIDs, seats)
            console.log('*** Result: ', result)

            res.json({message: result.toString()}).end()
        } finally {
            gateway.disconnect()
        }
    } catch (error) {
        console.error('******** FAILED to run the transaction: ', error)
        res.json({error: error})
    }
})


app.get('/bundle.js', function (req, res) {
    res.sendFile(path.resolve('static/bundle.js'));
});


app.get('/*', function (req, res) {
    res.sendFile(path.resolve('static/index.html'));
});


app.listen(port, () => {
  console.log('Example app listening on port ', port)
  main()
})

// vytvorenie 3 organizacii, ich adminov a pouzivatelov
async function main(){
    try{
        wallet = await buildWallet(Wallets, walletPath)

        ccpOrg1 = buildCCPOrg1()
        caClient1 = buildCAClient(FabricCAServices, ccpOrg1, 'ca.org1.example.com')
        await enrollAdmin(caClient1, wallet, mspOrg1)
        await registerAndEnrollUser(caClient1, wallet, mspOrg1, org1UserId, 'org1.department1')

        ccpOrg2 = buildCCPOrg2()
        caClient2 = buildCAClient(FabricCAServices, ccpOrg2, 'ca.org2.example.com')
        await enrollAdmin(caClient2, wallet, mspOrg2)
        await registerAndEnrollUser(caClient2, wallet, mspOrg2, org2UserId, 'org2.department1')

        ccpOrg3 = buildCCPOrg3()
        caClient3 = buildCAClient(FabricCAServices, ccpOrg3, 'ca.org3.example.com')
        await enrollAdmin(caClient3, wallet, mspOrg3)
        await registerAndEnrollUser(caClient3, wallet, mspOrg3, org3UserId, 'org3.department1')

    } catch (error) {
        console.error('******** FAILED to run the application: ', error)
    }
}
