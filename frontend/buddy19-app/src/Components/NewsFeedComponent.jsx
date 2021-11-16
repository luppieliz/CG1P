import React, {Component} from "react";
import Card from 'react-bootstrap/Card';
import Col from "react-bootstrap/Col";
import Row from "react-bootstrap/Row";
import Container from "react-bootstrap/Container";
import Placeholder from "react-bootstrap/Placeholder";
import NewsDataService from '../api/NewsDataService';
import Multiselect from 'multiselect-react-dropdown';
import Button from 'react-bootstrap/Button';

import '../newsfeed.css';
import {SESSION_USER_BUSINESS_INDUSTRY} from "../Constants";

class NewsFeedComponent extends Component {

    constructor(props) {
        super(props)

        this.state = {
            news: [],
            newsDisplay: [],
            isEmpty: false,
            tagsSelected: {},
            options: [],
            selectedValues: []
        }
    }

    //functions that run on load
    componentDidMount() {
        this.refreshNews();
    }

    //function to retrieve all news items from the database
    refreshNews() {
        //TODO implement retrieve by date
        NewsDataService.retrieveAllNews()
            .then(
                response => {
                    this.state.isEmpty = response.data.length === 0;
                    this.setState({news: response.data});
                    this.setState({newsDisplay: response.data});
                    this.generateTaglist();

                    var industry = sessionStorage.getItem(SESSION_USER_BUSINESS_INDUSTRY);
                    this.setState({
                        selectedValues: [{
                            value: industry
                        }]
                    });
                    this.setState({
                            tagsSelected: {
                                [industry]: true
                            }
                        },
                        this.showFilteredClicked);
                }
            )
    }

    //function to generate a list of tags based on news retrieved
    //todo: reduce time complexity
    generateTaglist() {
        var tags = {}; //list of existing tags
        var output = []; //output format: [{value:"tag1"},{value:"tag2"}]
        var idx = 0;
        for (var i in this.state.news) {
            if (this.state.news[i].tagList.length != 0) { //iterate through news items and find ones with tags
                var arr = this.state.news[i].tagList.split(",");
                for (var s in arr) {
                    if (!tags.hasOwnProperty(arr[s])) { //
                        tags[arr[s]] = 0;
                        // console.log("adding " + arr[s]);
                        output[idx] = {value: arr[s]};
                        idx++;
                    }
                }
                // console.log(this.state.news[i].tagList);

            }
        }
        this.setState({options: output})
    }

    //function to call the api to push news into database DEV ONLY

    //function to process the tags and display at the bottom of the card
    processTags(tagString) {
        if (tagString.length == 0) {
            return "No tags";
        } else {
            return tagString.split(",").join(", ");
        }
    }

    //function to apply filters
    showFilteredClicked() {
        var output = [];
        var idx = 0;
        for (var tag in this.state.tagsSelected) {
            if (this.state.tagsSelected[tag]) {
                for (var i in this.state.news) { //iterate through news with index i
                    if (this.state.news[i].tagList.includes(tag)) {
                        output[idx] = this.state.news[i];
                        idx++;
                    }
                }
            }
        }
        if (output.length == 0) {
            this.setState({newsDisplay: this.state.news});
        } else {
            this.setState({newsDisplay: output});
        }
    }

    //function for when a filter option is selected
    onSelect = (selectedList, selectedItem) => { //note usage of arrow function here, necessary as this refers to multiselect, not newsfeed component see https://stackoverflow.com/questions/32317154/react-uncaught-typeerror-cannot-read-property-setstate-of-undefined?rq=1
        var tag = selectedItem['value'];
        this.state.tagsSelected[tag] = true;
        console.log(JSON.stringify(this.state.tagsSelected));
    }

    //function for when a filter option is removed
    onRemove = (selectedList, removedItem) => {
        var tag = removedItem['value'];
        this.state.tagsSelected[tag] = false;
        console.log(JSON.stringify(this.state.tagsSelected));
    }

    //root component of NewsFeed that displays header, footer, and news cards
    render() {

        //==========Column for news feed to display filtered news==========//
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
            newsFeedColumn = <Row xs={1} md={2} className="g-4" style={{padding: "0"}}>
                {Array.from(this.state.newsDisplay, (_, idx) => (
                    <Col>
                        <Card bg={'light'} text={'dark'} style={{padding: "0"}}>
                            <Card.Img variant="top" src={this.processTags(this.state.newsDisplay[idx].urlToImage)}/>
                            <Card.Body>
                                <Card.Title>
                                    <Card.Link href={this.state.newsDisplay[idx].url}>
                                        {this.state.newsDisplay[idx].title}
                                    </Card.Link>
                                </Card.Title>
                                <Card.Text>
                                    {this.state.newsDisplay[idx].description}
                                </Card.Text>
                            </Card.Body>
                            <Card.Footer>
                                <small
                                    className="text-muted">{this.processTags(this.state.newsDisplay[idx].tagList)} | {this.state.newsDisplay[idx].publishedDate} | </small>
                            </Card.Footer>
                        </Card>
                    </Col>
                ))}
            </Row>
        }


        //==========Dropdown component to select filter options==========//
        //https://www.npmjs.com/package/multiselect-react-dropdown for more options //consider grouping?
        let dropdown = <Multiselect
            options={this.state.options} // Options to display in the dropdown
            selectedValues={this.state.selectedValues} // Preselected value to persist in dropdown
            onSelect={this.onSelect} // Function will trigger on select event
            onRemove={this.onRemove} // Function will trigger on remove event
            displayValue="value" // Property name to display in the dropdown options
        />


        //==========Final render==========//
        return (
            <div>
                <Placeholder xs={12} bg="transparent"/>
                <Placeholder xs={12} bg="transparent"/>
                <h1 className="text-dark cg1p-header">News</h1>
                <Placeholder xs={12} bg="transparent"/>
                <Placeholder xs={12} bg="transparent"/>

                <Container>
                    <Row>
                        <Col xs={11} style={{padding: "0"}}>
                            {dropdown}
                        </Col>
                        <Col style={{padding: "0"}}>
                            <Button variant="outline-dark" onClick={() => this.showFilteredClicked()}>Filter</Button>
                        </Col>
                    </Row>
                    <Placeholder xs={12} bg="transparent"/>
                </Container>

                <Container>
                    <Row>
                        {/*news column*/}
                        {/*see newsFeedColumn definition for more information*/}
                        {newsFeedColumn}
                    </Row>
                    <Placeholder xs={12} bg="transparent"/>
                </Container>
                <Placeholder xs={12} bg="transparent" style={{paddingBottom: "50vh"}}/>
            </div>
        )
    }
}

export default NewsFeedComponent;