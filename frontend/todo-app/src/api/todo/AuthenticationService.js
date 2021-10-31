import axios from 'axios'
import { API_URL, SESSION_EMAIL, SESSION_TOKEN } from '../../Constants'

// Service for keeping track of currently authenticated user
// Also facilitates retrieval of currently logged in email
class AuthenticationService {

    // Log out
    logout() {
        sessionStorage.removeItem(SESSION_EMAIL)
    }

    // Check if user is logged in
    isUserLoggedIn() {
        let user = sessionStorage.getItem(SESSION_EMAIL)
        console.log('logged in: %s', user)
        if (user === null) return false
        return true
    }

    // Retrieve Current Email
    getLoggedInEmail() {
        let user = sessionStorage.getItem(SESSION_EMAIL)
        if (user === null) return ''
        return user
    }

    // CODE FOR BASIC AUTH
    executeBasicAuthenticationService(email, password) {
        return axios.get(`${API_URL}/basicauth`,
            { headers: { authorization: this.createBasicAuthToken(email, password) } })
    }

    createBasicAuthToken(email, password) {
        let token = 'Basic ' + window.btoa(email + ':' + password)
        sessionStorage.setItem(SESSION_TOKEN, token)
        console.log('token: %s', token)
        return token
    }

    registerSuccessfulLoginForBasicAuth(email, password) {
        sessionStorage.setItem(SESSION_EMAIL, email)
        this.createBasicAuthToken(email, password)
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
}

export default new AuthenticationService()