import React, {Component} from 'react';
import { Route, Redirect } from 'react-router-dom';
import AuthenticationService from './AuthenticationService.js';

// Ensures user is authenticated before granting access to a route, else redirect to login
class AuthenticatedRoute extends Component {
    render() {
        if(AuthenticationService.isUserLoggedIn()) {
            // spread operator - take all properties
            return <Route {...this.props}/>
        } else {
            return  <Redirect to ="/login"/>
        }
    }
}

export default AuthenticatedRoute;