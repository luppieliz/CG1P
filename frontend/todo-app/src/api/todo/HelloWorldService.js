import axios from 'axios'
import { API_URL } from '../../Constants';


class HelloWorldService {
    executeHelloWorldService() {
        return axios.get(`${API_URL}/hello-world`);
    }

    executeHelloWorldBeanService() {
        return axios.get(`${API_URL}/hello-world-bean`);
    }

    executeHelloWorldPathVariableService(name) {
        const token = 'Basic ' + window.btoa(name + "goodpassword")
        return axios.get(`${API_URL}/hello-world/path-variable/${name}`,
        {
            headers: { authorization: 'Basic ' + window.btoa(name + ":" + "goodpassword") }
        });
    }

}

export default new HelloWorldService()