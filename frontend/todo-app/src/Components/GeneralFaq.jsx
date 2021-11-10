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
import FaqDataService from '../api/FaqDataService';




// Footer

class GeneralFaq extends Component {
    constructor(props) {
        super(props)

        this.state = {
            faq: [],
            faqDisplay: [],
            isEmpty: false,
            tagsSelected: {},
            options: []
        }
    }

    getFAQ() {
        FaqDataService.retrieveAllFAQ()
            .then(
                response => {
                    this.state.isEmpty = response.data.length === 0;
                    this.setState({ faq: response.data })
                    this.setState({ faqDisplay: response.data })
                    //             console.log("retrieval made");
                    console.log(response);
                    //             this.generateTaglist();
                }
            )
    }

    componentDidMount() {
        this.getFAQ()
    }

    render() {
        return (
            <div >
                <Placeholder xs={12} bg="transparent" />
                <Placeholder xs={12} bg="transparent" />
                <h1 className="cg1p-header">FAQ {this.state.userName}</h1>
                <Placeholder xs={12} bg="transparent" />
                <Placeholder xs={12} bg="transparent" />
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
                <Placeholder xs={12} bg="transparent" />
                <Placeholder xs={12} bg="transparent" />
                <Placeholder xs={12} bg="transparent" />
                <Placeholder xs={12} bg="transparent" />

                {/* the questions */}
                <Container>
                    <Row>

                        <Accordion>
                            <Accordion.Item eventKey="0">
                                <Accordion.Header>Do i need to declare my onsite manpower details?</Accordion.Header>
                                <Accordion.Body className="text-black bg-white">
                                    <Row>
                                        <Col>You are required to declare your onsite manpower if you received an email
                                            notifying you to submit your onsite manpower details, or if there is a
                                            “MPSubmit” reference number in your company dashboard after you have logged into GoBusiness.
                                            For companies in the Construction, Process and, Marine and Offshore Sectors
                                            Companies in the above sectors will not receive an email notification to submit manpower details but should still continue
                                            to submit their manpower details for the reference numbers they see in the system that are available to them.
                                            These companies are required to submit more information including NRIC/Fin numbers and premises/location
                                            details in their manpower declaration. These companies will not have an “MPSubmit” reference number.</Col>
                                        <Col> <Image src="https://static6.depositphotos.com/1131998/649/v/600/depositphotos_6490192-stock-illustration-smile-face.jpg" fluid /></Col>
                                    </Row>
                                </Accordion.Body>
                            </Accordion.Item>
                            <Accordion.Item eventKey="1">
                                <Accordion.Header>Why is it necessary for me to declare my onsite manpower details?</Accordion.Header>
                                <Accordion.Body className="text-black bg-white " >
                                    <Row>
                                        <Col>You are required to declare your onsite manpower if you received an email
                                            notifying you to submit your onsite manpower details, or if there is a
                                            “MPSubmit” reference number in your company dashboard after you have logged into GoBusiness.
                                            For companies in the Construction, Process and, Marine and Offshore Sectors
                                            Companies in the above sectors will not receive an email notification to submit manpower details but should still continue
                                            to submit their manpower details for the reference numbers they see in the system that are available to them.
                                            These companies are required to submit more information including NRIC/Fin numbers and premises/location
                                            details in their manpower declaration. These companies will not have an “MPSubmit” reference number.</Col>
                                        <Col> <Image src="https://static6.depositphotos.com/1131998/649/v/600/depositphotos_6490192-stock-illustration-smile-face.jpg" fluid /></Col>
                                    </Row>
                                </Accordion.Body>
                            </Accordion.Item>
                        </Accordion>


                    </Row>
                    <Placeholder xs={12} bg="transparent" style={{ paddingBottom: "50vh" }} />
                </Container>
            </div>
        )
    }
}

export default GeneralFaq