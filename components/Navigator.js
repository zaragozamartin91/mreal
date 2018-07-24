import React from 'react'
import {Link} from 'react-router-dom'

// The Navigator creates links that can be used to navigate
// between routes.
const Navigator = React.createClass({
    render() {
        return (
            <header>
                <nav className="navbar navbar-expand-lg fixed-top ">
                    <a className="navbar-brand" href="#">Mreal</a>
                    <button className="navbar-toggler"
                            type="button"
                            data-toggle="collapse"
                            data-target="#navbarSupportedContent"
                            aria-controls="navbarSupportedContent"
                            aria-expanded="false"
                            aria-label="Toggle navigation">
                        <span className="navbar-toggler-icon"/>
                    </button>

                    <div className="collapse navbar-collapse " id="navbarSupportedContent">
                        <ul className="navbar-nav mr-4">

                            <li className="nav-item">
                                <Link className="nav-link" data-value="about" to="/">Home</Link>
                            </li>
                            <li className="nav-item">
                                <Link className="nav-link " data-value="portfolio" to="/roster">Roster</Link>
                            </li>
                            <li className="nav-item">
                                <Link className="nav-link " data-value="blog" to="/schedule">Schedule</Link>
                            </li>
                        </ul>
                    </div>
                </nav>
            </header>
        );
    }
});

export default Navigator
