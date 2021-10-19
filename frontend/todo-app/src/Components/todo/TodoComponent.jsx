import React, { Component } from 'react'
import moment from 'moment'
import { Form, Formik, Field, ErrorMessage } from 'formik'
import TodoDataService from '../../api/todo/TodoDataService.js'
import AuthenticationService from '../fixed/AuthenticationService.js'
import Container from 'react-bootstrap/Container'
import Row from 'react-bootstrap/Row'
import Col from 'react-bootstrap/Col'
import Card from 'react-bootstrap/Card'
import Button from 'react-bootstrap/Button'
import Placeholder from 'react-bootstrap/Placeholder'

// Page to update or add a specific todo
class TodoComponent extends Component {



    constructor(props) {
        super(props)

        // State of the page - contains id, desc, and date for a specific todo.
        this.state = {
            id: this.props.match.params.id,
            description: '',
            targetDate: moment(new Date()).format('YYYY-MM-DD')

        }

        this.onSubmit = this.onSubmit.bind(this);
        this.validate = this.validate.bind(this);

    }

    // on load of page
    componentDidMount() {


        // if -1 (create), do not try to load todo as it is empty
        if (this.state.id === "-1") {
            return
        }

        let username = AuthenticationService.getLoggedInUserName();

        TodoDataService.retrieveTodo(username, this.state.id)
            .then(
                response => this.setState({
                    description: response.data.description,
                    targetDate: moment(response.data.targetDate).format('YYYY-MM-DD')
                })
            )
    }

    // on Formik Submit
    onSubmit(values) {
        let username = AuthenticationService.getLoggedInUserName();
        let todo = {
            id: this.state.id,
            description: values.description,
            targetDate: values.targetDate
        }

        console.log(this.state.id)

        // if state (todo id) is -1, means todo does not exist yet, means create todo
        if (this.state.id == -1) {
            console.log("id=1")
            TodoDataService.createTodo(username, todo)
                .then(() => this.props.history.push("/todos"))
            // else state (todo id) is not -1, means todo exists, means update todo
        } else {
            TodoDataService.updateTodo(username, this.state.id, todo)
                .then(() => this.props.history.push("/todos"))
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

        if (!moment(values.targetDate).isValid()) {
            errors.targetDate = "Enter a valid target Date"
        }

        return errors
    }

    render() {
        // rely on modern JS destructuring, can assign/retrieve together
        let { description, targetDate } = this.state

        return (
            <div>

                <Container>
                    <Row>
                        <Col></Col>
                        <Col>
                        <Placeholder xs={12} bg="black" />
                        <Placeholder xs={12} bg="black" />
                            <Card>
                                <Placeholder xs={12} bg="light" />
                                <h1 className="text-dark">What are you gonna do today? :)</h1>
                                <div className="container text-dark">
                                    <Card.Body>
                                        <Formik
                                            initialValues={{ description, targetDate }}
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
                                                        <ErrorMessage name="targetDate" component="div" className="alert alert-warning"></ErrorMessage>
                                                        <fieldset className="form-group">
                                                            <label >Description</label>
                                                            <Field className="form-control" type="text" name="description"></Field>
                                                        </fieldset>
                                                        <fieldset className="form-group">
                                                            <label>Target Date</label>
                                                            <Field className="form-control" type="date" name="targetDate"></Field>
                                                        </fieldset>
                                                        <Placeholder xs={12} bg="light" />
                                                        <button className="btn btn-success" type="submit" >Save</button>
                                                        <Placeholder xs={12} bg="light" />
                                                    </Form>
                                                )
                                            }
                                        </Formik>
                                    </Card.Body>
                                </div>
                            </Card>
                        </Col>
                        <Col></Col>
                    </Row>
                </Container>
            </div>
        )
    }
}

export default TodoComponent;