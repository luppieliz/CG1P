import axios from 'axios';
import { API_URL } from '../../Constants';

class NewsDataService {
    retrieveAllNews() {
        //todo replace with call to database
        return axios.get(`${API_URL}/newsapi/sg/covid`);
    }
}

export default new NewsDataService()