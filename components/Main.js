import React from 'react'
import {Switch, Route} from 'react-router-dom'
import Home from './Home'
import PostForm from './PostForm'

// The Main component renders one of the three provided
// Routes (provided that one matches). Both the /roster
// and /schedule routes will match any pathname that starts
// with /roster or /schedule. The / route will only match
// when the pathname is exactly the string "/"
function Main(props) {
    return (
        <main>
            <Switch>
                <Route exact path='/'
                       render={pps => <Home {...pps} token={props.token}/>}/>
                <Route path='/upload'
                       render={pps => <PostForm {...pps} username={props.username} token={props.token}/>}/>
            </Switch>
        </main>
    );
}

export default Main
