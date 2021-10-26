import React, { Component } from 'react'
// import routing features
import { Link } from 'react-router-dom'
// auth service
import AuthenticationService from './AuthenticationService.js'
// import withRouter to wrap - auto update header based on auth
import { withRouter } from 'react-router';

import NavDropdown from 'react-bootstrap/NavDropdown';

import moment from 'moment'
import Offcanvas from 'react-bootstrap/Offcanvas'
import Button from 'react-bootstrap/Button'
import Container from 'react-bootstrap/Container'
import Navbar from 'react-bootstrap/Navbar'
import Row from 'react-bootstrap/Row'
import Col from 'react-bootstrap/Col'
import Card from 'react-bootstrap/Card'
import Nav from 'react-bootstrap/Nav'


// Header with navigation features as a navbar. Shows different things based on whether user is logged in.
class HeaderComponent extends Component {

    constructor(props) {

        super(props);
        this.handleShow = this.handleShow.bind(this);
        this.handleClose = this.handleClose.bind(this);

        this.state = {
            showSidebar: false,
            showModal: false
        }
    }

    handleClose() { //sidebar
        this.setState({ showSidebar: false });
    }

    handleShow() { //sidebar
        this.setState({ showSidebar: true });
    }

    render() {
        const isUserLoggedIn = AuthenticationService.isUserLoggedIn();
        return (
            <header>
                <Navbar bg="dark" expand={false} >
                    <Container fluid>
                        <Navbar.Brand className="text-light" href="/"><h4>Buddy-19</h4></Navbar.Brand>

                        <Button variant="primary" onClick={this.handleShow}>MENU</Button>

                        <Offcanvas show={this.state.showSidebar} onHide={this.handleClose}>
                            <Offcanvas.Header closeButton>
                                <Offcanvas.Title className="text-dark">Menu</Offcanvas.Title>
                            </Offcanvas.Header>
                            <Offcanvas.Body className="text-dark">
                                <Row>
                                    <Col>
                                        <Card className="text-dark">
                                            {/* <Card.Img variant="top" src="holder.js/100px180" /> */}
                                            <Card.Body>
                                                <Card.Text>
                                                    <h1>date maybe or calender</h1>
                                                </Card.Text>
                                            </Card.Body>
                                            <Card.Text className="text-dark" >
                                                <h4>{isUserLoggedIn && <Link className="nav-link" to="/welcome/admin">Home</Link>}
                                                    {isUserLoggedIn && <Link className="nav-link" to="/todos">Dashboard</Link>}
                                                    {!isUserLoggedIn && <Link className="nav-link" to="/news">News</Link>}
                                                    {isUserLoggedIn && <Link className="nav-link" to="/news">News</Link>}
                                                    {isUserLoggedIn && <Link className="nav-link" to="/mylist">mylist</Link>}
                                                    {isUserLoggedIn && <Link className="nav-link" to="/profile">Profile</Link>}
                                                    {!isUserLoggedIn && <Link className="nav-link" to="/login">Login</Link>}
                                                    {!isUserLoggedIn && <Link className="nav-link" to="/signup">SignUp</Link>}
                                                    {isUserLoggedIn && <Link className="nav-link" to="/logout" onClick={AuthenticationService.logout}>Logout</Link>}
                                                    <NavDropdown
                                                        id="nav-dropdown-dark-example"
                                                        title="Questions"
                                                        menuVariant="dark"
                                                    >
                                                        {!isUserLoggedIn && <NavDropdown.Item ><Link className="nav-link" to="/faq">FAQ</Link></NavDropdown.Item>}
                                                        {isUserLoggedIn && <NavDropdown.Item ><Link className="nav-link" to="/faq">FAQ</Link></NavDropdown.Item>}
                                                        {!isUserLoggedIn && <NavDropdown.Item ><Link className="nav-link" to="/aboutus">About Us</Link></NavDropdown.Item>}
                                                        {isUserLoggedIn && <NavDropdown.Item ><Link className="nav-link" to="/aboutus">About Us</Link></NavDropdown.Item>}
                                                        {!isUserLoggedIn && <NavDropdown.Item ><Link className="nav-link" to="/contactus">Contact Us</Link></NavDropdown.Item>}
                                                        {isUserLoggedIn && <NavDropdown.Item ><Link className="nav-link" to="/contactus">Contact Us</Link></NavDropdown.Item>}
                                                    </NavDropdown>
                                                </h4>
                                            </Card.Text>
                                        </Card>
                                    </Col>
                                </Row>
                            </Offcanvas.Body>
                        </Offcanvas>
                    </Container>
                </Navbar>
            </header>
            // <header>


            //     <nav className="navbar navbar-expand-md navbar-dark bg-dark">
            //         <h4><a href="/" className="navbar-brand">Buddy-19</a></h4>
            //         <ul className="navbar-nav">
            //             {isUserLoggedIn && <li><Link className="nav-link" to="/welcome/admin">Home</Link></li>}
            //             {isUserLoggedIn && <li><Link className="nav-link" to="/todos">Dashboard</Link></li>}
            //             {!isUserLoggedIn && <li><Link className="nav-link" to="/news">News</Link></li>}
            //             {isUserLoggedIn && <li><Link className="nav-link" to="/news">News</Link></li>}

            //             <NavDropdown
            //                 id="nav-dropdown-dark-example"
            //                 title="Questions"
            //                 menuVariant="dark"
            //                 >
            //                 {!isUserLoggedIn && <NavDropdown.Item ><Link className="nav-link" to="/faq">FAQ</Link></NavDropdown.Item>}
            //                 {isUserLoggedIn && <NavDropdown.Item ><Link className="nav-link" to="/faq">FAQ</Link></NavDropdown.Item>}
            //                 {!isUserLoggedIn && <NavDropdown.Item ><Link className="nav-link" to="/aboutus">About Us</Link></NavDropdown.Item>}
            //                 {isUserLoggedIn && <NavDropdown.Item ><Link className="nav-link" to="/aboutus">About Us</Link></NavDropdown.Item>}
            //                 {!isUserLoggedIn && <NavDropdown.Item ><Link className="nav-link" to="/contactus">Contact Us</Link></NavDropdown.Item>}
            //                 {isUserLoggedIn && <NavDropdown.Item ><Link className="nav-link" to="/contactus">Contact Us</Link></NavDropdown.Item>}

            //                 </NavDropdown>


            //         </ul>
            //         <ul className="navbar-nav navbar-collapse justify-content-end">
            //             {isUserLoggedIn && <li><Link className="nav-link" to="/mylist">mylist</Link></li>}
            //             {isUserLoggedIn && <li><Link className="nav-link" to="/profile">Profile</Link></li>}
            //             {!isUserLoggedIn && <li><Link className="nav-link" to="/login">Login</Link></li>}
            //             {isUserLoggedIn && <li><Link className="nav-link" to="/logout" onClick={AuthenticationService.logout}>Logout</Link></li>} 
            //             {/* link for profile, news not implemented */}

            //         </ul>
            //     </nav>
            // </header>
        )
    }
}

export default withRouter(HeaderComponent);