import axios from 'axios'
import { API_URL } from '../../Constants';

export const USER_NAME_SESSION_ATTRIBUTE_NAME = 'authenticatedUser'

// Service for keeping track of currently authenticated user
// Also facilitates retrieval of currently logged in username
class AuthenticationService {

    // Log Out
    logout() {
        sessionStorage.removeItem(USER_NAME_SESSION_ATTRIBUTE_NAME);
    }

    // Is User Logged In
    isUserLoggedIn() {
        let user = sessionStorage.getItem(USER_NAME_SESSION_ATTRIBUTE_NAME);
        if (user === null) return false
        return true
    }

    // Retrieve Current User Name
    getLoggedInUserName() {
        let user = sessionStorage.getItem(USER_NAME_SESSION_ATTRIBUTE_NAME);
        if (user === null) return ''
        return user
    }

    // CODE FOR BASIC AUTH
    executeBasicAuthenticationService(username, password) {
            return axios.get(`${API_URL}/basicauth`
            , {
                username,
                password
            })
            
    }

    createBasicAuthToken(username, password) {
            return 'Basic ' + window.btoa(username + ":" + password)
    }

    registerSuccessfulLoginForBasicAuth(username, password) {
            sessionStorage.setItem(USER_NAME_SESSION_ATTRIBUTE_NAME, username)
            console.log("setting up axios interceptors")
            this.setupAxiosInterceptors(this.createBasicAuthToken(username,password))
            console.log("token %s", this.createBasicAuthToken(username,password))
    }

    // BELOW CODE IS SPECIFICALLY FOR JWT AUTH
//    executeJwtAuthenticationService(username, password) {
//        return axios.post(`${API_URL}/authenticate`, {
//            username,
//            password
//        })
//    }
//
//    registerSuccessfulLoginForJwt(username, token) {
//        sessionStorage.setItem(USER_NAME_SESSION_ATTRIBUTE_NAME, username)
//        this.setupAxiosInterceptors(this.createJWTToken(token))
//    }
//
//    createJWTToken(token) {
//        return 'Bearer ' + token
//    }

    // intercept HTTP requests and include token
    setupAxiosInterceptors(token) {

        axios.interceptors.request.use(
            (config) => {

                if (this.isUserLoggedIn()) {
                    config.headers.authorization = token
                }

                return config;
            }
        )
    }
}

export default new AuthenticationService();