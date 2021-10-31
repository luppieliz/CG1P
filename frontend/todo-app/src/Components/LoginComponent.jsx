import React, { Component } from 'react';
import AuthenticationService from '../api/AuthenticationService.js';
import { Image } from 'react-bootstrap';
import { Link } from 'react-router-dom';
import Container from 'react-bootstrap/Container'
import Row from 'react-bootstrap/Row'
import Col from 'react-bootstrap/Col'
import Card from 'react-bootstrap/Card'
import Placeholder from 'react-bootstrap/Placeholder'


// Log In
class LoginComponent extends Component {

    constructor(props) {
        super(props)

        // State - contains the username, password, and hasLoginFailed boolean fields.
        this.state = {
            username: 'admin',
            password: 'goodpassword',
            hasLoginFailed: false, // boolean 1
        }
        this.handleChange = this.handleChange.bind(this)
        this.loginClicked = this.loginClicked.bind(this)


    }


    // handle change in a field (manual way)
    handleChange(event) {
        this.setState(
            {
                // pull name from event and change that in the state to the new value from the event
                // square bracket to tell JS you are handling a variable instead of constant
                [event.target.name]: event.target.value
            }
        );
        console.log(this.state)
    }

    // On login click, execute Basic Auth Service to ensure user and pass match with the backend.
    // If successful, use authenticationService to register the log in ad redirect to the welcone page for that user
    // Else, set login failed to true to display alert
    loginClicked() {

        AuthenticationService
            .executeBasicAuthenticationService(this.state.username, this.state.password)
            .then((response) => {
                AuthenticationService.registerSuccessfulLoginForBasicAuth(this.state.username, this.state.password);
                this.props.history.push(`/welcome/${this.state.username}`);
            })
            .catch(() => {
                this.setState({ hasLoginFailed: true })
            })
    }

    render() {
        return (


            // In JavaScript, BOOLEAN && SHOW X means IF BOOLEAN, THEN SHOW X
            // If login is failed (reflected in state), display alert.
            <Container>
                <Row>
                    <Col></Col>
                    <Col>
                        <h1 className="text-warning" style={{ padding: '100px' }}>Buddy-19</h1>
                    </Col>
                    <Col></Col>
                </Row>

                <Row>
                    <Col >
                        <Image style={{ width: '35rem' }} src="https://images.unsplash.com/photo-1603357465999-241beecc2629?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1232&q=80" rounded fluid />
                    </Col>
                    <Col className="text-black text-left">
                        <Card border="warning" style={{ width: '35rem', height: '20rem', borderWidth: '5px' }}>
                            <Card.Body>
                                <Placeholder xs={12} bg="light" />
                                    <div className="form-group ">
                                        <label>User Name</label>
                                        <input type="text" className="form-control" name="username" value={this.state.username} onChange={this.handleChange} />
                                    </div>
                                    <div className="form-group">
                                        <Placeholder xs={12} bg="light" />
                                        <label>Password</label>
                                        <input type="password" className="form-control" name="password" value={this.state.password} onChange={this.handleChange} />
                                    </div>
                                    <Placeholder xs={12} bg="light" />
                                    <button className="btn btn-success" onClick={this.loginClicked}>Login</button>
                                    <Placeholder xs={12} bg="light" />
                                    <p className="forgot-password text-center"><a href="#placeholder">Forgot password?</a> or new to the app? <Link className="new user text-right" to="/signup">Sign up!</Link>
                                    </p>
                                    {this.state.hasLoginFailed && <div className="alert alert-warning">Invalid Credentials</div>}
                            </Card.Body>
                        </Card>
                    </Col>

                </Row>


            </Container>

        )
    }
}

export default LoginComponent;


