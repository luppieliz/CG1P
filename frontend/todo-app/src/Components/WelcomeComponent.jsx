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
import { SESSION_USER_ID, SESSION_USER_NAME } from '../Constants';


// Welcome landing page, that links to the Todos page.
class WelcomeComponent extends Component {
    constructor(props) {
        super(props);
        this.retrieveWelcomeMessage = this.retrieveWelcomeMessage.bind(this);
        this.handleSuccessfulResponse = this.handleSuccessfulResponse.bind(this);
        this.handleError = this.handleError.bind(this);

        this.state = {
            userId: sessionStorage.getItem(SESSION_USER_ID),
            userName: sessionStorage.getItem(SESSION_USER_NAME),
            welcomeMessage: '',
            errorMessage: ''
        }
    }

    render() {
        return [
            'Dark',
        ].map((variant, idx) => (
            <div style={{ backgroundImage: "url(https://cdn.shopify.com/s/files/1/2118/5289/products/EnvyLoops_Interconnected_V1_-_Glow-4K0_3840x.jpg?v=1527308804"
            ,backgroundPosition: 'center'
            ,backgroundSize: 'cover'
            ,backgroundRepeat: 'no-repeat'
            ,width: '100vw'
            ,height: '100%'
            }}>

                <Container >
                    <Placeholder xs={12} bg="transparent" />
                    <h1 className="text-dark">Welcome Home {this.state.userName}</h1>
                    <Placeholder xs={12} bg="transparent" />
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
                    <Placeholder xs={12} bg="transparent" />
                    <h2 className="text-dark">My Dashboard</h2>
                    <Placeholder xs={12} bg="transparent" />
                    <Row>
                        <Col >
                            <Card className="card h-100" bg={variant.toLowerCase()} >
                                <Card.Body>
                                    <Card.Title className="text-danger">Employee Management</Card.Title>
                                    <Card.Text>
                                        employee 123 has updated her status
                                    </Card.Text>
                                </Card.Body>
                            </Card>
                        </Col>
                        <Col>
                            <Card className="card h-100" bg={variant.toLowerCase()} >
                                <Card.Body>
                                    <Card.Title className="text-warning">Todos</Card.Title>
                                    <Card.Text>
                                        you have 5 undone task!
                                    </Card.Text>
                                </Card.Body>


                            </Card>
                        </Col>
                        <Col>
                            <Card className="card h-100 mb-2" bg={variant.toLowerCase()}>
                                <Card.Body>
                                    <Card.Title className="text-success">Today's Top News!</Card.Title>
                                    <Card.Text>
                                        BERRAKING NEWS COVID CASES HIGH AF!!
                                    </Card.Text>
                                </Card.Body>


                            </Card>
                        </Col>
                        <Col>
                            <Card className="card h-100" bg={variant.toLowerCase()}>
                                <Card.Body>
                                    <Card.Title className="text-primary">Need Help?</Card.Title>
                                    <Card.Text>
                                        Top Faqs today
                                    </Card.Text>
                                </Card.Body>


                            </Card>
                        </Col>
                    </Row>
                    <Placeholder xs={12} bg="transparent" />
                    <Card>
                        <iframe className="text-light" height="600px" width="100%;" src="https://infographics.channelnewsasia.com/covid-19/sgcovid19chart.html?type=embed&amp;channel=cna"></iframe>
                    </Card>
                    <Placeholder xs={12} bg="transparent" />
                    <Card>
                        <iframe width="100%" height="600" scrolling="no" src="https://infographics.channelnewsasia.com/covid-19/asia-covid-19-daily-cases.html?type=embed&amp;channel=cna" ></iframe>
                        {/* <iframe src="https://public.domo.com/cards/bWxVg" width="50%" height="600" marginheight="0" marginwidth="0" frameborder="0"></iframe> */}
                    </Card>
                </Container>
            </div>


        ))
    }

    retrieveWelcomeMessage() {
        HelloWorldService.executeHelloWorldService(this.state.userName)
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