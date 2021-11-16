import axios from 'axios'
import {API_URL} from '../Constants';

class UserDataService {

    createUser(user) {
        return axios.post(`${API_URL}/user`, user
            //,{headers: { authorization: 'Basic ' + window.btoa(username + ":" + "goodpassword") }}
        );
    }

    retrieveUser(id) {
        return axios.get(`${API_URL}/user/${id}`
            //,{headers: { authorization: 'Basic ' + window.btoa(username + ":" + "goodpassword") }}
        );
    }

    retrieveUsersByBusiness(businessId) {
        return axios.get(`${API_URL}/user/business/${businessId}`
            //,{headers: { authorization: 'Basic ' + window.btoa(username + ":" + "goodpassword") }}
        );
    }

    updateUser(user) {
        return axios.put(`${API_URL}/user`, user
            //,{headers: { authorization: 'Basic ' + window.btoa(username + ":" + "goodpassword") }}
        );
    }

}

export default new UserDataService()