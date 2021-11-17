import axios from 'axios'
import {API_URL, SESSION_INTERCEPTOR, SESSION_USER_ID} from '../Constants'

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
}

export default new AuthenticationService()
