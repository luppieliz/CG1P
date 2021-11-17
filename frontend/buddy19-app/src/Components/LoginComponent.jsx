import React, {Component} from 'react'
import AuthenticationService from '../api/AuthenticationService.js'
import {Link} from 'react-router-dom'
import Container from 'react-bootstrap/Container'
import Row from 'react-bootstrap/Row'
import Button from 'react-bootstrap/Button'

import Col from 'react-bootstrap/Col'
import Card from 'react-bootstrap/Card'
import Placeholder from 'react-bootstrap/Placeholder'
import {
    SESSION_TOKEN,
    SESSION_USER_BUSINESS,
    SESSION_USER_BUSINESS_INDUSTRY,
    SESSION_USER_ID,
    SESSION_USER_NAME,
    SESSION_USER_ROLE
} from '../Constants.js'

import GLOBE from 'vanta/dist/vanta.net.min'

// Log In
class LoginComponent extends Component {

    constructor(props) {
        super(props)
        this.vantaRef = React.createRef()


        // State - contains the email, password, and hasLoginFailed boolean fields.
        this.state = {
            email: 'admin@gmail.com',
            password: 'goodpassword',
            hasLoginFailed: false, // boolean 1
        }

        this.handleChange = this.handleChange.bind(this)
        this.loginClicked = this.loginClicked.bind(this)
    }

    componentDidMount() {
        this.vantaEffect = GLOBE({
            el: this.vantaRef.current,
            mouseControls: true,
            touchControls: true,
            gyroControls: false,
            minHeight: 200.0,
            minWidth: 200.0,
            scale: 1.0,
            scaleMobile: 1.0,
            color: 0x777777,
            color2: 0xFFFFFF,
            backgroundColor: 0x000000
        })
    }

    componentWillUnmount() {
        if (this.vantaEffect) this.vantaEffect.destroy()
    }

    // handle change in a field (manual way)
    handleChange(event) {
        this.setState(
            {
                // pull name from event and change that in the state to the new value from the event
                // square bracket to tell JS you are handling a variable instead of constant
                [event.target.name]: event.target.value
            }
        )
    }

    // On login click, execute Basic Auth Service to ensure user and pass match with the backend.
    // If successful, use authenticationService to register the log in ad redirect to the welcone page for that user
    // Else, set login failed to true to display alert
    loginClicked() {
        AuthenticationService.executeJwtAuthenticationService(this.state.email, this.state.password)
            .then((response) => {
                let JWTtoken = 'Bearer ' + response.data.token
                sessionStorage.setItem(SESSION_TOKEN, JWTtoken)
                return AuthenticationService.registerSuccessfulLoginForJwt(this.state.email)
            })
            .then(response => {
                sessionStorage.setItem(SESSION_USER_ID, response.data.id)
                sessionStorage.setItem(SESSION_USER_NAME, response.data.name)
                sessionStorage.setItem(SESSION_USER_BUSINESS, response.data.business.id)
                sessionStorage.setItem(SESSION_USER_BUSINESS_INDUSTRY, response.data.business.industry.name)
                sessionStorage.setItem(SESSION_USER_ROLE, response.data.authority)

                this.props.history.push(`/welcome`)
            })
            .catch(() => {
                this.setState({hasLoginFailed: true})
            })
    }

    render() {
        return (


            // In JavaScript, BOOLEAN && SHOW X means IF BOOLEAN, THEN SHOW X
            // If login is failed (reflected in state), display alert.
            <div style={{height: "92vh", width: "100%"}} ref={this.vantaRef}>
                <Container>
                    <Row>
                        <Col>
                            <Placeholder xs={12} bg="transparent"/>
                            <Placeholder xs={12} bg="transparent"/>
                            <Placeholder xs={12} bg="transparent"/>
                            <Placeholder xs={12} bg="transparent"/>
                            <Placeholder xs={12} bg="transparent"/>
                            <h0 className="cg1p-header">Buddy-19</h0>
                            <Placeholder xs={12} bg="transparent"/>
                            <h1 className="cg1p-header" style={{color: "#ffffff"}}>Login</h1>
                            <Placeholder xs={12} bg="transparent"/>
                            <Placeholder xs={12} bg="transparent"/>
                        </Col>
                    </Row>
                    <Row>
                        <Col></Col>
                        <Col className="text-black text-left">
                            <Card>
                                <Card.Body>
                                    <div className="form-group ">
                                        <label>Email</label>
                                        <input type="text" className="form-control" name="email"
                                               value={this.state.email} onChange={this.handleChange}/>
                                    </div>
                                    <div className="form-group">
                                        <label>Password</label>
                                        <input type="password" className="form-control" name="password"
                                               value={this.state.password} onChange={this.handleChange}/>
                                    </div>
                                    <Placeholder xs={12} bg="transparent"/>
                                    <Button variant="outline-dark" onClick={this.loginClicked}>Login</Button>
                                    <Placeholder xs={12} bg="transparent"/>
                                    <p className="forgot-password text-center"><a href="#placeholder">Forgot
                                        password?</a> or new to the app? <Link className="new user text-right"
                                                                               to="/signup">Sign up!</Link></p>
                                    {this.state.hasLoginFailed &&
                                    <div className="alert alert-warning">Invalid Credentials</div>}
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

export default LoginComponent