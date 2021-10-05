import axios from 'axios'
import { API_URL } from '../../Constants';

// API to facilitate sending HTTP requests to the backend via the Axios dependency
// Uses JPA_API_URL to use JPA database instead of the hardcoded service.

class TodoDataService {
    retrieveAllTodos(username) {
        return axios.get(`${API_URL}/${username}/todos`,{
                                                                    auth: {
                                                                      username: username,
                                                                      password: "goodpassword"
                                                                  }});
    }

    retrieveTodo(username, id) {
        return axios.get(`${API_URL}/${username}/todos/${id}`);
    }

    deleteTodo(username, id) {
        return axios.delete(`${API_URL}/${username}/todos/${id}`);
    }

    updateTodo(username, id, todo) {
        return axios.put(`${API_URL}/${username}/todos/${id}`, todo);
    }

    createTodo(username, todo) {
        return axios.post(`${API_URL}/${username}/todos/-1`,todo);
    }

}

export default new TodoDataService()