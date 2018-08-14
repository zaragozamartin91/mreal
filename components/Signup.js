import React from 'react';


const axios = require('axios');

const EMPTY_LAMBDA = () => {
    console.log("NO SE ASIGNO UNA FUNCION PARA MANEJAR LA CREACION DE USUARIO")
};

const SignupPage = React.createClass({
    getDefaultProps() {
        return {
            signupSuccess: EMPTY_LAMBDA
        }
    },

    getInitialState() {
        return {
            username: '',
            password: '',
            repeatPassword: '',
            message: ''
        }
    },

    submitForm(event) {
        event.preventDefault();
        const {username, password} = this.state;
        axios.post('/api/signup', {username, password})
            .then(response => {
                console.log("SignupPage response.data: " + JSON.stringify(response.data));
                this.disableFullScreen();
                this.props.signupSuccess(response.data);
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
                <div className={"container"}>
                    <h1>Crear usuario</h1>

                    <p className="bg-danger">{this.state.message}</p>

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

                        <div className="form-group">
                            <label htmlFor="exampleInputPassword2">Repetir password</label>
                            <input type="password" className="form-control" id="exampleInputPassword2" name={"repeatPassword"}
                                   placeholder="Repetir password" value={this.state.repeatPassword}
                                   onChange={event => this.setState({repeatPassword: event.target.value})}/>
                        </div>

                        <button onClick={this.submitForm} className="btn btn-primary">Crear usuario</button>
                    </form>
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

export default SignupPage;
