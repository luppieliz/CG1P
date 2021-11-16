import React, {Component} from 'react';
import {Redirect, Route} from 'react-router-dom';
import axios from 'axios'
import AuthenticationService from '../api/AuthenticationService.js';
import {SESSION_INTERCEPTOR, SESSION_TOKEN} from '../Constants.js';

// Ensures user is authenticated before granting access to a route, else redirect to login
class AuthenticatedRoute extends Component {

    // intercept HTTP requests and include token
    setupAxiosInterceptors() {
        const interceptor = axios.interceptors.request.use(
            config => {
                if (AuthenticationService.isUserLoggedIn()) {
                    console.log('isloggedin triggered from interceptor')
                    config.headers.authorization = sessionStorage.getItem(SESSION_TOKEN)
                }
                return config
            }
        )
        sessionStorage.setItem(SESSION_INTERCEPTOR, interceptor)
    }

    componentDidMount() {
        this.setupAxiosInterceptors()
    }

    render() {
        if (AuthenticationService.isUserLoggedIn()) {
            console.log('isloggedin triggered from route render')
            // spread operator - take all properties
            return <Route {...this.props} />
        } else {
            return <Redirect to="/login"/>
        }
    }
}

export default AuthenticatedRoute