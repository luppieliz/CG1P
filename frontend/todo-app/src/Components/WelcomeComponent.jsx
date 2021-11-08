import React, { Component } from 'react'
// import routing features
import HelloWorldService from '../api/HelloWorldService'
import UserDataService from '../api/UserDataService'

import Card from 'react-bootstrap/Card';
import Placeholder from 'react-bootstrap/Placeholder';
import Container from 'react-bootstrap/Container';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';
import { SESSION_USER_ID, SESSION_USER_NAME } from '../Constants';

import FormControlLabel from '@mui/material/FormControlLabel';
import Switch from '@mui/material/Switch';

import ListTodosComponent from "./ListTodosComponent";
import SideBarComponent from "./SideBarComponent";




// Welcome landing page, that links to the Todos page.
class WelcomeComponent extends Component {
    constructor(props) {
        super(props);
        this.retrieveWelcomeMessage = this.retrieveWelcomeMessage.bind(this);
        this.handleSuccessfulResponse = this.handleSuccessfulResponse.bind(this);
        this.handleError = this.handleError.bind(this);
        this.retrieveHealthStatus = this.retrieveHealthStatus.bind(this);
        this.toggleCovidStatus = this.toggleCovidStatus.bind(this);
        this.toggleShnStatus = this.toggleShnStatus.bind(this);

        this.handleShow = this.handleShowModal.bind(this);
        this.handleClose = this.handleCloseModal.bind(this);

        this.state = {
            userId: sessionStorage.getItem(SESSION_USER_ID),
            userName: sessionStorage.getItem(SESSION_USER_NAME),
            welcomeMessage: '',
            errorMessage: '',
            shnStatus: false,
            covidStatus: false,
            show : "false",
            setShow : "false"
        }
    }

    componentDidMount() {
        this.retrieveHealthStatus()
        this.setState({ showModal: false });
        
    }

    handleCloseModal() { //MODAL 
        this.setState({ showModal: false });
    }

    handleShowModal() { //MODAL
        this.setState({ showModal: true });
    }

    render() {

        return [
            'white',
        ].map((variant, idx) => (
            <div>
                <Placeholder xs={12} bg="transparent" />
                <h1 className="text-dark">Welcome Home {this.state.userName}</h1>
                <Placeholder xs={12} bg="transparent" />

                <Container >
                    <Row>
                        <Col>
                            <Card>
                                <h2 className="text-dark">Checklist</h2>
                            </Card>
                            <Card>
                                <SideBarComponent />
                            </Card>
                        </Col>
                        <Col xs={5}>
                            <Card ><h2 className="text-dark">Announcements</h2></Card>

                            <Card className="card" bg={variant.toLowerCase()} >
                                <Card.Body>
                                    <Card.Title className="text-danger">Employee Management</Card.Title>
                                    <Card.Text className="text-dark">
                                        employee 123 has updated her status
                                    </Card.Text>
                                </Card.Body>
                            </Card>


                            <Card className="card" bg={variant.toLowerCase()} >
                                <Card.Body>
                                    <Card.Title className="text-warning">Todos</Card.Title>
                                    <Card.Text className="text-dark">
                                        you have 5 undone task!
                                    </Card.Text>
                                </Card.Body>
                            </Card>

                        </Col>
                        <Col>
                            <Card bg={variant.toLowerCase()} className="text-center">
                                <Card.Body>
                                    <Card.Title>
                                        <strong><h2 className="text-dark">
                                            My COVID Status</h2></strong>
                                    </Card.Title>
                                    <Card.Text>
                                        <h5 className="text-dark">
                                            <Card.Header>Am I on Stay-Home Notice : {this.state.shnStatus ? 'Yes' : 'No'}</Card.Header>
                                            <FormControlLabel onClick={this.toggleShnStatus} control={<Switch color="warning" />} label="" />


                                        </h5>
                                        <h5 className="text-dark">
                                            <Card.Header>Am I feeling the COVID : {this.state.covidStatus ? 'Yes' : 'No'}</Card.Header>
                                            <FormControlLabel onClick={this.toggleCovidStatus} control={<Switch color="warning" />} label="" />
                                        </h5>

                                    </Card.Text>
                                </Card.Body>
                            </Card>
                            <Placeholder xs={12} bg="transparent" />
                            <Card bg={variant.toLowerCase()} className="text-center">
                                <Card.Body>
                                    <Card.Title>
                                        <h3 className="container text-dark">
                                            Get your inspirational quote of the day</h3>
                                        <div className="d-grid gap-2">
                                            <button size="lg" onClick={this.retrieveWelcomeMessage} className="btn btn-success" >Here</button>
                                        </div>
                                    </Card.Title>
                                    <Card.Text>
                                        <h4 className="container text-dark">
                                            {this.state.welcomeMessage}
                                        </h4>
                                        <div className="container text-dark">
                                            {this.state.errorMessage}
                                        </div>
                                    </Card.Text>
                                </Card.Body>
                            </Card>
                        </Col>
                    </Row>
                   
                    <Row>
                        <Placeholder xs={12} bg="transparent" />
                        <Card>
                            <iframe className="text-light" height="600px" width="100%;" src="https://infographics.channelnewsasia.com/covid-19/sgcovid19chart.html?type=embed&amp;channel=cna"></iframe>
                        </Card>
                        <Placeholder xs={12} bg="transparent" />
                        <Card>
                            <iframe width="100%" height="600" scrolling="no" src="https://infographics.channelnewsasia.com/covid-19/asia-covid-19-daily-cases.html?type=embed&amp;channel=cna" ></iframe>
                            {/* <iframe src="https://public.domo.com/cards/bWxVg" width="50%" height="600" marginheight="0" marginwidth="0" frameborder="0"></iframe> */}
                        </Card>
                    </Row>
                </Container>
            </div>


        ))
    }

    retrieveWelcomeMessage() {
        HelloWorldService.executeHelloWorldService(this.state.userName)
            .then(response => this.handleSuccessfulResponse(response))
            .catch(error => this.handleError(error));
    }

    retrieveHealthStatus() {
        UserDataService.retrieveUser(this.state.userId).then(response => {
            this.setState({
                shnStatus: response.data.shnStatus,
                covidStatus: response.data.covidStatus
            })
        })
    }

    toggleCovidStatus() {
        UserDataService.retrieveUser(this.state.userId).then(response => {
            let user = {
                id: response.data.id,
                email: response.data.email,
                name: response.data.name,
                password: response.data.password,
                shnStatus: response.data.shnStatus,
                covidStatus: !(response.data.covidStatus),
                authority: response.data.authority,
                business: response.data.business
            }
            UserDataService.updateUser(user);
        })
        this.setState({ covidStatus: !this.state.covidStatus })
    }

    toggleShnStatus() {
        UserDataService.retrieveUser(this.state.userId).then(response => {
            let user = {
                id: response.data.id,
                email: response.data.email,
                name: response.data.name,
                password: response.data.password,
                shnStatus: !(response.data.shnStatus),
                covidStatus: response.data.covidStatus,
                authority: response.data.authority,
                business: response.data.business
            }
            UserDataService.updateUser(user);
        })
        this.setState({ shnStatus: !this.state.shnStatus })
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