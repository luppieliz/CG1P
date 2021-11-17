import React, {Component} from 'react';

import Container from 'react-bootstrap/Container'
import Row from 'react-bootstrap/Row'
import Col from 'react-bootstrap/Col'
import Card from 'react-bootstrap/Card'
import Button from 'react-bootstrap/Button'
import Form from 'react-bootstrap/Form'
import ListGroup from 'react-bootstrap/ListGroup'
import Badge from 'react-bootstrap/Badge'
import Placeholder from 'react-bootstrap/Placeholder'


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
    }

    EditClicked() {

    }

    render() {
        return (
            <>
                <div>
                    <Container>
                        <Placeholder xs={12} bg="transparent"/>
                        <h1 className="text-dark">Profile Page</h1>
                        <Placeholder xs={12} bg="transparent"/>
                        <Row>
                            <Col>
                                <Card style={{width: '18rem'}}>
                                    <Card.Img variant="top"
                                              src="https://pbs.twimg.com/profile_images/1176237957851881472/CHOXLj9b_400x400.jpg"/>
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
                                <Form className="text-dark">
                                    <Row className="mb-3">
                                        <Form.Group as={Col} controlId="formGridEmail">
                                            <Form.Label>Email</Form.Label>
                                            <Form.Control type="email" placeholder="Enter email"/>
                                        </Form.Group>

                                        <Form.Group as={Col} controlId="formGridPassword">
                                            <Form.Label>Password</Form.Label>
                                            <Form.Control type="password" placeholder="Password"/>
                                        </Form.Group>
                                    </Row>

                                    <Form.Group className="mb-3" controlId="formGridAddress1">
                                        <Form.Label>Address</Form.Label>
                                        <Form.Control placeholder="1234 Main St"/>
                                    </Form.Group>

                                    <Form.Group className="mb-3" controlId="formGridAddress2">
                                        <Form.Label>Address 2</Form.Label>
                                        <Form.Control placeholder="Apartment, studio, or floor"/>
                                    </Form.Group>

                                    <Row className="mb-3">
                                        <Form.Group as={Col} controlId="formGridCity">
                                            <Form.Label>City</Form.Label>
                                            <Form.Control/>
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
                                            <Form.Control/>
                                        </Form.Group>
                                    </Row>

                                    <Form.Group className="mb-3" id="formGridCheckbox">
                                        <Form.Check type="checkbox" label="Check me out"/>
                                    </Form.Group>

                                    <Button variant="primary" type="submit">
                                        Update Information
                                    </Button>
                                </Form>
                            </Col>

                            <Col>
                                <ListGroup as="ol" numbered>
                                    <ListGroup.Item
                                        as="li"
                                        className="d-flex justify-content-between align-items-start"
                                    >
                                        <div className="ms-2 me-auto">
                                            <div className="fw-bold">Subheading</div>
                                            Cras justo odio
                                        </div>
                                        <Badge variant="primary" pill>
                                            14
                                        </Badge>
                                    </ListGroup.Item>
                                    <ListGroup.Item
                                        as="li"
                                        className="d-flex justify-content-between align-items-start"
                                    >
                                        <div className="ms-2 me-auto">
                                            <div className="fw-bold">Subheading</div>
                                            Cras justo odio
                                        </div>
                                        <Badge variant="primary" pill>
                                            14
                                        </Badge>
                                    </ListGroup.Item>
                                    <ListGroup.Item
                                        as="li"
                                        className="d-flex justify-content-between align-items-start"
                                    >
                                        <div className="ms-2 me-auto">
                                            <div className="fw-bold">Subheading</div>
                                            Cras justo odio
                                        </div>
                                        <Badge variant="primary" pill>
                                            14
                                        </Badge>
                                    </ListGroup.Item>
                                    <ListGroup.Item
                                        as="li"
                                        className="d-flex justify-content-between align-items-start"
                                    >
                                        <div className="ms-2 me-auto">
                                            <div className="fw-bold">Subheading</div>
                                            Cras justo odio
                                        </div>
                                        <Badge variant="primary" pill>
                                            14
                                        </Badge>
                                    </ListGroup.Item>
                                    <ListGroup.Item
                                        as="li"
                                        className="d-flex justify-content-between align-items-start"
                                    >
                                        <div className="ms-2 me-auto">
                                            <div className="fw-bold">Subheading</div>
                                            Cras justo odio
                                        </div>
                                        <Badge variant="primary" pill>
                                            14
                                        </Badge>
                                    </ListGroup.Item>
                                </ListGroup>
                            </Col>

                        </Row>
                    </Container>
                    <Placeholder xs={12} bg="transparent"/>
                </div>
            </>
        )
    }
}

export default ProfileComponent;


