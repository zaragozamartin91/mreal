import React from 'react'
import Navigator from './Navigator'
import Main from './Main'
import LoginPage from './Login'
import SignupPage from './Signup'

console.log('Parseando cookie ' + document.cookie);
const cookie = document.cookie || '';

const cookieTokenStr = cookie.split('; ').find(s => s.startsWith('token='));
const cookieToken = cookieTokenStr ? cookieTokenStr.replace('token=', '') : null;

const cookieUsernameStr = cookie.split('; ').find(s => s.startsWith('username='));
const cookieUsername = cookieUsernameStr ? cookieUsernameStr.replace('username=', '') : null;

const App = React.createClass({
    getInitialState() {
        return {token: null, username: null, renderReady: false, signup: false}
    },

    componentDidMount() {
        console.log('App MONTADA!');
        /* Si se encuentra un token en las cookies entonces se setea el mismo y se obtiene el usuario
        Caso contrario, se marca al componente como listo para renderizar */
        if (cookieToken) this.setState({token: cookieToken, username: cookieUsername, renderReady: true});
        else this.setState({renderReady: true});
    },

    loginSuccess(data) {
        console.log("loginSuccess data: " + JSON.stringify(data));
        this.setState({token: data.token, username: data.username});
    },

    render() {
        console.log("Renderizando App");

        if (!this.state.renderReady) return <p className="primary">Espere...</p>;


        const token = this.state.token;
        const username = this.state.username;

        if (token) return (
            <div>
                <Navigator/>
                <Main token={token} username={username}/>
            </div>
        );

        if(this.state.signup) return <SignupPage
            signupSuccess={() => this.setState({signup:false})}/>;

        return <LoginPage
                loginSuccess={this.loginSuccess}
                onSignupClick={() => this.setState({signup:true})} />;
    }
});


export default App
