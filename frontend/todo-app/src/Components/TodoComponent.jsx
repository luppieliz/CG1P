import React, { Component } from 'react'
import moment from 'moment'
import { Form, Formik, Field, ErrorMessage } from 'formik'
import TodoDataService from '../api/TodoDataService.js'
import Container from 'react-bootstrap/Container'
import Row from 'react-bootstrap/Row'
import Col from 'react-bootstrap/Col'
import { SESSION_USER_ID } from '../Constants.js'

// Page to update or add a specific todo
class TodoComponent extends Component {

    constructor(props) {
        super(props)

        // State of the page - contains id, desc, and date for a specific todo.
        this.state = {
            userId: sessionStorage.getItem(SESSION_USER_ID),
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

        TodoDataService.retrieveTodo(this.state.userId, this.state.id)
            .then(response => this.setState({
                description: response.data.description,
                targetDate: moment(response.data.targetDate).format('YYYY-MM-DD')
            }))
    }

    // on Formik Submit
    onSubmit(values) {
        let todo = {
            id: this.state.id,
            description: values.description,
            targetDate: values.targetDate
        }

        console.log(this.state.id)

        // if state (todo id) is -1, means todo does not exist yet, means create todo
        if (this.state.id == -1) {
            console.log("id=1")
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
                <Col></Col>
                <Col></Col>
                <Col></Col>
                <Col>
                    <Container>
                        <Row>
                            <h1 className="text-dark">Todo</h1>
                            <div className="container text-dark ">
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