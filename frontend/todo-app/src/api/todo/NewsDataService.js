import axios from 'axios';
import { API_URL } from '../../Constants';

class NewsDataService {
    retrieveAllNews() {
        return axios.get(`${API_URL}/newsapi/newsdb/all`);
    }

    retrieveNewsWithTags(tags) {
        return axios.get(`${API_URL}/newsdb/${tags}`);
        // return axios.get(`${API_URL}/newsapi/sg/covid`);
    }
}

export default new NewsDataService()