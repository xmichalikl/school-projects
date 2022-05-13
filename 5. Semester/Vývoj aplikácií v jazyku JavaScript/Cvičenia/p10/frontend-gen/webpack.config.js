const path = require('path');

module.exports = {
    mode: 'development',
    entry: './src/index.tsx',
    output: {
        filename: 'bundle.js',
        path: path.resolve('static'),
        publicPath: '/'
    },
    module: {
        rules: [
            {
                test: /\.(ts|tsx)$/,
                exclude: /node_modules/,
                use: 'ts-loader'
            }
        ]
    },
    resolve: {
        extensions: ['.ts','.tsx','.js','.jsx']
    },
    devServer: {
        static: {
            directory: path.join(__dirname, 'static')
        },
        port: 9090,
        compress: true
    }
};