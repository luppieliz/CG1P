import React, { Component } from 'react'
import { Form, Formik, Field, ErrorMessage } from 'formik'
import Container from 'react-bootstrap/Container'
import Row from 'react-bootstrap/Row'
import Col from 'react-bootstrap/Col'
import BusinessDataService from '../../api/todo/BusinessDataService.js';
import UserDataService from '../../api/todo/UserDataService.js';
import IndustryDataService from '../../api/todo/IndustryDataService.js'
import ReactNotification, { store } from 'react-notifications-component'
import 'react-notifications-component/dist/theme.css'

class OwnerSignupComponent extends Component {

    constructor(props) {
        super(props)

        this.state = {
            name: 'aaaa',
            email: 'a@a',
            password: 'aaaaaaaaa',
            businessUEN: 'abc',
            businessName: 'name',
            industry: 'Healthcare',
            industryList: [],
        }

        this.onSubmit = this.onSubmit.bind(this)
        this.validate = this.validate.bind(this);
    }

    componentDidMount() {
        IndustryDataService.retrieveAllIndustries().then(
            response => {
                this.setState({ industryList: response.data })
            }
        );
    }

    onSubmit(values) {

        // retrieve industry object, get response containing industry, 
        // create new business with industry, get response containing business, 
        // create new user with business
        let industry = values.industry
        IndustryDataService.retrieveIndustry(industry).then(
            response => {
                let business = {
                    uen: values.businessUEN,
                    name: values.businessName,
                    industry: response.data
                }
                BusinessDataService.createBusiness(business).then(
                    response2 => {
                        let user = {
                            email: values.email,
                            name: values.name,
                            password: values.password,
                            authority: "ROLE_BUSINESSOWNER",
                            business: response2.data
                        }
                        UserDataService.createUser(user).then(() => {showSuccess()}).catch((error) => {
                            throwError(error.response.data.message)
                        })
                    }
                ).catch((error) => {
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
        if (!values.businessName) {
            throwWarning("Please Enter Your Business Name!")
            errors.businessUEN = "Please Enter Your Business Name!"
        }
        if (!values.industry) {
            throwWarning("Please Enter Your Industry!")     
            errors.businessUEN = "Please Enter Your Industry!"   
        }
        return errors
    }

    render() {

        let { name, email, password, businessUEN, businessName, industry } = this.state

        return (
            <div>
                <ReactNotification />
                <Container>
                    <Row>
                        <Col></Col>
                        <Col>
                            <h1 className="text-white">Signup - Business Owner</h1>
                            <div className="container text-white">
                                <Formik
                                    initialValues={{ name, email, password, businessUEN, businessName, industry }}
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
                                                <ErrorMessage name="businessName" component="div" className="alert alert-warning"></ErrorMessage>
                                                <ErrorMessage name="industry" component="div" className="alert alert-warning"></ErrorMessage>

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
                                                <fieldset className="form-group">
                                                    <label>Your Business Name</label>
                                                    <Field className="form-control" type="text" placeholder="Enter Business Name" name="businessName"></Field>
                                                </fieldset>

                                                <fieldset className="form-group">
                                                    <label>Your Industry</label>
                                                    <Field className="form-control" as="select" name="industry">
                                                        {this.state.industryList.map(
                                                            oneIndustry =>
                                                                <option key={oneIndustry.name} value={oneIndustry.name}>{oneIndustry.name}</option>
                                                        )}
                                                    </Field>
                                                </fieldset>

                                                <button className="btn btn-success" type="submit" >Sign Up</button>
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

export default OwnerSignupComponent;

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
            duration: 5000,
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
            duration: 5000,
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
            duration: 10000,
        }
    });
}