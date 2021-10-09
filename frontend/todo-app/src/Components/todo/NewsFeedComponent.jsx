import React, {Component} from "react";
import Card from 'react-bootstrap/Card'
import Col from "react-bootstrap/Col";
import Row from "react-bootstrap/Row";
import Container from "react-bootstrap/Container";
import Placeholder from "react-bootstrap/Placeholder";

class NewsFeedComponent extends Component {

    //root component of NewsFeed that displays header, footer, and news cards
    render () {
        return (
            <div>
                <Card className="text-dark">
                    {/* <Card.Img variant="top" src="holder.js/100px180" /> */}
                    <Card.Body>
                        <Card.Text>
                            <h1>News</h1>
                        </Card.Text>
                    </Card.Body>
                </Card>

                <Placeholder xs={12} bg="dark" className="bg-black" />
                <Placeholder xs={12} bg="dark" className="bg-black" />
                <Container>
                    <Row xs={1} md={1} className="g-4">
                        {Array.from({ length: 4 }).map((_, idx) => (
                            <Col>
                                <Card bg={'dark'}>
                                    <Card.Img variant="top" src="holder.js/100px160" />
                                    <Card.Body>
                                        <Card.Title>News headline {idx}</Card.Title>
                                        <Card.Text>
                                            This is a longer card with supporting text below as a natural
                                            lead-in to additional content. This content is a little bit longer.
                                        </Card.Text>
                                    </Card.Body>
                                </Card>
                            </Col>
                        ))}
                    </Row>
                </Container>
            </div>
        )
    }
}

export default NewsFeedComponent;