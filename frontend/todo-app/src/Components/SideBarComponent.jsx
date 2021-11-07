import React, { Component } from 'react'

import Offcanvas from 'react-bootstrap/Offcanvas'
import Button from 'react-bootstrap/Button'

//for todo
import TodoDataService from '../api/TodoDataService';
import moment from 'moment'
import Card from 'react-bootstrap/Card'
import Container from 'react-bootstrap/Container'
import Row from 'react-bootstrap/Row'
import Col from 'react-bootstrap/Col'
import { SESSION_USER_ID } from '../Constants';
// import Modal from 'react-bootstrap/Modal'

class SideBarComponent extends Component {

    constructor(props) {

        super(props);

        this.state = {
            userId: sessionStorage.getItem(SESSION_USER_ID),
            showSidebar: false,
            showModal: false,
            todos: [],
            message: null
        }
        
        this.handleShow = this.handleShow.bind(this);
        this.handleClose = this.handleClose.bind(this);

        // this.handleShow = this.handleShowModal.bind(this);
        // this.handleClose = this.handleCloseModal.bind(this);

        this.refreshTodos = this.refreshTodos.bind(this);
        this.addTodoClicked = this.addTodoClicked.bind(this);
        this.updateTodoClicked = this.updateTodoClicked.bind(this);
        this.deleteTodoClicked = this.deleteTodoClicked.bind(this);
    }

    componentDidMount() {
        this.setState({ showSidebar: false });
        // this.setState({ showModal: false });
        this.refreshTodos();
    }

    handleClose() { //sidebar
        this.setState({ showSidebar: false });
    }

    handleShow() { //sidebar
        this.setState({ showSidebar: true });
    }

    // handleCloseModal() { //MODAL 
    //     this.setState({ showModal: false });
    // }

    // handleShowModal() { //MODAL
    //     this.setState({ showModal: true });
    // }

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

    render() {
        return (
            <div>
                <Container>
                    <Button variant="primary" onClick={this.handleShow}>
                        TODOS
                    </Button>
                    <Offcanvas show={this.state.showSidebar} onHide={this.handleClose}>
                        <Offcanvas.Header closeButton>
                            <Offcanvas.Title className="text-dark">My Tasks</Offcanvas.Title>
                        </Offcanvas.Header>
                        <Offcanvas.Body className="text-dark">
                            <Row>
                                <Col>

                                    <Card className="text-dark">
                                        {/* <Card.Img variant="top" src="holder.js/100px180" /> */}
                                        <Card.Body>
                                            <Card.Text>
                                                <h1>Todos</h1>
                                            </Card.Text>
                                        </Card.Body>
                                    </Card>
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
                                                                <td><button className="btn btn-success" onClick={() => this.updateTodoClicked(todo.id)}>Update</button></td>
                                                                <td><button className="btn btn-warning" onClick={() => this.deleteTodoClicked(todo.id)}>Delete</button></td>
                                                            </tr>
                                                    )
                                                }
                                            </tbody>
                                        </table>
                                        <div className="row"><button className="btn btn-success" onClick={() => this.addTodoClicked()}>Add</button></div>
                                    </div>
                                </Col>
                            </Row>
                        </Offcanvas.Body>
                    </Offcanvas>
                </Container>

                {/* <Button variant="primary" onClick={this.handleShowModal}>
                    Launch demo modal
                </Button>

                <Modal show ={this.state.showModal} onHide={this.handleCloseModal}>
                    <Modal.Header closeButton>
                        <Modal.Title>Modal heading</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>Woohoo, you're reading this text in a modal!</Modal.Body>
                    <Modal.Footer>
                        <Button variant="secondary" onClick={this.handleCloseModal}>
                            Close
                        </Button>
                        <Button variant="primary" onClick={this.handleCloseModal}>
                            Save Changes
                        </Button>
                    </Modal.Footer>
                </Modal> */}
            </div>
        )
    }

}

export default SideBarComponent;