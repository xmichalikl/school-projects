const express = require('express');
const sql = require('yesql').pg;
const pool = require('./db');
const fs = require('fs');

const { PrismaClient } = require('@prisma/client');
const prisma = new PrismaClient();

const app = express();
const port = process.env.PORT || 8080;

// ------ HELP FUNCTIONS ------
function validIdFormat(id) {
    return /^[0-9]+$/.test(id);
}
function getQuery(sqlFile) {
    return new Promise((resolve, reject) => {
        let folderName = sqlFile.split('-')[0];
        fs.readFile(`./queries/${folderName}/${sqlFile}.sql`, 'utf-8', (err, data) => {
            if (err) reject(err);
            else resolve(data.toString());
        });
    });
}
function parseData(endpoint, data) {
    switch(endpoint) {
        case 'v3-1': return topPurchases(data);
        case 'v3-2': return abilityUsages(data);
        case 'v3-3': return tower_kills(data);
        default: return {error: 'Database error'};      
    }
}

// ------ OTHER SETTINGS ------
app.use(express.json());
app.disable('etag');

// ------- GET DATA V1 -------
app.get('/v1/health/', async function(req, res) {
    try {
        // Get data from database
        var version = (await pool.query(`SELECT VERSION();`)).rows[0];
        var size = (await pool.query(`SELECT pg_database_size('dota2')/1024/1024 as dota2_db_size;`)).rows[0];

        // Change size property value type from string to int
        size[Object.keys(size)[0]] = parseInt(size[Object.keys(size)[0]]);

        // Send data in json format
        res.json({pgsql: Object.assign({}, version, size)});
    } 
    catch(err) {
        // Send error message in json format
        res.status(500);
        res.json({error: 'Database error'});
    }
});

// ------- GET DATA V2 -------
app.get('/v2/patches/', async function(req, res) {
    res.json({res: 'v2_endpoint_1'});
});
app.get('/v2/players/:player_id/game_exp/', async function(req, res) {
    var id = req.params.player_id;
    
    if (validIdFormat(id)) {
        try {
            // Get data from database
            var strQ = await getQuery('v2-2');
            var data = (await pool.query(sql(strQ)({player_id: id}))).rows;
            
            // Player info
            var player = Object.fromEntries(
                Object.entries(data[0]).slice(0, 2)
            );
            // Player matches
            var matches = [];
            data.forEach(obj => {
                var len = Object.keys(obj).length;
                var sliced = Object.fromEntries(
                    Object.entries(obj).slice(2, len)
                );
                matches.push(sliced);
            });

            // Send data in json format
            res.status(200);
            res.json(Object.assign(player, {matches: matches}));
        }
        catch(err){
             // Send error message in json format
             res.status(500);
             res.json({error: 'Database error'});
        }
    }
    else {
        res.status(500);
        res.json({error: 'Bad {player_id} format'});
    }
});
app.get('/v2/players/:player_id/game_objectives/', async function(req, res) {
    var id = req.params.player_id;

    if (validIdFormat(id)) {
        try {
            // Get data from database
            var strQ = await getQuery('v2-3');
            var data = (await pool.query(sql(strQ)({player_id: id}))).rows;
            
            // Player info
            var player = Object.fromEntries(
                Object.entries(data[0]).slice(0, 2)
            );

            // Player matches
            var AllMatches = [];
            data.forEach(record => {
                var recordLen = Object.keys(record).length;
                var sliced = Object.fromEntries(
                    Object.entries(record).slice(2, recordLen)
                );
                AllMatches.push(sliced);
            });

            // Filter matches
            var filteredMatches = [];
            AllMatches.forEach(match => {
                // Check for match with same match_id 
                var foundMatch = filteredMatches.find(el => {
                    if (el.match_id === match.match_id) {
                        return true;
                    }
                });

                // If exists, then add another action from obj
                var matchLen = Object.keys(match).length;
                if (foundMatch !== undefined) {
                    var actions = [Object.fromEntries(
                        Object.entries(match).slice(2, matchLen)
                    )];
                    foundMatch.actions.push(...actions);
                }

                // Else add match with action to filteredMatches[]
                else {
                    foundMatch = Object.fromEntries(
                        Object.entries(match).slice(0, 2)
                    );
                    var actions = [Object.fromEntries(
                        Object.entries(match).slice(2, matchLen)
                    )];
                    filteredMatches.push(Object.assign(foundMatch, {actions: actions}));
                }
            }); 

            // Send data in json format
            res.status(200);
            res.json(Object.assign(player, {matches: filteredMatches}));
        }
        catch(err){
             // Send error message in json format
             res.status(500);
             res.json({error: 'Database error'});
        }
    }
    else {
        res.status(500);
        res.json({error: 'Bad {player_id} format'});
    }
});
app.get('/v2/players/:player_id/abilities/', async function(req, res) {
    var id = req.params.player_id;

    if (validIdFormat(id)) {
        try {
            // Get data from database
            var strQ = await getQuery('v2-4');
            var data = (await pool.query(sql(strQ)({player_id: id}))).rows;

            // Player info
            var player = Object.fromEntries(
                Object.entries(data[0]).slice(0, 2)
            );

            // Player matches
            var AllMatches = [];
            data.forEach(record => {
                var recordLen = Object.keys(record).length;
                var sliced = Object.fromEntries(
                    Object.entries(record).slice(2, recordLen)
                );
                AllMatches.push(sliced);
            });

            // Filter matches
            var filteredMatches = [];
            AllMatches.forEach(match => {
                // Check for match with same match_id 
                var foundMatch = filteredMatches.find(el => {
                    if (el.match_id === match.match_id) {
                        return true;
                    }
                });

                // If exists, then add another ability from obj
                var matchLen = Object.keys(match).length;
                if (foundMatch !== undefined) {
                    var abilities = [Object.fromEntries(
                        Object.entries(match).slice(2, matchLen)
                    )];
                    foundMatch.abilities.push(...abilities);
                }

                // Else add match with ability to filteredMatches[]
                else {
                    foundMatch = Object.fromEntries(
                        Object.entries(match).slice(0, 2)
                    );
                    var abilities = [Object.fromEntries(
                        Object.entries(match).slice(2, matchLen)
                    )];
                    filteredMatches.push(Object.assign(foundMatch, {abilities: abilities}));
                }
            }); 

            // Send data in json format
            res.status(200);
            res.json(Object.assign(player, {matches: filteredMatches}));
        }
        catch(err){
            // Send error message in json format
            res.status(500);
            res.json({error: 'Database error'});
       }
    }
    else {
        res.status(500);
        res.json({error: 'Bad {player_id} format'});
    }
});

