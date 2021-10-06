import React, { Component } from 'react';
import TodoDataService from '../../api/todo/TodoDataService';
import AuthenticationService from './AuthenticationService.js'
import moment from 'moment'

// Display a list of all Todos, where the user can manage, add, update, delete todos.
class ListTodosComponent extends Component {

    constructor(props) {
        super(props)
        // State - contains the array of todos, as well as a message for deleteTodo successful
        this.state = {
            todos: [],
            message: null
        }
        this.updateTodoClicked = this.updateTodoClicked.bind(this);
        this.deleteTodoClicked = this.deleteTodoClicked.bind(this);
        this.addTodoClicked = this.addTodoClicked.bind(this);
        this.refreshTodos = this.refreshTodos.bind(this);
    }

    // bring todos from axios get into current state for display, after initial render has been triggered.
    componentDidMount() {
        this.refreshTodos();
    }

    // same as above, but for subsequent refreshes
    refreshTodos() {
        let username = AuthenticationService.getLoggedInUserName();
        TodoDataService.retrieveAllTodos(username)
            .then(
                response => {
                    this.setState({ todos: response.data })
                    // console.log(response)
                }
            )
    }

    // handler for when delete todo is clicked
    deleteTodoClicked(todoId) {
        let username = AuthenticationService.getLoggedInUserName();
        TodoDataService.deleteTodo(username, todoId)
            .then(
                response => {
                    this.setState({ message: `Delete of todo ${todoId} was successful` });
                    this.refreshTodos();
                }
            )
    }

    // handler for when update todo is clicked
    updateTodoClicked(todoId) {
        this.props.history.push(`/todos/${todoId}`);
    }

    // handler for when add todo is clicked
    addTodoClicked() {
        this.props.history.push(`/todos/-1`);
    }

    // jsx render for entire table
    render() {
        return (
            <div  className="text-white">
                <h1>Dashboard</h1>
                {this.state.message && <div className="alert alert-success">{this.state.message}</div>}
                <div className="container">
                    <table className="table">
                        <thead>
                            <tr>
                                <th>Description</th>
                                <th>Is Completed</th>
                                <th>Target Date</th>
                                <th>Update</th>
                                <th>Delete</th>
                            </tr>
                        </thead>
                        <tbody>
                            {
                                // script that for each todo, map it and display id, desc, done, targetDate, and buttons
                                this.state.todos.map(
                                    todo =>
                                        <tr key={todo.id}>
                                            <td>{todo.description}</td>
                                            <td>{todo.done.toString()}</td>
                                            <td>{moment(todo.targetDate).format('YYYY-MM-DD')}</td>
                                            <td><button className="btn btn-success" onClick={() => this.updateTodoClicked(todo.id)}>Update</button></td>
                                            <td><button className="btn btn-warning" onClick={() => this.deleteTodoClicked(todo.id)}>Delete</button></td>
                                        </tr>
                                )
                            }
                        </tbody>
                    </table>
                    <div className = "row">
                        <button className = "btn btn-success" onClick={() => this.addTodoClicked()}>Add</button>
                        </div>
                </div>
            </div>
        )
    }
}

export default ListTodosComponent;