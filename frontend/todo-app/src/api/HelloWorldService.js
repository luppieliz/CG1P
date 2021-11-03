import axios from 'axios'
import { API_URL } from '../Constants';

class HelloWorldService {
    executeHelloWorldService(userName) {
        return axios.get(`${API_URL}/hello-world/${userName}`);
    }
}

export default new HelloWorldService()