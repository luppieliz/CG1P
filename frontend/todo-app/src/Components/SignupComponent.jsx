import React, { Component } from 'react'
import { Form, Formik, Field, ErrorMessage } from 'formik'
import Container from 'react-bootstrap/Container'
import Row from 'react-bootstrap/Row'
import Col from 'react-bootstrap/Col'
import BusinessDataService from '../api/BusinessDataService.js';
import UserDataService from '../api/UserDataService.js';
import { Link } from 'react-router-dom'
import Button from 'react-bootstrap/Button'
import Card from 'react-bootstrap/Card'
import Placeholder from 'react-bootstrap/Placeholder'
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
                UserDataService.createUser(user).then(() => { showSuccess() }).catch((error) => {
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
            <div style={{ backgroundImage: "url(data:image/jpeg;base64,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"
            ,backgroundPosition: 'center'
            ,backgroundSize: 'cover'
            ,backgroundRepeat: 'no-repeat'
            ,width: '100vw'
            ,height: '100vh'
            }}>
                <ReactNotification />
                <Container>
                    <Row>
                        <Col></Col>
                        <Col>
                            <Placeholder xs={12} bg="transparent" />
                            <h1 className="text-dark">Signup - Employee</h1>
                            <Placeholder xs={12} bg="transparent" />
                            <Card border="primary" style={{ width: '35rem', height: '27rem', borderWidth: '5px' }}>
                                <Card.Body>
                                    <div className="container text-dark">

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
                                                        <Placeholder xs={12} bg="white" />
                                                        <button className="btn btn-success" type="submit" >Sign Up</button>
                                                        <p className="forgot-password text-center">Already registered? <a href="/login">Sign In</a></p>
                                                        <Link style={{ padding: '10px' }} className="new user text-left" to="/signupbusiness"><Button variant="dark">Register as a Business Owner</Button></Link>
                                                    </Form>
                                                )
                                            }
                                        </Formik>

                                    </div>
                                </Card.Body>
                            </Card>
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

function showSuccess() {
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