// ------- GET DATA V3 -------
app.get('/v3/matches/:match_id/top_purchases/', async function(req, res) {
    var id = req.params.match_id;

    // Check match_id format
    if (!validIdFormat(id)) {
        res.status(500);
        res.json({error: 'Bad {match_id} format'});
        return;
    }

    try {
        // Get data from database
        var strQ = await getQuery('v3-1');
        var data = (await pool.query(sql(strQ)({match_id: id}))).rows;
        var responseData = parseData('v3-1', data);
        
        // Send data in json format
        res.status(200);
        res.json(responseData);
    } 
    catch(err) {
        // Send error message in json format
        res.status(500);
        res.json({error: 'Database error'});
    }
});
app.get('/v3/abilities/:ability_id/usage/', async function(req, res) {
    var id = req.params.ability_id;

    // Check match_id format
    if (!validIdFormat(id)) {
        res.status(500);
        res.json({error: 'Bad {ability_id} format'});
        return;
    }

    try {
        // Get data from database
        var strQ = await getQuery('v3-2');
        var data = (await pool.query(sql(strQ)({ability_id: id}))).rows;
        var responseData = parseData('v3-2', data);

        // Send data in json format
        res.status(200);
        res.json(responseData);
    } 
    catch(err) {
        // Send error message in json format
        res.status(500);
        res.json({error: 'Database error'});
    }
});
app.get('/v3/statistics/tower_kills/', async function(req, res) {
    try {
        // Get data from database
        var strQ = await getQuery('v3-3');
        var data = (await pool.query(sql(strQ)())).rows;
        var responseData = parseData('v3-3', data);
        
        // Send data in json format
        res.status(200);
        res.json(responseData);
    } 
    catch(err) {
        // Send error message in json format
        res.status(500);
        res.json({error: 'Database error'});
    }
});

