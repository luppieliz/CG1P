import axios from 'axios'
import {QUOTE_SOURCE} from '../Constants';

class QuoteService {
    executeQuoteService() {
        return axios.get(`${QUOTE_SOURCE}`);
    }
}

export default new QuoteService()