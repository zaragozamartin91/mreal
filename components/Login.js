import React from 'react';
import ReactDOM from 'react-dom';

const metaMsg = document.querySelector('meta[name=message]') || {content: ''};
const message = metaMsg.content;

const LoginPage = React.createClass({
    render() {
        return (<div className={"container"}>
            <h1>Iniciar sesion</h1>

            <p className="bg-danger">{message}</p>

            <form method={"POST"} action={"/login"}>
                <div className="form-group">
                    <label htmlFor="exampleInputEmail1">Username</label>
                    <input type="email" className="form-control" id="exampleInputEmail1" name={"username"}
                           aria-describedby="emailHelp" placeholder="Nombre de usuario"/>
                </div>

                <div className="form-group">
                    <label htmlFor="exampleInputPassword1">Password</label>
                    <input type="password" className="form-control" id="exampleInputPassword1" name={"password"}
                           placeholder="Password"/>
                </div>

                <button type="submit" className="btn btn-primary">Iniciar sesion</button>
            </form>
        </div>)
    }
});

ReactDOM.render(
    <LoginPage/>,
    document.getElementById('root')
);
