import React, { Component } from 'react'
import TodoDataService from '../api/TodoDataService'
import moment from 'moment'
import Placeholder from 'react-bootstrap/Placeholder'
import Container from 'react-bootstrap/Container'
import Row from 'react-bootstrap/Row'
import Col from 'react-bootstrap/Col'
import { SESSION_USER_ID } from '../Constants'
import Card from 'react-bootstrap/Card';
import { GrAdd } from "@react-icons/all-files/gr/GrAdd";
import { FaTrashAlt } from "@react-icons/all-files/fa/FaTrashAlt";
import { GrUpdate } from "@react-icons/all-files/gr/GrUpdate";


// Display a list of all Todos, where the user can manage, add, update, delete todos.
class ListTodosComponent extends Component {

    constructor(props) {
        super(props)
        // State - contains the array of todos, as well as a message for deleteTodo successful
        this.state = {
            userId: sessionStorage.getItem(SESSION_USER_ID),
            todos: [],
            message: null,
            show : "false",
            setShow : "false"
        }

        this.refreshTodos = this.refreshTodos.bind(this)
        this.addTodoClicked = this.addTodoClicked.bind(this)
        this.updateTodoClicked = this.updateTodoClicked.bind(this)
        this.deleteTodoClicked = this.deleteTodoClicked.bind(this)

        this.handleShow = this.handleShowModal.bind(this);
        this.handleClose = this.handleCloseModal.bind(this);
    }

    // bring todos from axios get into current state for display, after initial render has been triggered.
    componentDidMount() {
        this.refreshTodos()
        this.setState({ showModal: false });
    }

    handleCloseModal() { //MODAL 
        this.setState({ showModal: false });
    }

    handleShowModal() { //MODAL
        this.setState({ showModal: true });
    }

    // same as above, but for subsequent refreshes
    refreshTodos() {
        TodoDataService.retrieveAllTodos(this.state.userId)
            .then(response => this.setState({ todos: response.data }))
    }

    // handler for when add todo is clicked
    addTodoClicked() {
        this.props.history.push(`/todos/-1`)
    }

    // handler for when update todo is clicked
    updateTodoClicked(todoId) {
        this.props.history.push(`/todos/${todoId}`)
    }

    // handler for when delete todo is clicked
    deleteTodoClicked(todoId) {
        TodoDataService.deleteTodo(this.state.userId, todoId)
            .then(() => {
                this.setState({ message: `Delete of todo ${todoId} was successful` })
                this.refreshTodos()
            })
    }

    // jsx render for entire table
    render() {
        return (
            <div>
                    <Card>
                        <Col>
                            <div className="text-dark">{this.state.message && <div className="alert alert-success">{this.state.message}</div>}
                                <table className="table text-dark">
                                    <thead>
                                        <tr>
                                            <th>Task</th>
                                            <th>Done?</th>
                                            <th>Due</th>
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
                                                        <td>{todo.isDone.toString()}</td>
                                                        <td>{moment(todo.targetDate).format('YYYY-MM-DD')}</td>
                                                        <td><button className="btn btn-warning" onClick={() => this.updateTodoClicked(todo.id)}><GrUpdate/></button></td>
                                                        <td><button className="btn btn-danger" onClick={() => this.deleteTodoClicked(todo.id)}><FaTrashAlt/></button></td>
                                                    </tr>
                                            )
                                        }
                                    </tbody>
                                </table>
                                <div className="d-grid gap-2"><button className="btn btn-success" onClick={() => this.handleShowModal()}><GrAdd/></button></div>
                            </div>
                        </Col>
                        </Card>
                        <Placeholder xs={12} bg="transparent" />
            </div>
        )
    }
}

export default ListTodosComponent