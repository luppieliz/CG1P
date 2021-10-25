import React, {Component} from 'react'
// import routing features
import {Link} from 'react-router-dom'
// auth service
import AuthenticationService from './AuthenticationService.js'
// import withRouter to wrap - auto update header based on auth
import {withRouter} from 'react-router';
import DropdownButton from 'react-bootstrap/DropdownButton';
import Nav from 'react-bootstrap/Nav';
import NavDropdown from 'react-bootstrap/NavDropdown';


// Header with navigation features as a navbar. Shows different things based on whether user is logged in.
class HeaderComponent extends Component {
    render() {
        const isUserLoggedIn = AuthenticationService.isUserLoggedIn();
        return (
            <header>
                <nav className="navbar navbar-expand-md navbar-dark bg-dark">
                    <h4><a href="/" className="navbar-brand">Buddy-19</a></h4>
                    <ul className="navbar-nav">
                        {isUserLoggedIn && <li><Link className="nav-link" to="/welcome/admin">Home</Link></li>}
                        {isUserLoggedIn && <li><Link className="nav-link" to="/todos">Dashboard</Link></li>}
                        {!isUserLoggedIn && <li><Link className="nav-link" to="/news">News</Link></li>}
                        {isUserLoggedIn && <li><Link className="nav-link" to="/news">News</Link></li>}

                        <NavDropdown
                            id="nav-dropdown-dark-example"
                            title="Questions"
                            menuVariant="dark"
                            >
                            {!isUserLoggedIn && <NavDropdown.Item ><Link className="nav-link" to="/faq">FAQ</Link></NavDropdown.Item>}
                            {isUserLoggedIn && <NavDropdown.Item ><Link className="nav-link" to="/faq">FAQ</Link></NavDropdown.Item>}
                            {!isUserLoggedIn && <NavDropdown.Item ><Link className="nav-link" to="/aboutus">About Us</Link></NavDropdown.Item>}
                            {isUserLoggedIn && <NavDropdown.Item ><Link className="nav-link" to="/aboutus">About Us</Link></NavDropdown.Item>}
                            {!isUserLoggedIn && <NavDropdown.Item ><Link className="nav-link" to="/contactus">Contact Us</Link></NavDropdown.Item>}
                            {isUserLoggedIn && <NavDropdown.Item ><Link className="nav-link" to="/contactus">Contact Us</Link></NavDropdown.Item>}
                            
                            </NavDropdown>
                        
                        
                    </ul>
                    <ul className="navbar-nav navbar-collapse justify-content-end">
                        {isUserLoggedIn && <li><Link className="nav-link" to="/mylist">mylist</Link></li>}
                        {isUserLoggedIn && <li><Link className="nav-link" to="/profile">Profile</Link></li>}
                        {!isUserLoggedIn && <li><Link className="nav-link" to="/login">Login</Link></li>}
                        {isUserLoggedIn && <li><Link className="nav-link" to="/logout" onClick={AuthenticationService.logout}>Logout</Link></li>} 
                        {/* link for profile, news not implemented */}
                        
                    </ul>
                </nav>
            </header>
        )
    }
}

export default withRouter(HeaderComponent);