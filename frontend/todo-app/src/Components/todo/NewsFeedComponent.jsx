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
            news: [],
            isEmpty: false
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
                    this.state.isEmpty = response.data.length === 0;
                    this.setState({news:response.data})
                    console.log("retrieveall made");
                    console.log(response);
                }
            )
    }

    tagClicked(tags) {
        NewsDataService.retrieveNewsWithTags(tags)
            .then(
                response => {
                    this.state.isEmpty = response.data.length === 0;
                    this.setState({news:response.data})
                    console.log("retrieve by tag made. tags: " + tags);
                    console.log(response);
                    console.log("this.state.isEmpty: " + this.state.isEmpty)
                }
            )
    }
    //root component of NewsFeed that displays header, footer, and news cards
    render () {
        let newsFeedColumn; //display empty message if there is no news in this.state
        if (this.state.isEmpty) {
            newsFeedColumn = <Card className="text-dark">
                <Card.Body>
                    <Card.Text>
                        <h1>empty!</h1>
                    </Card.Text>
                </Card.Body>
            </Card>;
        } else {
            //iterate through all this.state.news items and display them in card form
            newsFeedColumn = <Row xs={1} md={1} className="g-4">
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
        }


        //return the render
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
                    <Row>
                        {/*test column*/}
                        Filter by:
                        <Col>
                            <button className = "btn btn-success" onClick={() => this.tagClicked("tagname")}>tagname</button>
                            <button className = "btn btn-success" onClick={() => this.tagClicked("all")}>all</button>
                        </Col>
                        {/*news column*/}
                        <Col>
                            {/*see newsFeedColumn definition for more information*/}
                            {newsFeedColumn}


                        </Col>
                    </Row>

                </Container>
            </div>
        )
    }
}

export default NewsFeedComponent;