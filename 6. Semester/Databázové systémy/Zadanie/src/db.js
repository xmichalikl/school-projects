const Pool = require('pg').Pool;
const dotenv = require('dotenv');
const dotenvExpand = require('dotenv-expand');

dotenv.config();

const pool = new Pool(
    {
        host:       process.env.DB_HOST,
        database:   process.env.DB_NAME,
        port:       process.env.DB_PORT,
        user:       process.env.DB_USER,
        password:   process.env.DB_PASS,
    }
);

module.exports = pool;