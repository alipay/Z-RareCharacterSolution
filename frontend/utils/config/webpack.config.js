'use strict';
const { join } = require('path');

const config = {
  mode: 'production',
  entry: join(__dirname, '../src/index.ts'),
  output: {
    path: join(__dirname, '../dist/'),
    filename: 'index.web.js',
    library: 'RareWordsUtils',
    libraryTarget: 'window' // 采用通用模块定义
  },
  resolve: {
    extensions: ['.js', '.ts']
  },
  module: {
    rules: [
      {
        test: /(\.js)|(\.ts)$/,
        use: [
          {
            loader: 'babel-loader',
            options: {
              presets: [['@babel/preset-env', { targets: { ie: 11, ios: 9, android: 5 } }]]
            }
          }
        ],
        exclude: /node_modules/
      }
    ]
  }
};
module.exports = config;
