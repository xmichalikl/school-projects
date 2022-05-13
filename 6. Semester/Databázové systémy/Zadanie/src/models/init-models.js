var DataTypes = require("sequelize").DataTypes;
var _Abilities = require("./abilities");
var _AbilityUpgrades = require("./ability_upgrades");
var _Chats = require("./chats");
var _ClusterRegions = require("./cluster_regions");
var _GameObjectives = require("./game_objectives");
var _Heroes = require("./heroes");
var _Items = require("./items");
var _Matches = require("./matches");
var _MatchesPlayersDetails = require("./matches_players_details");
var _Patches = require("./patches");
var _PlayerActions = require("./player_actions");
var _PlayerRatings = require("./player_ratings");
var _PlayerTimes = require("./player_times");
var _Players = require("./players");
var _PurchaseLogs = require("./purchase_logs");
var _Teamfights = require("./teamfights");
var _TeamfightsPlayers = require("./teamfights_players");

function initModels(sequelize) {
  var Abilities = _Abilities(sequelize, DataTypes);
  var AbilityUpgrades = _AbilityUpgrades(sequelize, DataTypes);
  var Chats = _Chats(sequelize, DataTypes);
  var ClusterRegions = _ClusterRegions(sequelize, DataTypes);
  var GameObjectives = _GameObjectives(sequelize, DataTypes);
  var Heroes = _Heroes(sequelize, DataTypes);
  var Items = _Items(sequelize, DataTypes);
  var Matches = _Matches(sequelize, DataTypes);
  var MatchesPlayersDetails = _MatchesPlayersDetails(sequelize, DataTypes);
  var Patches = _Patches(sequelize, DataTypes);
  var PlayerActions = _PlayerActions(sequelize, DataTypes);
  var PlayerRatings = _PlayerRatings(sequelize, DataTypes);
  var PlayerTimes = _PlayerTimes(sequelize, DataTypes);
  var Players = _Players(sequelize, DataTypes);
  var PurchaseLogs = _PurchaseLogs(sequelize, DataTypes);
  var Teamfights = _Teamfights(sequelize, DataTypes);
  var TeamfightsPlayers = _TeamfightsPlayers(sequelize, DataTypes);


  return {
    Abilities,
    AbilityUpgrades,
    Chats,
    ClusterRegions,
    GameObjectives,
    Heroes,
    Items,
    Matches,
    MatchesPlayersDetails,
    Patches,
    PlayerActions,
    PlayerRatings,
    PlayerTimes,
    Players,
    PurchaseLogs,
    Teamfights,
    TeamfightsPlayers,
  };
}
module.exports = initModels;
module.exports.initModels = initModels;
module.exports.default = initModels;
