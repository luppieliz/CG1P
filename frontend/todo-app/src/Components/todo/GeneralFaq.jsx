import React, { Component } from 'react';
import Navbar from 'react-bootstrap/Navbar'
import Container from 'react-bootstrap/Container'
import Form from 'react-bootstrap/Form'
import Button from 'react-bootstrap/Button'
import FormControl from 'react-bootstrap/FormControl'
import Row from 'react-bootstrap/Row'
import Placeholder from 'react-bootstrap/Placeholder'
import Col from 'react-bootstrap/Col'
import Image from 'react-bootstrap/Image'
import Card from 'react-bootstrap/Card'
import Accordion from 'react-bootstrap/Accordion'

// Footer

class GeneralFaq extends Component {

    render() {
        return (
            <div>
                {/* header */}
                <Card className="text-dark">
                    {/* <Card.Img variant="top" src="holder.js/100px180" /> */}
                    <Card.Body>
                        <Card.Text>
                            <h1>FAQ</h1>
                        </Card.Text>
                    </Card.Body>
                </Card>

                <Placeholder xs={12} bg="dark" className="bg-black" />
                <Placeholder xs={12} bg="dark" className="bg-black" />


                <Row>
                    <Col></Col>
                    <Col xs={5}>
                        <Form className="d-flex">
                            <FormControl
                                type="search"
                                placeholder="Search"
                                className="mr-2"
                                aria-label="Search"
                            />
                            <Button src="" variant="outline-success">Search</Button>
                        </Form>
                    </Col>
                    <Col></Col>
                </Row>

                <Placeholder xs={12} bg="dark" className="bg-black" />
                <Placeholder xs={12} bg="dark" className="bg-black" />
                <Placeholder xs={12} bg="dark" className="bg-black" />
                <Placeholder xs={12} bg="dark" className="bg-black" />

                {/* the questions */}
                <Container>
                    <Row>

                        <Col>
                            <Accordion>
                                <Accordion.Item eventKey="0">
                                    <Accordion.Header>Do i need to declare my onsite manpower details?</Accordion.Header>
                                    <Accordion.Body className="text-white bg-black">
                                        You are required to declare your onsite manpower if you received an email
                                        notifying you to submit your onsite manpower details, or if there is a
                                        “MPSubmit” reference number in your company dashboard after you have logged into GoBusiness.
                                        For companies in the Construction, Process and, Marine and Offshore Sectors
                                        Companies in the above sectors will not receive an email notification to submit manpower details but should still continue
                                        to submit their manpower details for the reference numbers they see in the system that are available to them.
                                        These companies are required to submit more information including NRIC/Fin numbers and premises/location
                                        details in their manpower declaration. These companies will not have an “MPSubmit” reference number.
                                    </Accordion.Body>
                                </Accordion.Item>
                                <Accordion.Item eventKey="1">
                                    <Accordion.Header>Why is it necessary for me to declare my onsite manpower details?</Accordion.Header>
                                    <Accordion.Body className="text-white bg-black " >
                                        As announced under the Stablisation Period on 24 Sep 2021, work-from-home will be the
                                        default at workplaces and employers must ensure that employees who are able to work from
                                        home do so. The onsite manpower declaration will help us in tracking and guide our decisions moving forward.
                                        We seek your assistance in adhering to this and appreciate your contributions in the national fight against COVID-19.
                                    </Accordion.Body>
                                </Accordion.Item>
                            </Accordion>
                        </Col>
                        <Col></Col>
                    </Row>
                </Container>


            </div>
        )
    }
}

export default GeneralFaq