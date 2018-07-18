const webpack = require('webpack');
const path = require('path');

module.exports = {
    entry: {
        index: path.join(__dirname, 'components', 'Index.js'),
        login: path.join(__dirname, 'components', 'Login.js'),
    },

    /* Se creara un archivo bundle por cada entrada que debera ser importado como <script></script> en las vistas */
    output: {
        filename: "[name].bundle.js",
        path: path.join(__dirname, 'src' , 'main' , 'resources' , 'static', 'js')
    },

    /* Indicamos que deseamos utilizar Babel para compilar los componentes de la carpeta components/ excluyendo 
    los elementos del directorio node_modules/ y bower_components/. */
    module: {
        loaders: [{
            test: path.join(__dirname, 'components'),
            loader: ['babel-loader'],
            exclude: /node_modules|bower_components/
        }]
    },
    /* La siguiente instruccion hace que sea posible mapear los errores en runtime con el codigo fuente 
    de React. Al ocurrir un error, ir a la consola de chrome o firefox y la linea de codigo del error
    correspondera al archivo fuente de react y no al del bundle. Para mas informacion ir a 
    https://webpack.js.org/configuration/devtool/ */
    devtool: 'inline-source-map',

    /* El siguiente plugin colocara todo el codigo en comun en un archivo common.bundle.js */
    plugins: [
        new webpack.optimize.CommonsChunkPlugin(["common"]),
    ]
};
