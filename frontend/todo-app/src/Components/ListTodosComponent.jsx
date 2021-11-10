import React, { Component } from 'react'
import TodoDataService from '../api/TodoDataService'
import moment from 'moment'
import Col from 'react-bootstrap/Col'
import { SESSION_USER_ID } from '../Constants'
import Card from 'react-bootstrap/Card';
import { GrAdd } from "@react-icons/all-files/gr/GrAdd";
import { FaTrashAlt } from "@react-icons/all-files/fa/FaTrashAlt";
import { GrUpdate } from "@react-icons/all-files/gr/GrUpdate";
import Offcanvas from 'react-bootstrap/Offcanvas'
import Button from 'react-bootstrap/Button'
import Container from 'react-bootstrap/Container'
import Row from 'react-bootstrap/Row'



// Display a list of all Todos, where the user can manage, add, update, delete todos.
class ListTodosComponent extends Component {

    constructor(props) {
        super(props)
        // State - contains the array of todos, as well as a message for deleteTodo successful
        this.state = {
            userId: sessionStorage.getItem(SESSION_USER_ID),
            todos: [],
            message: null,
            showSidebar: false
        }
        this.refreshTodos = this.refreshTodos.bind(this)
        this.addTodoClicked = this.addTodoClicked.bind(this)
        this.updateTodoClicked = this.updateTodoClicked.bind(this)
        this.deleteTodoClicked = this.deleteTodoClicked.bind(this)

        this.handleShow = this.handleShow.bind(this);
        this.handleClose = this.handleClose.bind(this);
    }
    // bring todos from axios get into current state for display, after initial render has been triggered.
    componentDidMount() {
        this.refreshTodos()
        this.setState({ showSidebar: false });
    }

    handleClose() { //sidebar
        this.setState({ showSidebar: false });
    }

    handleShow() { //sidebar
        this.setState({ showSidebar: true });
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
                <Container>
                    <Button variant="primary" onClick={this.handleShow}>
                        TODOS
                    </Button>
                    <Offcanvas show={this.state.showSidebar} onHide={this.handleClose}>
                        <Offcanvas.Header closeButton>
                            <Offcanvas.Title className="text-dark">Checklist</Offcanvas.Title>
                        </Offcanvas.Header>
                        <Offcanvas.Body className="text-dark">
                            <Row>
                                <Col>

                                    <Card className="text-dark">
                                        {/* <Card.Img variant="top" src="holder.js/100px180" /> */}
                                        <Card.Body>
                                            <Card.Text>
                                            <iframe src="https://free.timeanddate.com/clock/i81r8hlp/n236/tlsg/fn6/fs20/fcfff/tc000/ftb/bacfff/pa8/tt0/tw1/th2/ta1/tb4" frameborder="0" width="333" height="68"></iframe>
                                            </Card.Text>
                                        </Card.Body>
                                    </Card>
                                    <div className="text-dark">{this.state.message && <div className="alert alert-success">{this.state.message}</div>}
                                        <table className="table text-dark">
                                            <thead>
                                                <tr>
                                                    <th>Task</th>
                                                    <th>Done?</th>
                                                    <th>Date Created</th>
                                                    <th>Update</th>
                                                    <th>Delete</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                {
                                                    // script that for each todo, map it and display id, desc, done, createdDate, and buttons
                                                    this.state.todos.map(
                                                        todo =>
                                                            <tr key={todo.id}>
                                                                <td>{todo.description}</td>
                                                                <td>{todo.isDone.toString()}</td>
                                                                <td>{moment(todo.createdDate).format('YYYY-MM-DD')}</td>
                                                                <td><button className="btn btn-warning" onClick={() => this.updateTodoClicked(todo.id)}><GrUpdate/></button></td>
                                                                <td><button className="btn btn-danger" onClick={() => this.deleteTodoClicked(todo.id)}><FaTrashAlt/></button></td>
                                                            </tr>
                                                    )
                                                }
                                            </tbody>
                                        </table>
                                        <div className="d-grid gap-2"><button className="btn btn-success" onClick={() => this.addTodoClicked()}><GrAdd/></button></div>
                                    </div>
                                </Col>
                            </Row>
                        </Offcanvas.Body>
                    </Offcanvas>
                </Container>
            </div>
        )
    }
}

export default ListTodosComponent