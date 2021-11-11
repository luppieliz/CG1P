import axios from 'axios';
import { API_URL } from '../Constants';

class NewsDataService {
    retrieveAllNews() {
        return axios.get(`${API_URL}/newsapi/newsdb/all`);
    }

    //TODO implement retrieve by date limit
    // retrieveNewsByDate(date) {
    //     return axios.get(`${API_URL}/newsapi/newsdb/${date}`);
    // }

    triggerNewsApi() {
    // return axios.get(`${API_URL}/newsapi/sg/covid`);
        return axios.get(`${API_URL}/newsapi/channelnewsasia.com,straitstimes.com/covid+singapore/2021-11-40`);
}
}

export default new NewsDataService()