import React, { Component } from 'react';
import AuthenticationService from './AuthenticationService.js';
import { Image } from 'react-bootstrap';
import { Link } from 'react-router-dom';

// Log In
class LoginComponent extends Component {

    constructor(props) {
        super(props)

        // State - contains the username, password, and hasLoginFailed boolean fields.
        this.state = {
            username: 'username',
            password: 'password',
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
//             .executeJwtAuthenticationService(this.state.username, this.state.password)
            .executeBasicAuthenticationService(this.state.username, this.state.password)
            .then((response) => {
//                 AuthenticationService.registerSuccessfulLoginForJwt(this.state.username, response.data.token);
                   AuthenticationService.registerSuccessfulLoginForBasicAuth(this.state.username, this.state.username);

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
            <div>
                <h1>Login</h1>
                <div className="container">
                    <div className="row row align-items-center">
                        <div className="col-sm">
                            <Image src="https://getthematic.com/insights/content/images/wordpress/2019/01/shutterstock_1112175710-1.jpg" fluid />
                        </div>
                        <div className="col-md">
                            <div className="form-group">
                                <label>User Name</label>
                                <input type="text" className="form-control" name="username" value={this.state.username} onChange={this.handleChange} />
                            </div>
                            <div className="form-group">
                                <label>Password</label>
                                <input type="password" className="form-control" name="password" value={this.state.password} onChange={this.handleChange} />
                            </div>
                            <button className="btn btn-success" onClick={this.loginClicked}>Login</button>
                            <p className="forgot-password text-center"><a href="#placeholder">Forgot password?</a> or new to the app? <Link className="new user text-right" to="/signup">Sign up!</Link></p>
                            {this.state.hasLoginFailed && <div className="alert alert-warning">Invalid Credentials</div>}
                        </div>
                    </div>
                </div>
            </div>

        )
    }
}

export default LoginComponent;


