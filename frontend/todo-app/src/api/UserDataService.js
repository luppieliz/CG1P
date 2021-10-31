import axios from 'axios'
import { API_URL } from '../Constants';

class UserDataService {
    
    createUser(user) {
        return axios.post(`${API_URL}/user`, user
                //,{headers: { authorization: 'Basic ' + window.btoa(username + ":" + "goodpassword") }}
        );
    }

}

export default new UserDataService()