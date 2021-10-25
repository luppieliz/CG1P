import React, { Component } from 'react';
import AuthenticationService from './AuthenticationService.js';
import { Image } from 'react-bootstrap';
import { Link } from 'react-router-dom';
import Container from 'react-bootstrap/Container'
import Row from 'react-bootstrap/Row'
import Col from 'react-bootstrap/Col'
import Card from 'react-bootstrap/Card'
import Button from 'react-bootstrap/Button'
import Placeholder from 'react-bootstrap/Placeholder'
import Form from 'react-bootstrap/Form'


class ProfileComponent extends Component {

    constructor(props) {
        super(props)


        this.state = {
            username: 'admin',
            password: 'goodpassword',
            hasLoginFailed: false, // boolean 1
        }
        this.handleChange = this.handleChange.bind(this)
        this.loginClicked = this.EditClicked.bind(this)


    }

    handleChange(event) {
        this.setState(
            {
                [event.target.name]: event.target.value
            }
        );
        console.log(this.state)
    }

    EditClicked() {

    }

    render() {
        return (
            <>
                <Card className="text-dark">
                    {/* <Card.Img variant="top" src="holder.js/100px180" /> */}
                    <Card.Body>
                        <Card.Text>
                            <h1>Profile Page</h1>
                        </Card.Text>
                    </Card.Body>
                </Card>
                <Container>
                    <Row>
                        <Col>
                        <Card style={{ width: '18rem' }}>
                            <Card.Img variant="top" src="https://pbs.twimg.com/profile_images/1176237957851881472/CHOXLj9b_400x400.jpg" />
                            <Card.Body className="text-dark">
                                <Card.Title>Name</Card.Title>
                                <Card.Text>
                                    Manager of 123 astronaut
                                </Card.Text>
                                <Button variant="primary" onClick={this.EditClicked}>Edit</Button>
                            </Card.Body>
                        </Card>
                        </Col>

                        <Col>
                        <Form>
                            <Row className="mb-3">
                                <Form.Group as={Col} controlId="formGridEmail">
                                <Form.Label>Email</Form.Label>
                                <Form.Control type="email" placeholder="Enter email" />
                                </Form.Group>

                                <Form.Group as={Col} controlId="formGridPassword">
                                <Form.Label>Password</Form.Label>
                                <Form.Control type="password" placeholder="Password" />
                                </Form.Group>
                            </Row>

                            <Form.Group className="mb-3" controlId="formGridAddress1">
                                <Form.Label>Address</Form.Label>
                                <Form.Control placeholder="1234 Main St" />
                            </Form.Group>

                            <Form.Group className="mb-3" controlId="formGridAddress2">
                                <Form.Label>Address 2</Form.Label>
                                <Form.Control placeholder="Apartment, studio, or floor" />
                            </Form.Group>

                            <Row className="mb-3">
                                <Form.Group as={Col} controlId="formGridCity">
                                <Form.Label>City</Form.Label>
                                <Form.Control />
                                </Form.Group>

                                <Form.Group as={Col} controlId="formGridState">
                                <Form.Label>State</Form.Label>
                                <Form.Select defaultValue="Choose...">
                                    <option>Choose...</option>
                                    <option>...</option>
                                </Form.Select>
                                </Form.Group>

                                <Form.Group as={Col} controlId="formGridZip">
                                <Form.Label>Zip</Form.Label>
                                <Form.Control />
                                </Form.Group>
                            </Row>

                            <Form.Group className="mb-3" id="formGridCheckbox">
                                <Form.Check type="checkbox" label="Check me out" />
                            </Form.Group>

                            <Button variant="primary" type="submit">
                                Submit
                            </Button>
                            </Form>
                        </Col>
                        <Col></Col>

                    </Row>
                </Container>
            </>
        )
    }
}

export default ProfileComponent;


