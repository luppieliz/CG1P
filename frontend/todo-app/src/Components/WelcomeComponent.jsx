import React, { Component } from 'react'
// import routing features
import HelloWorldService from '../api/HelloWorldService'
import UserDataService from '../api/UserDataService'
import TodoDataService from '../api/TodoDataService'

import Card from 'react-bootstrap/Card';
import Placeholder from 'react-bootstrap/Placeholder';
import Container from 'react-bootstrap/Container';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';
import Offcanvas from 'react-bootstrap/Offcanvas'
import Button from 'react-bootstrap/Button'
import Tabs from 'react-bootstrap/Tabs'
import Tab from 'react-bootstrap/Tab'

import { GrUpdate } from "@react-icons/all-files/gr/GrUpdate";
import moment from 'moment'
import { GrAdd } from "@react-icons/all-files/gr/GrAdd";
import { FaTrashAlt } from "@react-icons/all-files/fa/FaTrashAlt";
import { SESSION_USER_ID, SESSION_USER_NAME } from '../Constants';

import FormControlLabel from '@mui/material/FormControlLabel';
import Switch from '@mui/material/Switch';



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

        this.refreshTodos = this.refreshTodos.bind(this)
        this.addTodoClicked = this.addTodoClicked.bind(this)
        this.updateTodoClicked = this.updateTodoClicked.bind(this)
        this.deleteTodoClicked = this.deleteTodoClicked.bind(this)

        this.handleShow = this.handleShow.bind(this);
        this.handleClose = this.handleClose.bind(this);


        this.state = {
            userId: sessionStorage.getItem(SESSION_USER_ID),
            userName: sessionStorage.getItem(SESSION_USER_NAME),
            welcomeMessage: '',
            errorMessage: '',
            shnStatus: false,
            covidStatus: false,
            showSidebar: false,
            todos: [],
            message: null

        }
    }

    componentDidMount() {
        this.retrieveHealthStatus()
        this.refreshTodos()
        this.setState({ showSidebar: false });

    }

    handleClose() { //sidebar
        this.setState({ showSidebar: false });
    }

    handleShow() { //sidebar
        this.setState({ showSidebar: true });
    }

    // same as above, but for subsequent refreshes
    refreshTodos() {
        TodoDataService.retrieveAllTodos(this.state.userId)
            .then(response => this.setState({ todos: response.data }))
    }

    // handler for when add todo is clicked
    addTodoClicked() {
        this.props.history.push(`/todos/-1`)
    }

    // handler for when update todo is clicked
    updateTodoClicked(todoId) {
        this.props.history.push(`/todos/${todoId}`)
    }

    // handler for when delete todo is clicked
    deleteTodoClicked(todoId) {
        TodoDataService.deleteTodo(this.state.userId, todoId)
            .then(() => {
                this.setState({ message: `Delete of todo ${todoId} was successful` })
                this.refreshTodos()
            })
    }


    render() {

        let { description, targetDate } = this.state
        return [
            'white',
        ].map((variant, idx) => (
            <div style={{
                
            }}>
                <Placeholder xs={12} bg="transparent" />
                <h1 className="text-dark">Welcome Home {this.state.userName}</h1>
                <Placeholder xs={12} bg="transparent" />

                <Container>
                    <Row>
                        <Col>
                            <Card>
                                <Container>
                                <Row>
                                    <Button variant="transparent" onClick={this.handleShow}>
                                        <h2 className="text-dark">Checklist</h2>
                                    </Button></Row>
                                    <Offcanvas show={this.state.showSidebar} onHide={this.handleClose}>
                                        <Offcanvas.Header closeButton>
                                            <Offcanvas.Title className="text-dark">Checklist</Offcanvas.Title>
                                        </Offcanvas.Header>
                                        <Offcanvas.Body className="text-dark">
                                            <Row>
                                                <Col>

                                                    <Card className="text-dark">
                                                        {/* <Card.Img variant="top" src="holder.js/100px180" /> */}
                                                        <Card.Body>
                                                            <Card.Text>
                                                                <iframe src="https://free.timeanddate.com/clock/i81r8hlp/n236/tlsg/fn6/fs20/fcfff/tc000/ftb/bacfff/pa8/tt0/tw1/th2/ta1/tb4" frameborder="0" width="333" height="68"></iframe>
                                                            </Card.Text>
                                                        </Card.Body>
                                                    </Card>
                                                    <div className="text-dark">{this.state.message && <div className="alert alert-success">{this.state.message}</div>}
                                                        <table className="table text-dark">
                                                            <thead>
                                                                <tr>
                                                                    <th>Task</th>
                                                                    <th>Done?</th>
                                                                    <th>Due</th>
                                                                    <th>Update</th>
                                                                    <th>Delete</th>
                                                                </tr>
                                                            </thead>
                                                            <tbody>
                                                                {
                                                                    // script that for each todo, map it and display id, desc, done, targetDate, and buttons
                                                                    this.state.todos.map(
                                                                        todo =>
                                                                            <tr key={todo.id}>
                                                                                <td>{todo.description}</td>
                                                                                <td>{todo.isDone.toString()}</td>
                                                                                <td>{moment(todo.targetDate).format('YYYY-MM-DD')}</td>
                                                                                <td><button className="btn btn-warning" onClick={() => this.updateTodoClicked(todo.id)}><GrUpdate /></button></td>
                                                                                <td><button className="btn btn-danger" onClick={() => this.deleteTodoClicked(todo.id)}><FaTrashAlt /></button></td>
                                                                            </tr>
                                                                    )
                                                                }
                                                            </tbody>
                                                        </table>
                                                        <div className="d-grid gap-2"><button className="btn btn-success" onClick={() => this.addTodoClicked()}><GrAdd /></button></div>
                                                    </div>
                                                </Col>
                                            </Row>
                                        </Offcanvas.Body>
                                    </Offcanvas>
                                </Container>
                            </Card>
                            <Card>
                            <iframe width="300px" height="350px" src="https://data.gov.sg/dataset/covid-19-case-numbers/resource/99334c54-479f-472d-a6f5-fe38d2f9b1aa/view/c5b1cfad-f0c8-43e8-9bce-ea541923e9e7" frameBorder="0"> </iframe>
                            </Card>
                        </Col>
                        <Col xs={5}>
                            <Card ><h2 className="text-dark">Announcements</h2></Card>
                            <Tabs defaultActiveKey="profile" id="uncontrolled-tab-example" className="mb-3">
                                <Tab eventKey="Dining" title="Dining">
                                    <Card className = "text-dark">
                                        <Card.Text>
                                        First, we will allow up to five fully vaccinated persons
                                         from the same household to dine-in together at food and beverage 
                                         (F&B) establishments that are able to administer comprehensive VDS checks. 
                                        </Card.Text>
                                    </Card>
                                </Tab>
                                <Tab eventKey="vaccine" title="Vaccine">
                                <Card className = "text-dark">
                                        <Card.Text>
                                        Progress in vaccination and boosters has contributed immensely to the tempering of COVID-19 cases.
                                         However, those who are not fully vaccinated continue to disproportionately 
                                         make up the bulk of severe and ICU cases, and impose a strain on our healthcare system
                                         . There remains a need to protect them. Vaccination-Differentiated Safe Management Measures 
                                         (VDS) will therefore remain a crucial prong of our re-opening strategy in the coming weeks.
                                        </Card.Text>
                                    </Card>
                                </Tab>
                                <Tab eventKey="travel" title="Travel">
                                <Card className = "text-dark">
                                        <Card.Text>
                                        Finally, our experience to date with the Vaccinated Travel Lanes (VTLs) 
                                        has shown that we can open up air travel in a safe way. 
                                        We will further simplify travel protocols to safely and seamlessly 
                                        reconnect with the rest of the world.
                                        </Card.Text>
                                    </Card>
                                </Tab>
                                <Tab eventKey="healthcare" title="Healthcare">
                                <Card className = "text-dark">
                                        <Card.Text>
                                        Daily case numbers have stabilised for around three weeks now.
                                         While cases have remained at more than 3,000 a day on average,
                                          close to 99% of cases continue to have mild or no symptoms and 
                                          the vast majority are able to recover well at home.
                                           The proportion of patients who require oxygen supplementation 
                                           has held steady at 0.8% of our total cases, and those who require 
                                           ICU care at 0.3%, in the past 28 days. 
                                           The number of cases in the ICU remains high but stable
                                            at around 140 cases, who occupy 70% of our current ICU bed capacity. 
                                            We have been actively expanding the capacity of COVID-19 Treatment Facilities 
                                            (CTFs) and Community Isolation Facilities (CIFs) over the past few weeks to take in 
                                            COVID-19 patients who do not require acute care in hospitals. 
                                        </Card.Text>
                                    </Card>
                                </Tab>
                                <Tab eventKey="Chilren" title="Children">
                                <Card className = "text-dark">
                                        <Card.Text>
                                        The Expert Committee on COVID-19 Vaccination (EC19V) has
                                         noted that the Pfizer BioNTech/ Comirnaty vaccine has been 
                                         approved by the US for use in those aged 5 to 11 years.
                                          EC19V is assessing the extension of vaccination to children 
                                          aged 5 to 11 years in Singapore. This will confer on them 
                                          protection against infection and severe illness, and bette
                                          r enable the resumption of a richer educational experience 
                                          for our school children in 2022. 
                                        </Card.Text>
                                    </Card>
                                </Tab>
                                <Tab eventKey="Work" title="Work">
                                <Card className = "text-dark">
                                        <Card.Text>
                                        From 1 January 2022, employers must ensure that unvaccinated
                                         employees do not return to the workplace.
                                         1 These individuals are at a higher risk of 
                                         falling seriously ill from COVID-19 infection and 
                                         putting a strain on our healthcare capacity.
                                          Please refer to the Updated Advisory on COVID-19 
                                          Vaccination at the Workplace for more details on 
                                          work arrangements for unvaccinated employees.
                                        </Card.Text>
                                    </Card>
                                </Tab>
                            </Tabs>

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
                            <Card className="text-dark">
                                <h2>Latest Covid-19 Cases updates</h2>
                            </Card>
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

                    <Row>
                        <Placeholder xs={12} bg="transparent" />
                            <Card className="text-dark">
                                <h2>Plan your next business Trip</h2>
                                
                            </Card>
                            <Placeholder xs={12} bg="transparent" />
                            <iframe className="text-light" height="800px" width="100%;" scrolling="no" src="https://www.farecompare.com/maps/SIN-"></iframe>
                            <Placeholder xs={12} bg="transparent" />
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