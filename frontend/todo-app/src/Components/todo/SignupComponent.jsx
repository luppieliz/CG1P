import React, { Component } from 'react'
import { Form, Formik, Field, ErrorMessage } from 'formik'
import Container from 'react-bootstrap/Container'
import Row from 'react-bootstrap/Row'
import Col from 'react-bootstrap/Col'
import BusinessDataService from '../../api/todo/BusinessDataService.js';
import UserDataService from '../../api/todo/UserDataService.js';
import { Link } from 'react-router-dom'
import Button from 'react-bootstrap/Button'
import ReactNotification, { store } from 'react-notifications-component'
import 'react-notifications-component/dist/theme.css'


class SignupComponent extends Component {

    constructor(props) {
        super(props)
        this.state = {
            name: 'anrev',
            email: 'A@A',
            password: 'aaaaaaaaa',
            businessUEN: 'aaaa',
            business: {},
        }

        this.onSubmit = this.onSubmit.bind(this)
        this.validate = this.validate.bind(this);

    }

    onSubmit(values) {

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
                UserDataService.createUser(user).then(() => {showSuccess()}).catch((error) => {
                    throwError(error.response.data.message)
                })
            }
        ).catch((error) => {
            throwError(error.response.data.message)
        })
    }

    validate(values) {
        let errors = {}
        if (!values.name) {
            throwWarning("Please Enter Your Name!")
            errors.name = "Please Enter Your Name!"
        }
        if (!values.email) {
            throwWarning("Please Enter Your Email!")
            errors.email = "Please Enter Your Email!"
        }
        if (!values.password) {
            throwWarning("Please Enter Your Password!")
            errors.password = "Please Enter Your Password!"
        } else if (values.password.length < 8) {
            throwWarning("Password has to be at least 8 characters long!")
            errors.password = "Password has to be at least 8 characters long!"
        }
        if (!values.businessUEN) {
            throwWarning("Please Enter Your UEN!")
            errors.businessUEN = "Please Enter Your UEN!"
        }
        return errors
    }

    render() {
        let { name, email, password, businessUEN } = this.state
        return (
            <div>
                <ReactNotification />
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
                                                {/* <ErrorMessage name="name" component="div" className="alert alert-warning"></ErrorMessage>
                                                <ErrorMessage name="email" component="div" className="alert alert-warning"></ErrorMessage>
                                                <ErrorMessage name="password" component="div" className="alert alert-warning"></ErrorMessage>
                                                <ErrorMessage name="businessUEN" component="div" className="alert alert-warning"></ErrorMessage> */}
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

// ==================== UTILITY FUNCTIONS ====================

function throwWarning(warningMessage) {
    store.addNotification({
        title: "Warning!",
        message: warningMessage,
        type: "warning",
        insert: "top",
        container: "top-right",
        animationIn: ["animate__animated", "animate__fadeIn"],
        animationOut: ["animate__animated", "animate__fadeOut"],
        dismiss: {
            duration: 50000000,
        }
    });
}

function throwError(errorMessage) {
    store.addNotification({
        title: "Error!",
        message: errorMessage,
        type: "danger",
        insert: "top",
        container: "top-right",
        animationIn: ["animate__animated", "animate__fadeIn"],
        animationOut: ["animate__animated", "animate__fadeOut"],
        dismiss: {
            duration: 5000000,
        }
    });
}

function showSuccess () {
    store.addNotification({
        title: "Success!",
        message: "You have signed up successfully! Please login.",
        type: "success",
        insert: "top",
        container: "top-right",
        animationIn: ["animate__animated", "animate__fadeIn"],
        animationOut: ["animate__animated", "animate__fadeOut"],
        dismiss: {
            duration: 10000000,
        }
    });
}