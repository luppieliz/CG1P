import React, {Component} from "react";
import Card from 'react-bootstrap/Card'
import Col from "react-bootstrap/Col";
import Row from "react-bootstrap/Row";
import Container from "react-bootstrap/Container";
import Placeholder from "react-bootstrap/Placeholder";
import NewsDataService from '../../api/todo/NewsDataService';

class NewsFeedComponent extends Component {

    constructor(props) {
        super(props)

        //state contains array of news items taken from database through api call
        this.state = {
            news: []
        }
    }

    componentDidMount() {
        this.refreshNews();
    }

    refreshNews() {
        //implement search by tags
        NewsDataService.retrieveAllNews()
            .then(
                response => {
                    this.setState({news:response.data})
                    console.log("retrieveall made");
                    console.log(response);
                }
            )
    }

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
                        {Array.from(this.state.news, (_, idx) => (
                            <Col>
                                <Card bg={'dark'}>
                                    {/*<Card.Img variant="top" src="holder.js/100px160" />*/}
                                    <Card.Body>
                                        <Card.Title>
                                            <Card.Link href={this.state.news[idx].url}>
                                                {this.state.news[idx].title}
                                            </Card.Link>
                                        </Card.Title>
                                        <Card.Text>
                                            {this.state.news[idx].description}
                                        </Card.Text>
                                    </Card.Body>
                                    <Card.Footer>
                                        <small className="text-muted">tags go here</small>
                                    </Card.Footer>
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