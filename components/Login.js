import React from 'react';


const axios = require('axios');

const EMPTY_LAMBDA = () => {
    console.log("NO SE ASIGNO UNA FUNCION")
};

const LoginPage = React.createClass({
    getDefaultProps() {
        return {
            loginSuccess: EMPTY_LAMBDA,
            onSignupClick: EMPTY_LAMBDA
        }
    },

    getInitialState() {
        return {
            username: '',
            password: '',
            message: ''
        }
    },

    submitForm(event) {
        event.preventDefault();
        const {username, password} = this.state;
        axios.post('login', {username, password})
            .then(response => {
                console.log("LoginPage response.data: " + JSON.stringify(response.data));
                this.disableFullScreen();
                this.props.loginSuccess(response.data);
            })
            .catch(error => {
                if (error.response) this.setState({message: error.response.data.message});
                else this.setState({message: error.message});
            });
    },

    render() {
        this.enableFullScreen();

        return (
            <div>

                <div id={"login-container"}>

                    <h1>Iniciar sesion</h1>

                    <p className="text-danger">{this.state.message}</p>


                    <form>
                        <div className="form-group">
                            <label htmlFor="exampleInputEmail1">Username</label>
                            <input type="email" className="form-control" id="exampleInputEmail1" name={"username"}
                                   aria-describedby="emailHelp" placeholder="Nombre de usuario"
                                   value={this.state.username}
                                   onChange={event => this.setState({username: event.target.value})}/>
                        </div>

                        <div className="form-group">
                            <label htmlFor="exampleInputPassword1">Password</label>
                            <input type="password" className="form-control" id="exampleInputPassword1" name={"password"}
                                   placeholder="Password" value={this.state.password}
                                   onChange={event => this.setState({password: event.target.value})}/>
                        </div>
                    </form>

                    <button onClick={this.submitForm}
                            className="btn btn-primary btn1">Iniciar sesion
                    </button>

                    <button onClick={this.props.onSignupClick}
                            className="btn btn-success btn2">Crear usuario
                    </button>
                </div>
            </div>
        );
    },

    enableFullScreen() {
        document.querySelector("html").style.width = "100%";
        document.querySelector("html").style.height = "100%";

        let bodyStyle = document.querySelector("body").style;
        bodyStyle.width = "100%";
        bodyStyle.height = "100%";
        bodyStyle.display = "flex";
        bodyStyle.justifyContent = "center";
        bodyStyle.alignItems = "center";
    },

    disableFullScreen() {
        let htmlElem = document.querySelector("html");
        htmlElem.style.width = "";
        htmlElem.style.height = "";

        let bodyStyle = document.querySelector("body").style;
        bodyStyle.width = "";
        bodyStyle.height = "";
        bodyStyle.display = "";
        bodyStyle.justifyContent = "";
        bodyStyle.alignItems = "";
    }
});

export default LoginPage;
