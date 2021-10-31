import React, { Component } from 'react'
// import routing features
import { Link } from 'react-router-dom'
import HelloWorldService from '../api/HelloWorldService'
import Card from 'react-bootstrap/Card';
import Carousel from 'react-bootstrap/Carousel';
import Placeholder from 'react-bootstrap/Placeholder';
import Container from 'react-bootstrap/Container';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';


// Welcome landing page, that links to the Todos page.
class WelcomeComponent extends Component {
    constructor(props) {
        super(props);
        this.retrieveWelcomeMessage = this.retrieveWelcomeMessage.bind(this);
        this.handleSuccessfulResponse = this.handleSuccessfulResponse.bind(this);
        this.handleError = this.handleError.bind(this);

        this.state = {
            welcomeMessage: '',
            errorMessage: ''
        }
    }

    render() {
        return [
            'Dark',
        ].map((variant, idx) => (
            <div>

                <Card className="text-dark">
                    {/* <Card.Img variant="top" src="holder.js/100px180" /> */}
                    <Card.Body>
                        <Card.Text>
                            <h1>Welcome Home {this.props.match.params.name}</h1>
                        </Card.Text>
                    </Card.Body>
                </Card>

                <Carousel fade>
                    <Carousel.Item>
                        <img height={500}
                             className="d-block w-100"
                             src="https://i.pinimg.com/originals/d0/56/fc/d056fcad2c63462009271802ee5c5371.jpg"
                             alt="First slide"
                        />
                        <Carousel.Caption>
                            <Link to="/todos"></Link>
                            <p>Nulla vitae elit libero, a pharetra augue mollis interdum.</p>
                        </Carousel.Caption>
                    </Carousel.Item>
                    <Carousel.Item>
                        <img height={500}
                             className="d-block w-100"
                             src="https://images.unsplash.com/photo-1607418554432-e4331e6437f7?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1331&q=80"
                             alt="Second slide"
                        />

                        <Carousel.Caption>
                            <h3>Second slide label</h3>
                            <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit.</p>
                        </Carousel.Caption>
                    </Carousel.Item>
                    <Carousel.Item>
                        <img height={500}
                             className="d-block w-100"
                             src="https://images.unsplash.com/photo-1607418557343-cd4c9e4d285b?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1331&q=80"
                             alt="Third slide"
                        />

                        <Carousel.Caption>
                            <h3>Third slide label</h3>
                            <p>Praesent commodo cursus magna, vel scelerisque nisl consectetur.</p>
                        </Carousel.Caption>
                    </Carousel.Item>
                </Carousel>
                <Placeholder xs={12} bg="black" />
                <Placeholder xs={12} bg="black" />


                <Container >
                    <Row>
                        <Placeholder xs={12} bg="black" />

                        <Card bg={variant.toLowerCase()} className="text-center">
                            <Card.Body>
                                <Card.Title>
                                    <h2 className="container text-white">
                                        Click<button onClick={this.retrieveWelcomeMessage} className="btn btn-success">Here</button>
                                        here to get your inspirational quote of the day     </h2>
                                </Card.Title>
                                <Card.Text>
                                    <h3 className="container text-white">
                                        {this.state.welcomeMessage}
                                    </h3>
                                    <div className="container text-white">
                                        {this.state.errorMessage}
                                    </div>
                                </Card.Text>
                            </Card.Body>
                        </Card>
                        <Placeholder xs={12} bg="black" />
                    </Row>
                    <Row>
                        <Col>
                            <Card bg={variant.toLowerCase()} >
                                <Card.Body>
                                    <Card.Title>Employee Management</Card.Title>
                                    <Card.Text>
                                        employee 123 has updated her status
                                    </Card.Text>
                                </Card.Body>
                            </Card>
                        </Col>
                        <Col>
                            <Card bg={variant.toLowerCase()} >
                                <Card.Body>
                                    <Card.Title>Todos</Card.Title>
                                    <Card.Text>
                                        you have 5 undone task!
                                    </Card.Text>
                                </Card.Body>


                            </Card>
                        </Col>
                        <Col>
                            <Card bg={variant.toLowerCase()} className="mb-2">
                                <Card.Body>
                                    <Card.Title>Today's Top News!</Card.Title>
                                    <Card.Text>
                                        BERRAKING NEWS COVID CASES HIGH AF!!
                                    </Card.Text>
                                </Card.Body>


                            </Card>
                        </Col>
                        <Col>
                            <Card bg={variant.toLowerCase()}>
                                <Card.Body>
                                    <Card.Title>Need Help?</Card.Title>
                                    <Card.Text>
                                        Top Faqs today
                                    </Card.Text>
                                </Card.Body>


                            </Card>
                        </Col>
                    </Row>
                    <Row>
                        <Card>
                            <iframe className = "text-light" height="600px"  width= "100%;" src="https://infographics.channelnewsasia.com/covid-19/sgcovid19chart.html?type=embed&amp;channel=cna"></iframe>
                            <iframe  width="100%" height="600" scrolling="no" src="https://infographics.channelnewsasia.com/covid-19/asia-covid-19-daily-cases.html?type=embed&amp;channel=cna" ></iframe>
                        </Card>
                    </Row>
                </Container>


            </div>


        ))
    }

    retrieveWelcomeMessage() {

        // HelloWorldService.executeHelloWorldService()
        //     .then(response => this.handleSuccessfulResponse(response));

        // HelloWorldService.executeHelloWorldBeanService()
        //     .then(response => this.handleSuccessfulResponse(response));

        HelloWorldService.executeHelloWorldPathVariableService(this.props.match.params.name)
            .then(response => this.handleSuccessfulResponse(response))
            .catch(error => this.handleError(error));

    }

    handleSuccessfulResponse(response) {
        this.setState({ welcomeMessage: response.data.message })
    }

    handleError(error) {
        console.log(error.response)
        let errorMessage = '';
        if (error.message) {
            errorMessage += error.message
        }
        if (error.response && error.response.data) {
            errorMessage += error.response.data.message
        }

        this.setState({ errorMessage: errorMessage })
    }

}

export default WelcomeComponent;