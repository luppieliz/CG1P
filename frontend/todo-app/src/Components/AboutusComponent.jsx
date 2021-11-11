
import Card from 'react-bootstrap/Card';
import Container from 'react-bootstrap/Container';
import Button from 'react-bootstrap/Button';
import Row from 'react-bootstrap/Row';
import Placeholder from 'react-bootstrap/Placeholder';
import React, { Component } from 'react';
import { Link } from 'react-router-dom'





class AboutusComponent extends Component {
    render() {
        return (
            <>
            <div>
                <Container>
                <Placeholder xs={12} bg="transparent"  />
                <Placeholder xs={12} bg="transparent"  />
                <Placeholder xs={12} bg="transparent"  />
                <h1  className="text-dark cg1p-header" >About Us</h1>
                <Placeholder xs={12} bg="transparent"  />
                <Placeholder xs={12} bg="transparent"  />
                <h3 class="text-dark ">Buddy-19 serves as both a
            one-stop information hub and a companion tool, providing services for business owners to better understand and manage
            the measures they have to take in the face of the pandemic.
            </h3>
                <Placeholder xs={12} bg="transparent" />
                <Placeholder xs={12} bg="transparent" />
                    <Row>
                        <Card className="text-dark card h-150" style={{ width: '13rem', margin: '2px' }}>
                            <Card.Img  width={171} height={400} variant="top" src="https://www.sideshow.com/storage/product-images/907470/red-ranger_mighty-morphin-power-rangers_silo_sm.png" />
                            <Card.Body>
                                <Card.Title>Marcus</Card.Title>
                                <Link to={{pathname: "https://www.linkedin.com/in/marcusgohsh/"}} target="_blank"><Button variant="outline-dark">Learn more</Button></Link>
                            </Card.Body>
                        </Card>
                        <Card className="text-dark card h-150" style={{ width: '13rem', margin: '2px' }}>
                            <Card.Img  width={171} height={400} variant="top" src="https://www.sideshow.com/storage/product-images/907474/blue-ranger_mighty-morphin-power-rangers_silo.png" />
                            <Card.Body>
                                <Card.Title>Anrev</Card.Title>
                                <Link to={{pathname: "https://www.linkedin.com/in/anrev/"}} target="_blank"><Button variant="outline-dark">Learn more</Button></Link>
                            </Card.Body>
                        </Card>
                        <Card className="text-dark card h-150" style={{ width: '13rem', margin: '2px' }}>
                            <Card.Img  width={171} height={400} variant="top" src="https://static.wikia.nocookie.net/powerrangers/images/7/7c/Mmpr-yellow.png" />
                            <Card.Body>
                                <Card.Title>Liz</Card.Title>
                                <Link to={{pathname: "https://www.linkedin.com/in/lizabethlee/"}} target="_blank"><Button variant="outline-dark">Learn more</Button></Link>
                            </Card.Body>
                        </Card>
                        <Card className="text-dark card h-150" style={{ width: '13rem', margin: '2px' }}>
                            <Card.Img  width={171} height={400} variant="top" src="https://static.wikia.nocookie.net/powerrangers/images/e/e9/Mmpr-pinkm.png" />
                            <Card.Body>
                                <Card.Title>Jasmine</Card.Title>
                                <Link to={{pathname: "https://www.linkedin.com/in/jasmine-quek-a05317207/"}} target="_blank"><Button variant="outline-dark">Learn more</Button></Link>
                            </Card.Body>
                        </Card>
                        <Card className="text-dark card h-150" style={{ width: '13rem', margin: '2px' }}>
                            <Card.Img  width={171} height={400} variant="top" src="https://static.wikia.nocookie.net/powerrangers/images/5/55/Mmpr-green4.png" />
                            <Card.Body>
                                <Card.Title>Shawn</Card.Title>
                                <Link to={{pathname: "https://www.linkedin.com/"}} target="_blank"><Button variant="outline-dark">Learn more</Button></Link>
                            </Card.Body>
                        </Card>
                        <Card className="text-dark card h-150" style={{ width: '13rem', margin: '2px' }}>
                            <Card.Img  width={171} height={400} variant="top" src="https://static.wikia.nocookie.net/powerrangers/images/c/cf/Mmpr-black.png" />
                            <Card.Body>
                                <Card.Title>Minh</Card.Title>
                                <Link to={{pathname: "https://www.linkedin.com/in/vuquangminh3172/"}} target="_blank"><Button variant="outline-dark">Learn more</Button></Link>
                            </Card.Body>
                        </Card>
                    </Row>
                </Container>
                <Placeholder xs={12} bg="transparent"  />
                <Placeholder xs={12} bg="transparent"  />
                <Placeholder xs={12} bg="transparent"  />
                <Placeholder xs={12} bg="transparent"  />
                <Placeholder xs={12} bg="transparent"  />
                </div>
            </>
        )
    }
}

export default AboutusComponent;