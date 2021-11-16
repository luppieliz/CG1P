import axios from 'axios';
import {API_URL} from '../Constants';

class FaqDataService {
    retrieveAllFAQ() {
        return axios.get(`${API_URL}/faq/retrieveFromDB`);
    }
}

export default new FaqDataService()