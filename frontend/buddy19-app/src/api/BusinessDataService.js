import axios from 'axios'
import {API_URL} from '../Constants';

class BusinessDataService {

    retrieveBusiness(uen) {
        return axios.get(`${API_URL}/business/uen/${uen}`
            //,{headers: { authorization: 'Basic ' + window.btoa(username + ":" + "goodpassword") }}
        );
    }

    createBusiness(business) {
        return axios.post(`${API_URL}/business`, business);
    }

}

export default new BusinessDataService()