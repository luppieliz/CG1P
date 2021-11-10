import React, { Component } from 'react'
import moment from 'moment'
import { Form, Formik, Field, ErrorMessage } from 'formik'
import TodoDataService from '../api/TodoDataService.js'
import Container from 'react-bootstrap/Container'
import Row from 'react-bootstrap/Row'
import Col from 'react-bootstrap/Col'
import { SESSION_USER_BUSINESS, SESSION_USER_ID } from '../Constants.js'
import UserDataService from '../api/UserDataService.js'
import Multiselect from 'multiselect-react-dropdown'

// Page to update or add a specific todo
class TodoComponent extends Component {

    constructor(props) {
        super(props)

        // State of the page - contains id, desc, and date for a specific todo.
        this.state = {
            userId: sessionStorage.getItem(SESSION_USER_ID),
            id: this.props.match.params.id,
            description: '',
            listItems: [],
            selectedValues: [],
            employees: []
        }

        this.onSelect = this.onSelect.bind(this)
        this.onRemove = this.onRemove.bind(this)
        this.onSubmit = this.onSubmit.bind(this)
        this.validate = this.validate.bind(this)
    }

    // on load of page
    componentDidMount() {

        UserDataService.retrieveUsersByBusiness(sessionStorage.getItem(SESSION_USER_BUSINESS))
            .then(response => {
                this.setState({
                    listItems: response.data.map(employee => ({
                        name: employee.name + '  (' + employee.email + ')',
                        value: employee.id
                    }))
                })
            })

        // if -1 (create), do not try to load todo as it is empty
        if (this.state.id === "-1") {
            return
        }

        TodoDataService.retrieveTodo(this.state.userId, this.state.id)
            .then(response => this.setState({
                description: response.data.description,
                selectedValues: response.data.createdFor.map(employee => ({
                    name: employee.name + '  (' + employee.email + ')',
                    value: employee.id
                })),
                employees: response.data.createdFor.map(employee => 
                    employee.id
                )
            }))

        console.log(this.state.employees)
    }

    // for employee dropdown
    onSelect(selectedList, selectedItem) {
        this.state.employees.push(selectedItem.value)
        console.log(this.state.employees)
    }

    // for employee dropdown
    onRemove(selectedList, removedItem) {
        const index = this.state.employees.indexOf(removedItem.value)
        this.state.employees.splice(index, 1)
        console.log(this.state.employees)
    }

    // on Formik Submit
    onSubmit(values) {
        let todo = {
            description: values.description,
            createdForIds: this.state.employees
        }

        console.log(todo)

        // if state (todo id) is -1, means todo does not exist yet, means create todo
        if (this.state.id == -1) {
            TodoDataService.createTodo(this.state.userId, todo)
                .then(() => this.props.history.push("/welcome"))
            // else state (todo id) is not -1, means todo exists, means update todo
        } else {
            TodoDataService.updateTodo(this.state.userId, this.state.id, todo)
                .then(() => this.props.history.push("/welcome"))
        }
    }

    // on Formik Validate call
    // if errors populated, will not call onSubmit above
    validate(values) {
        let errors = {}

        if (!values.description) {
            errors.description = "Enter a description"
        } else if (values.description.length < 5) {
            errors.description = "Enter at least 5 characters for description"
        }

        return errors
    }

    render() {
        // rely on modern JS destructuring, can assign/retrieve together
        let { description } = this.state

        return (
            <div>
                <Col></Col>
                <Col></Col>
                <Col></Col>
                <Col>
                    <Container>
                        <Row>
                            <h1 className="text-dark">Todo</h1>
                            <div className="container text-dark ">
                                <Formik
                                    initialValues={{ description }}
                                    onSubmit={this.onSubmit}
                                    validateOnChange={false}
                                    validateOnBlur={false}
                                    validate={this.validate}
                                    enableReinitialize={true}
                                >
                                    {
                                        (props) => (
                                            <Form>
                                                <ErrorMessage name="description" component="div" className="alert alert-warning "></ErrorMessage>
                                                <fieldset className="form-group">
                                                    <label >Description</label>
                                                    <Field className="form-control" type="text" name="description"></Field>
                                                </fieldset>
                                                <fieldset className="form-group">
                                                    <label >Assign To</label>
                                                    <Multiselect
                                                        options={this.state.listItems}
                                                        selectedValues={this.state.selectedValues}
                                                        onSelect={this.onSelect}
                                                        onRemove={this.onRemove}
                                                        displayValue="name"
                                                        showCheckbox
                                                        closeOnSelect={false}
                                                        placeholder="Select employees"
                                                        hidePlaceholder
                                                    />
                                                </fieldset>
                                                <button className="btn btn-success" type="submit" >Save</button>
                                            </Form>
                                        )
                                    }
                                </Formik>
                            </div>
                        </Row>
                    </Container>
                </Col>
                <Col></Col>
                <Col></Col>
                <Col></Col>
            </div>
        )
    }
}

export default TodoComponent;