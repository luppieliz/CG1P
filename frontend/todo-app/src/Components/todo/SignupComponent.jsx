import React, { Component } from 'react'
import { Form, Formik, Field, ErrorMessage } from 'formik'
import Container from 'react-bootstrap/Container'
import Row from 'react-bootstrap/Row'
import Col from 'react-bootstrap/Col'
import BusinessDataService from '../../api/todo/BusinessDataService.js';
import UserDataService from '../../api/todo/UserDataService.js';
import { Link } from 'react-router-dom'
import Button from 'react-bootstrap/Button'

// Page to update or add a specific todo
class SignupComponent extends Component {

    constructor(props) {
        super(props)

        // State of the page - contains id, desc, and date for a specific todo.
        this.state = {
            name: '',
            email: '',
            password: '',
            businessUEN: '',
            business:{}
        }

        this.onSubmit = this.onSubmit.bind(this)
        this.validate = this.validate.bind(this);

    }


    // on Formik Submit, add user
    onSubmit(values) {
        // retrieve business object
        let uen = values.businessUEN
        BusinessDataService.retrieveBusiness(uen).then(
            response => {
                // this.setState({business: response.data})
                // console.log(response)
                let user = {
                    email: values.email,
                    name: values.name,
                    password: values.password,
                    authority: "ROLE_EMPLOYEE",
                    business: response.data
                }
                UserDataService.createUser(user)
            }
        )
    }

    // on Formik Validate call
    // if errors populated, will not call onSubmit above
    validate(values) {
        let errors = {}

        if (!values.name) {
            console.log("flag");
            errors.name = "Enter your name"
        } 

        if (!values.email) {
            errors.email = "Enter your email"
        } 

        if (!values.password) {
            errors.password = "Enter a password"
        } else if (values.password.length < 5) {
            errors.password = "Enter at least 5 characters for password"
        }

        if (!values.businessUEN) {
            errors.businessUEN = "Enter your business UEN"
        } 

        return errors
    }

    render() {

        let { name, email, password, businessUEN } = this.state

        return (
            <div>
                <Container>
                    <Row>
                        <Col></Col>
                        <Col>
                <h1 className="text-white">Signup - Employee</h1>
                <div className="container text-white">
                    <Formik
                        initialValues={{ name, email, password, businessUEN }}
                        onSubmit={this.onSubmit}
                        validateOnChange={false}
                        validateOnBlur={false}
                        validate={this.validate}
                        enableReinitialize={true}
                    >
                        {
                            (props) => (
                                <Form>
                                    <ErrorMessage name="name" component="div" className="alert alert-warning"></ErrorMessage>
                                    <ErrorMessage name="email" component="div" className="alert alert-warning"></ErrorMessage>
                                    <ErrorMessage name="password" component="div" className="alert alert-warning"></ErrorMessage>
                                    <ErrorMessage name="businessUEN" component="div" className="alert alert-warning"></ErrorMessage>
                                    <fieldset className="form-group">
                                        <label>Name</label>
                                        <Field className="form-control" type="text" placeholder="Enter name" name="name"></Field>
                                    </fieldset>
                                    <fieldset className="form-group">
                                        <label>Email Address</label>
                                        <Field className="form-control" type="email" placeholder="Enter email" name="email"></Field>
                                    </fieldset>
                                    <fieldset className="form-group">
                                        <label>Password</label>
                                        <Field className="form-control" type="password" placeholder="Enter password" name="password"></Field>
                                    </fieldset>
                                    <fieldset className="form-group">
                                        <label>Your Business UEN</label>
                                        <Field className="form-control" type="text" placeholder="Enter Business UEN" name="businessUEN"></Field>
                                    </fieldset>
                                    <button className="btn btn-success" type="submit" >Sign Up</button>
                                    <p className="forgot-password text-center">Already registered? <a href="#placeholder">Sign In</a></p>
                                    <Link style={{ padding: '10px' }} className="new user text-left" to="/signupbusiness"><Button variant="dark">Register as a Business Owner</Button></Link>
                                </Form>
                            )
                        }
                    </Formik>
                </div>
                </Col>
                <Col></Col>
                </Row>
                </Container>
            </div>
        )
    }
}

export default SignupComponent;