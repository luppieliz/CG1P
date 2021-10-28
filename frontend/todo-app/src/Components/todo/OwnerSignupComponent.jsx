import React, { Component } from 'react'
import { Form, Formik, Field, ErrorMessage } from 'formik'
import Container from 'react-bootstrap/Container'
import Row from 'react-bootstrap/Row'
import Col from 'react-bootstrap/Col'
import BusinessDataService from '../../api/todo/BusinessDataService.js';
import UserDataService from '../../api/todo/UserDataService.js';
import IndustryDataService from '../../api/todo/IndustryDataService.js'

// Page to update or add a specific todo
class OwnerSignupComponent extends Component {

    constructor(props) {
        super(props)

        // State of the page - contains id, desc, and date for a specific todo.
        this.state = {
            name: '',
            email: '',
            password: '',
            businessUEN: '',
            businessName: '',
            industry: '',
            industryList: []
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

    // on Formik Submit, add user, and business
    onSubmit(values) {

        // retrieve industry object, get response containing industry, 
        // create new business witb industry, get response containing business, 
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
                        UserDataService.createUser(user)
                    }
                )
            }
        )
    }

    // on Formik Validate call
    // if errors populated, will not call onSubmit above
    validate(values) {
        let errors = {}

        if (!values.name) {
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

        if (!values.businessName) {
            errors.businessName = "Enter your business name"
        }

        if (!values.industry) {
            errors.industry = "Enter your industry"
        }

        return errors
    }

    render() {

        let { name, email, password, businessUEN, businessName, industry } = this.state
        const options = [];

        return (
            <div>
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
                                                    <Field className="form-control" as="select" onChange={this.onIndustryListDropdownSelected} name="industry">
                                                        {this.state.industryList.map(
                                                            oneIndustry =>
                                                                <option value={oneIndustry.name}>{oneIndustry.name}</option>
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