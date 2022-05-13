const Sequelize = require('sequelize');
module.exports = function(sequelize, DataTypes) {
  return sequelize.define('PurchaseLogs', {
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
    item_id: {
      type: DataTypes.INTEGER,
      allowNull: true
    },
    time: {
      type: DataTypes.INTEGER,
      allowNull: true
    }
  }, {
    sequelize,
    tableName: 'purchase_logs',
    schema: 'public',
    timestamps: false,
    indexes: [
      {
        name: "purchase_logs_pk",
        unique: true,
        fields: [
          { name: "id" },
        ]
      },
    ]
  });
};
