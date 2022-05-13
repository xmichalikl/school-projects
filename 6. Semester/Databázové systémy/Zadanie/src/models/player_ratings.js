const Sequelize = require('sequelize');
module.exports = function(sequelize, DataTypes) {
  return sequelize.define('PlayerRatings', {
    id: {
      autoIncrement: true,
      type: DataTypes.INTEGER,
      allowNull: false,
      primaryKey: true
    },
    player_id: {
      type: DataTypes.INTEGER,
      allowNull: true
    },
    total_wins: {
      type: DataTypes.INTEGER,
      allowNull: true
    },
    total_matches: {
      type: DataTypes.INTEGER,
      allowNull: true
    },
    trueskill_mu: {
      type: DataTypes.DECIMAL,
      allowNull: true
    },
    trueskill_sigma: {
      type: DataTypes.DECIMAL,
      allowNull: true
    }
  }, {
    sequelize,
    tableName: 'player_ratings',
    schema: 'public',
    timestamps: false,
    indexes: [
      {
        name: "player_ratings_pk",
        unique: true,
        fields: [
          { name: "id" },
        ]
      },
    ]
  });
};
