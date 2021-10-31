import React, { Component } from 'react';
import { Route, Redirect } from 'react-router-dom';
import axios from 'axios'
import AuthenticationService from '../api/AuthenticationService.js';
import { SESSION_TOKEN } from '../Constants.js';

// Ensures user is authenticated before granting access to a route, else redirect to login
class AuthenticatedRoute extends Component {

    // intercept HTTP requests and include token
    setupAxiosInterceptors() {
        axios.interceptors.request.use(
            config => {
                if (AuthenticationService.isUserLoggedIn()) {
                    console.log('interceptor setup triggered')
                    config.headers.authorization = sessionStorage.getItem(SESSION_TOKEN)
                }
                return config
            }
        )
    }

    componentDidMount() {
        this.setupAxiosInterceptors()
    }

    render() {
        if (AuthenticationService.isUserLoggedIn()) {
            // spread operator - take all properties
            return <Route {...this.props} />
        } else {
            return <Redirect to="/login" />
        }
    }
}

export default AuthenticatedRoute