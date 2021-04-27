var path = require('path');

module.exports = {
   mode: 'development',
   entry:  "./src/app.jsx",
   output:{
       path: path.resolve(__dirname, './build'),
       publicPath: '/build/',
       filename: "bundle.js"
   },
   devServer: {
     historyApiFallback: true,
     inline:true,
     port: 8008
   },
   performance: {
    hints: false,
    maxEntrypointSize: 512000,
    maxAssetSize: 512000
},
   module:{
       rules:[
           {
               test: /\.(js|jsx)$/,
               exclude: /(node_modules)/,
               loader: "babel-loader",
               options:{
                   presets:["@babel/preset-env",
                            "@babel/preset-react"],
                   plugins:["@babel/plugin-proposal-class-properties"]
               }
           },
       ]
   }
}
