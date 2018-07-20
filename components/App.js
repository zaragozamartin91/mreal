import React from 'react'
import Header from './Header'
import Main from './Main'
import LoginPage from './Login'

const App = React.createClass({
    getInitialState() {
        return {token: null}
    },

    loginSuccess(data) {
        console.log("loginSuccess data: " + JSON.stringify(data));
        this.setState({token: data.token});
    },

    render() {
        console.log("Renderizando App");
        const token = this.state.token;

        if (token) {
            return (<div>
                <Header/>
                <Main token={token}/>
            </div>);
        } else {
            return <LoginPage loginSuccess={this.loginSuccess}/>
        }
    }
});


export default App
