import React from 'react'
import Navigator from './Navigator'
import Main from './Main'
import LoginPage from './Login'

console.log('Parseando cookie ' + document.cookie);
const cookie = document.cookie || '';
const cookieTokenStr = cookie.split('; ').find(s => s.startsWith('token='));
const cookieToken = cookieTokenStr ? cookieTokenStr.replace('token=', '') : null;

const App = React.createClass({
    getInitialState() {
        return {token: null, renderReady: false}
    },

    componentDidMount() {
        console.log('App MONTADA!');
        /* Si se encuentra un token en las cookies entonces se setea el mismo y se obtiene el usuario
        Caso contrario, se marca al componente como listo para renderizar */
        if (cookieToken) this.setState({token: cookieToken, renderReady: true});
        else this.setState({renderReady: true});
    },

    loginSuccess(data) {
        console.log("loginSuccess data: " + JSON.stringify(data));
        this.setState({token: data.token});
    },

    render() {
        console.log("Renderizando App");

        if (this.state.renderReady) {
            const token = this.state.token;

            if (token) {
                return (
                    <div>
                        <Navigator/>
                        <Main token={token}/>
                    </div>);
            } else {
                return <LoginPage loginSuccess={this.loginSuccess}/>
            }
        } else {
            return <p className="primary">Espere...</p>
        }
    }
});


export default App
