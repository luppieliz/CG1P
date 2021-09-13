import axios from 'axios'
import {JPA_API_URL } from '../../Constants';

// API to facilitate sending HTTP requests to the backend via the Axios dependency
// Uses JPA_API_URL to use JPA database instead of the hardcoded service.

class TodoDataService {
    retrieveAllTodos(name) {
        return axios.get(`${JPA_API_URL}/users/${name}/todos`);
    }

    retrieveTodo(name, id) {
        return axios.get(`${JPA_API_URL}/users/${name}/todos/${id}`);
    }

    deleteTodo(name, id) {
        return axios.delete(`${JPA_API_URL}/users/${name}/todos/${id}`);
    }

    updateTodo(name, id, todo) {
        return axios.put(`${JPA_API_URL}/users/${name}/todos/${id}`, todo);
    }


    createTodo(name, todo) {
        return axios.post(`${JPA_API_URL}/users/${name}/todos`);
    }

}

export default new TodoDataService()