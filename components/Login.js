import React from 'react';


const axios = require('axios');

const EMPTY_LAMBDA = () => {
    console.log("NO SE ASIGNO UNA FUNCION PARA MANEJAR EL LOGIN")
};

const LoginPage = React.createClass({
    getDefaultProps() {
        return {
            loginSuccess: EMPTY_LAMBDA
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
                this.props.loginSuccess(response.data);
            })
            .catch(error => {
                if (error.response) this.setState({message: error.response.data.message});
                else this.setState({message: error.message});
            });
    },

    render() {
        return (
            <div>
                <div className={"container"}>
                    <h1>Iniciar sesion</h1>

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

                        <button onClick={this.submitForm} className="btn btn-primary">Iniciar sesion</button>
                    </form>
                </div>
            </div>
        );
    }
});

export default LoginPage;
