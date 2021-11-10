import axios from 'axios'
import { API_URL } from '../Constants'

// API to facilitate sending HTTP requests to the backend via the Axios dependency
class TodoDataService {
    retrieveCreatedTodos(userId) {
        return axios.get(`${API_URL}/${userId}/todos/created`)
    }

    retrieveAssignedTodos(userId) {
        return axios.get(`${API_URL}/${userId}/todos/assigned`)
    }

    retrieveTodo(userId, id) {
        return axios.get(`${API_URL}/${userId}/todos/${id}`)
    }

    createTodo(userId, todo) {
        return axios.post(`${API_URL}/${userId}/todos`, todo)
    }

    updateTodo(userId, id, todo) {
        return axios.put(`${API_URL}/${userId}/todos/${id}`, todo)
    }

    deleteTodo(userId, id) {
        return axios.delete(`${API_URL}/${userId}/todos/${id}`)
    }
}

export default new TodoDataService()