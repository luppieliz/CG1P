
import React, { Component } from 'react'
import Container from 'react-bootstrap/Container'
import Row from 'react-bootstrap/Row'
import Col from 'react-bootstrap/Col'
import Card from 'react-bootstrap/Card'
import Image from 'react-bootstrap/Image'


class OwnerSignupComponent extends Component {

    constructor(props) {
        super(props)

        this.state = {
            Username: '',
            email: '',
            password: '',
            hasSignupFailed: false,
            showSuccessMessage: false
        }

        this.handleChange = this.handleChange.bind(this)
        this.signupClicked = this.signupClicked.bind(this)
    }

    handleChange(event) {

        this.setState(
            {
                [event.target.name]
                    : event.target.value
            }
        )
    }


    signupClicked() {

        // ADD ADD-BUSINESS LOGIC HERE
        // SEND VERIFICATION EMAIL

    }

    render() {
        return (
            <Container>

                <Row>
                    <Col></Col>
                    <Col>
                        <h1 className="text-info" style={{ padding: '100px' }}>COVby</h1>
                    </Col>
                    <Col></Col>
                </Row>
                <Row style={{ padding: '50px' }}>
                    <Col><Image style={{ width: '35rem', height: '24rem' }} src="https://media.istockphoto.com/photos/business-people-standing-behind-social-distancing-signage-on-office-picture-id1262271993?b=1&k=20&m=1262271993&s=170667a&w=0&h=ssGXGBFECItq--aJ7gAGWgFWC_NXO_fN58oi5J4_bWs=" rounded fluid /></Col>
                    <Col className="text-black text-left">
                        <Card border="info" style={{ padding: '20px', width: '30rem', borderWidth: '4px' }}>
                            <form>
                                <div className="form-group">
                                    <label>User name</label>
                                    <input type="text" className="form-control" placeholder="First name" value={this.state.username} onChange={this.handleChange} />
                                </div>

                                <div className="form-group">
                                    <label>Email address</label>
                                    <input type="email" className="form-control" placeholder="Enter email" value={this.state.username} onChange={this.handleChange} />
                                </div>

                                <div className="form-group">
                                    <label>Password</label>
                                    <input type="password" className="form-control" placeholder="Enter password" value={this.state.username} onChange={this.handleChange} />
                                </div>

                                <div className="form-group">
                                    <label>Business UEN</label>
                                    <input type="text" className="form-control" placeholder="Enter UEN" value={this.state.username} onChange={this.handleChange} />
                                </div>

                                <div className="form-group">
                                    <label>Business Name (in accordance to UEN)</label>
                                    <input type="text" className="form-control" placeholder="Enter Registered Name" value={this.state.username} onChange={this.handleChange} />
                                </div>

                                <div>
                                    <label for="industryType">Please Select an Industry</label>
                                    <select name="industryType" id="industryType">
                                        <option value="Services">Services</option>
                                        <option value="F&B">F&B</option>
                                        <option value="Construction">Construction</option>
                                    </select>
                                </div>


                                <button type="submit" className="btn btn-primary btn-block" onClick={this.signupClicked}>Register Business</button>
                            </form>
                        </Card>
                    </Col>
                </Row>
            </Container>
        );

    }
}

export default OwnerSignupComponent
