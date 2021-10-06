import axios from 'axios'
import { API_URL } from '../../Constants';

// API to facilitate sending HTTP requests to the backend via the Axios dependency

class TodoDataService {
    retrieveAllTodos(username) {
        return axios.get(`${API_URL}/${username}/todos`
        //,{headers: { authorization: 'Basic ' + window.btoa(username + ":" + "goodpassword") }}
            );
    } 

    // executeHelloWorldPathVariableService(name) {
    //     const token = 'Basic ' + window.btoa(name + "goodpassword")
    //     return axios.get(`${API_URL}/hello-world/path-variable/${name}`,
    //     {
    //         headers: { authorization: 'Basic ' + window.btoa(name + ":" + "goodpassword") }
    //     });
    // }

    retrieveTodo(username, id) {
        return axios.get(`${API_URL}/${username}/todos/${id}`
                //,{headers: { authorization: 'Basic ' + window.btoa(username + ":" + "goodpassword") }}
            );
    }

    deleteTodo(username, id) {
        return axios.delete(`${API_URL}/${username}/todos/${id}`        
        //,{headers: { authorization: 'Basic ' + window.btoa(username + ":" + "goodpassword") }}
        );
    }

    updateTodo(username, id, todo) {
        return axios.put(`${API_URL}/${username}/todos/${id}`, todo        
        //,{headers: { authorization: 'Basic ' + window.btoa(username + ":" + "goodpassword") }}
        );
    }

    createTodo(username, todo) {
        return axios.post(`${API_URL}/${username}/todos`,todo
                //,{headers: { authorization: 'Basic ' + window.btoa(username + ":" + "goodpassword") }}
        );
    }

}

export default new TodoDataService()