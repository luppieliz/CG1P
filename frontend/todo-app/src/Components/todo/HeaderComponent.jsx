import React, {Component} from 'react'
// import routing features
import {Link} from 'react-router-dom'
// auth service
import AuthenticationService from '../../api/todo/AuthenticationService.js'
// import withRouter to wrap - auto update header based on auth
import {withRouter} from 'react-router'

// Header with navigation features as a navbar. Shows different things based on whether user is logged in.
class HeaderComponent extends Component {

    render() {
        const isUserLoggedIn = AuthenticationService.isUserLoggedIn();
        return (
            <header>
                <nav className="navbar navbar-expand-md navbar-dark bg-dark">
                    <div><a href="/" className="navbar-brand">COVby</a></div>
                    <ul className="navbar-nav">
                        {isUserLoggedIn && <li><Link className="nav-link" to="/welcome">Home</Link></li>}
                        {!isUserLoggedIn && <li><Link className="nav-link" to="/about">About</Link></li>}
                        {isUserLoggedIn && <li><Link className="nav-link" to="/todos">Dashboard</Link></li>}
                        {!isUserLoggedIn && <li><Link className="nav-link" to="/faq">faq</Link></li>}
                        {isUserLoggedIn && <li><Link className="nav-link" to="/faq">faq</Link></li>}
                        
                    </ul>
                    <ul className="navbar-nav navbar-collapse justify-content-end">
                        {isUserLoggedIn && <li><Link className="nav-link" to="/todos">Profile</Link></li>}
                        {!isUserLoggedIn && <li><Link className="nav-link" to="/login">Login</Link></li>}
                        {isUserLoggedIn && <li><Link className="nav-link" to="/logout" onClick={AuthenticationService.logout}>Logout</Link></li>} 
                        {/* link for profile, news not implemented */}
                    </ul>
                </nav>
            </header>
        )
    }
}

export default withRouter(HeaderComponent)