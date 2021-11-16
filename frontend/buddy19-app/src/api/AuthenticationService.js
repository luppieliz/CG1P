import axios from 'axios'
import { API_URL, SESSION_INTERCEPTOR, SESSION_TOKEN, SESSION_USER_ID } from '../Constants'

// Service for keeping track of currently authenticated user
// Also facilitates retrieval of currently logged in email
class AuthenticationService {

    // Log out
    logout() {
        axios.interceptors.request.eject(sessionStorage.getItem(SESSION_INTERCEPTOR))
        sessionStorage.clear()
    }

    // Check if user is logged in
    isUserLoggedIn() {
        let userId = sessionStorage.getItem(SESSION_USER_ID)
        console.log('logged in: %s', userId)
        if (userId === null) return false
        return true
    }

    executeJwtAuthenticationService(username, password) {
        return axios.post(`${API_URL}/authenticate`, {
            username,
            password
        })
    }

    registerSuccessfulLoginForJwt(email) {
        return axios.get(`${API_URL}/user/email/${email}`)
    }
    
    
    // CODE FOR BASIC AUTH
    // executeBasicAuthenticationService(email, password) {
    //     return axios.get(`${API_URL}/basicauth`,
    //         { headers: { authorization: this.createBasicAuthToken(email, password) } })
    // }

    // createBasicAuthToken(email, password) {
    //     let token = 'Basic ' + window.btoa(email + ':' + password)
    //     sessionStorage.setItem(SESSION_TOKEN, token)
    //     console.log('token: %s', token)
    //     return token
    // }

    // registerSuccessfulLoginForBasicAuth(email) {
    //     return axios.get(`${API_URL}/user/email/${email}`)
    // }

    // BELOW CODE IS SPECIFICALLY FOR JWT AUTH

    /*
        execute authentication
    */


}

export default new AuthenticationService()