// ------- GET DATA V4 -------
app.get('/v4/players/:player_id/game_exp/', async function(req, res) {
    var id = req.params.player_id;

    // Check player_id format
    if (!validIdFormat(id)) {
        res.status(500);
        res.json({error: 'Bad {player_id} format'});
        return;
    }

    try {
        // Get data from database
        var player = await prisma.players.findUnique({
            where: { id: parseInt(id) },
            select: { 
                id: true, nick: true,
                matches_players_details: { 
                    select: {
                        level: true, xp_hero: true, xp_creep: true,
                        xp_other: true, xp_roshan: true, player_slot: true,
                        matches: {
                            select: { id: true, duration: true, radiant_win: true }
                        },
                        heroes: {
                            select: { localized_name: true }
                        }
                    }
                }
            }
        });

        var respose = {
            id: player.id,
            player_nick: player.nick ?? 'unknown',
            matches: []
        };

        // Create match obj
        player.matches_players_details.forEach((mpd) => {
            var match = {
                match_id: mpd.matches.id,
                hero_localized_name: mpd.heroes.localized_name,
                match_duration_minutes: parseFloat((mpd.matches.duration/60.0).toFixed(2)),
                experiences_gained: (
                    (mpd.xp_hero ?? 0) + 
                    (mpd.xp_creep ?? 0) + 
                    (mpd.xp_other ?? 0) + 
                    (mpd.xp_roshan ?? 0)
                ),
                level_gained: mpd.level,
                winner: (
                    mpd.player_slot >= 0 && mpd.player_slot <= 4
                ) ? mpd.matches.radiant_win : !mpd.matches.radiant_win
            }
            respose.matches.push(match);
        });

        // Order by match_id
        respose.matches.sort((a, b) => {
            return a.match_id - b.match_id;
        })

        // Send data in json format
        res.json(respose);
    } 
    catch(err) {
        // Send error message in json format
        console.log(err);
        res.status(500);
        res.json({error: 'Database error'});
    }   
});
app.get('/v4/players/:player_id/game_objectives/', async function(req, res) {
    var id = req.params.player_id;

    // Check player_id format
    if (!validIdFormat(id)) {
        res.status(500);
        res.json({error: 'Bad {player_id} format'});
        return;
    }

    try {
        
        var player = await prisma.players.findUnique({
            where: { id: parseInt(id) },
            select: { 
                id: true, nick: true,
                matches_players_details: { 
                    select: {
                        matches: {
                            select: { id: true}
                        },
                        heroes: {
                            select: { localized_name: true }
                        },
                        game_objectives_game_objectives_match_player_detail_id_1Tomatches_players_details: {
                            //distinct: ['subtype'],
                            select: { subtype: true }
                        }
                    }   
                }
            }
        });

        var respose = {
            id: player.id,
            player_nick: player.nick ?? 'unknown',
            matches: []
        };

        player.matches_players_details.forEach((mpd) => {
            var match = {
                match_id: mpd.matches.id,
                hero_localized_name: mpd.heroes.localized_name,
                actions: []
            }

            // No action for match
            if (mpd.game_objectives_game_objectives_match_player_detail_id_1Tomatches_players_details.length == 0) {
                match.actions.push({ hero_action: "NO_ACTION", count: 1 });
                respose.matches.push(match);
                return;
            }

            mpd.game_objectives_game_objectives_match_player_detail_id_1Tomatches_players_details.forEach((action) => {
                var foundAction = match.actions.find(ac => {
                    if (ac.hero_action === action.subtype) {
                        return true;
                    }
                });

                if (foundAction != undefined) {
                    foundAction.count++;
                    return;
                }
                match.actions.push({hero_action: action.subtype, count: 1});
            });

            // Order by hero_action
            match.actions.sort((a, b) => {
                return a.hero_action.localeCompare(b.hero_action);
            });

            respose.matches.push(match);
        });

        // Order by match_id
        respose.matches.sort((a, b) => {
            return a.match_id - b.match_id;
        });
        
        // Send data in json format
        res.json(respose);
    }
    catch(err) {
        // Send error message in json format
        console.log(err);
        res.status(500);
        res.json({error: 'Database error'});
    }  
});


// ----- DEFAULT ENDPOINT -----
app.get("*", function(req, res) {
    var url = req.originalUrl;
    res.status(500);
    res.json({error: 'Internal Server Error'});
});

// ------- SERVER START -------
app.listen(port, () => {
    console.log('Server running at port ' + port); 
});

// ------ PARSE FUNCTIONS ------
function topPurchases(data) {
    var match = {
        id: data[0].match_id,
        heroes: []
    }

    var filteredHeroes = [];
    data.forEach(record => {
        // Check for hero with same match_id 
        var foundHero = filteredHeroes.find(hero => {
            if (hero.id === record.hero_id) {
                return true;
            }
        });

        // Create purchase obj
        var purchase = {
            id:     record.item_id,
            name:   record.item_name,
            count:  record.item_purchases
        }

        // If hero exists, then push purchase to foundHero.top_purchases[]
        if (foundHero !== undefined) {
            foundHero.top_purchases.push(purchase);
            return;
        }

        // Else push newHero with purchase to filteredHeroes[]
        var newHero = {
            id:     record.hero_id,
            name:   record.hero_name,
            top_purchases: [purchase]
        }
        filteredHeroes.push(newHero);   
    });
    match.heroes.push(...filteredHeroes);
    return(match);
}
function abilityUsages(data) {
    var ability = {
        id:     data[0].ability_id,
        name:   data[0].ability_name,
        heroes: []
    };

    var filteredHeroes = [];
    data.forEach(record => {
        // Check for hero with same match_id 
        var foundHero = filteredHeroes.find(hero => {
            if (hero.id === record.hero_id) {
                return true;
            }
        });

        // Create usage obj
        var usage = {
            bucket: record.bucket,
            count:  record.count
        }

        // If hero exists, then asign usage to foundHero
        if (foundHero !== undefined) {
            if (record.winner) foundHero.usage_winners = usage;
            if (!record.winner) foundHero.usage_loosers = usage;
            return;
        }

        // Else push newHero with usage to filteredHeroes[]
        var newHero = {
            id:     record.hero_id,
            name:   record.hero_name
        }
        if (record.winner) newHero.usage_winners = usage;
        if (!record.winner) newHero.usage_loosers = usage;
        filteredHeroes.push(newHero);   
    });
    ability.heroes.push(...filteredHeroes);
    return ability;
}
function tower_kills(data) {
    var heroes = [];
    data.forEach(record => {
        var newHero = {
            id:     record.hero_id,
            name:   record.hero_name,
            tower_kills: record.tower_kills
        }
        heroes.push(newHero);
    });
    return {heroes: heroes};
}