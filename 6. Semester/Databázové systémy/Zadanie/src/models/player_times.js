const Sequelize = require('sequelize');
module.exports = function(sequelize, DataTypes) {
  return sequelize.define('PlayerTimes', {
    id: {
      autoIncrement: true,
      type: DataTypes.INTEGER,
      allowNull: false,
      primaryKey: true
    },
    match_player_detail_id: {
      type: DataTypes.INTEGER,
      allowNull: true
    },
    time: {
      type: DataTypes.INTEGER,
      allowNull: true
    },
    gold: {
      type: DataTypes.INTEGER,
      allowNull: true
    },
    lh: {
      type: DataTypes.INTEGER,
      allowNull: true
    },
    xp: {
      type: DataTypes.INTEGER,
      allowNull: true
    }
  }, {
    sequelize,
    tableName: 'player_times',
    schema: 'public',
    timestamps: false,
    indexes: [
      {
        name: "player_times_pk",
        unique: true,
        fields: [
          { name: "id" },
        ]
      },
    ]
  });
};
