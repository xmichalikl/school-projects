const Sequelize = require('sequelize');
module.exports = function(sequelize, DataTypes) {
  return sequelize.define('AbilityUpgrades', {
    id: {
      autoIncrement: true,
      type: DataTypes.INTEGER,
      allowNull: false,
      primaryKey: true
    },
    ability_id: {
      type: DataTypes.INTEGER,
      allowNull: true
    },
    match_player_detail_id: {
      type: DataTypes.INTEGER,
      allowNull: true
    },
    level: {
      type: DataTypes.INTEGER,
      allowNull: true
    },
    time: {
      type: DataTypes.INTEGER,
      allowNull: true
    }
  }, {
    sequelize,
    tableName: 'ability_upgrades',
    schema: 'public',
    timestamps: false,
    indexes: [
      {
        name: "ability_upgrades_pk",
        unique: true,
        fields: [
          { name: "id" },
        ]
      },
    ]
  });
};
