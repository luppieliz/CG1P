import axios from 'axios'
import {API_URL} from '../Constants';

class IndustryDataService {

    retrieveAllIndustries() {
        return axios.get(`${API_URL}/industry`);
    }

    retrieveIndustry(industryName) {
        return axios.get(`${API_URL}/industry/name/${industryName}`
            //,{headers: { authorization: 'Basic ' + window.btoa(username + ":" + "goodpassword") }}
        );
    }

}

export default new IndustryDataService()