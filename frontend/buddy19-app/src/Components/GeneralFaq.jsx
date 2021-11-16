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
import FormControlLabel from '@mui/material/FormControlLabel';
import Switch from '@mui/material/Switch';




// Footer

class GeneralFaq extends Component {
    constructor(props) {
        super(props)

        this.toggleLanguage = this.toggleLanguage.bind(this);

        this.state = {
            faq: [],
            faqDisplay: [],
            isEmpty: false,
            tagsSelected: {},
            options: [],
            userLanguage: true,
        }

    }

    getFAQ() {
        FaqDataService.retrieveAllFAQ()
            .then(
                response => {
                    this.state.isEmpty = response.data.length === 0;
                    this.setState({ faq: response.data })
                    // this.setState({ faqDisplay: response.data })
                    //             console.log("retrieval made");
                    // console.log(response);
                    //             this.generateTaglist();
                    // console.log(this.state.faq[0]);

                    this.createUserDisplay();
                }
            )
    }

    createUserDisplay() {
        //get the logged in user's lamnguage and industry
        let userIndustry = "F&B"; //todo: set based on the logged in user
        let userLanguage = this.state.userLanguage ? "English" : "Chinese"
        let output = [];
        let idx = 0;
        console.log(this.state.faq);
        //traverse through the faq list to find matches
        for (var faqCount in this.state.faq) {
            let faqItem = this.state.faq[faqCount];
            //if there is a match, add it to faqDisplay
            // console.log(faqItem.language);
            // console.log(faqItem.industry)
            if (faqItem.language === userLanguage && faqItem.industry === userIndustry) {
                output[idx] = faqItem;
                idx++;
            }
        }
        //set the final list
        console.log(output);
        this.setState({ faqDisplay: output })

    }

    toggleLanguage() {
        this.setState({
            userLanguage: !this.state.userLanguage
        })
        this.getFAQ()
    }

    componentDidMount() {
        this.getFAQ()
    }

    render() {
        
        return (
            <div >
                <Placeholder xs={12} bg="transparent" />
                <Placeholder xs={12} bg="transparent" />
                <Placeholder xs={12} bg="transparent" />
                <h1 className="cg1p-header">FAQ {this.state.userName}</h1>
                <Placeholder xs={12} bg="transparent" />
                <Placeholder xs={12} bg="transparent" />

                <Row style={{ paddingLeft: "5vw", paddingRight: "5vw" }}>
                    <Col>
                        <Card className="text-center">
                            <Card.Body>
                                <Card.Title>
                                    {/* <h2 className="cg1p-header">
                                        My Language</h2> */}
                                </Card.Title>
                                <Card.Text>
                                    <h5 className="text-dark">
                                        <Card.Header>Language : {this.state.userLanguage ? 'English' : 'Chinese'}
                                        </Card.Header>
                                        <FormControlLabel onClick={this.toggleLanguage} control={<Switch color="warning" />} label=""
                                        />
                                    </h5>
                                </Card.Text>
                            </Card.Body>
                        </Card>
                    </Col>
                </Row>

                <Placeholder xs={12} bg="transparent" />
                <Placeholder xs={12} bg="transparent" />

                <Row xl={3} style={{ paddingLeft: "5vw", paddingRight: "5vw" }}>
                    {Array.from(this.state.faqDisplay, (_, idx) => (
                        <Col>
                            <Card>
                                <Card.Img variant="top" src={this.state.faqDisplay[idx].url} />
                                <Card.Body>
                                    <Card.Title>
                                        <Card.Link href={this.state.faqDisplay[idx].url}>
                                            {this.state.faqDisplay[idx].industry}
                                        </Card.Link>
                                    </Card.Title>
                                </Card.Body>
                            </Card>
                        </Col>
                    ))}
                </Row>
                <Placeholder xs={12} bg="transparent" style={{ paddingBottom: "5vh" }} />

            </div>
        )
    }
}

export default GeneralFaq