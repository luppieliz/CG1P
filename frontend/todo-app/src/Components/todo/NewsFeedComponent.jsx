import React, {Component} from "react";
import Card from 'react-bootstrap/Card'
import Col from "react-bootstrap/Col";
import Row from "react-bootstrap/Row";
import Container from "react-bootstrap/Container";
import Placeholder from "react-bootstrap/Placeholder";
import NewsDataService from '../../api/todo/NewsDataService';
import Multiselect from 'multiselect-react-dropdown';
import '../../newsfeed.css';

class NewsFeedComponent extends Component {

    constructor(props) {
        super(props)

        //state contains array of news items taken from database through api call
        this.state = {
            news: [],
            newsDisplay : [],
            isEmpty: false,
            tagsSelected: {},
            options: []
        }
        // this.updateTodoClicked = this.updateTodoClicked.bind(this);
        // this.onSelect = this.onSelect().bind(this);
        // this.onRemove = this.onRemove().bind(this);
    }

    componentDidMount() {
        this.refreshNews();
        this.refreshTaglist();
    }

    //function to retrieve all news items from the database
    refreshNews() {
        //implement retrieve by date
        NewsDataService.retrieveAllNews()
            .then(
                response => {
                    this.state.isEmpty = response.data.length === 0;
                    this.setState({news:response.data})
                    this.setState({newsDisplay:response.data})
                    // console.log("retrieveall made");
                    console.log(response);
                }
            )
    }

    //function to get all the tags from the system TODO
    refreshTaglist() {
        //this method fetches all the tags in the database to populate the filter
        //todo implement fetching from database to replace hardcoding
        var output = [{value: 'Healthcare'},
            {value: 'Tourism'}
            ];
        this.setState({options:output})
    }

    //function to call the api to push news into database DEV ONLY
    apiButtonClicked() {
        NewsDataService.triggerNewsApi()
            .then(
                response => {
                    console.log("triggered api, storing to database");
                    console.log(response);
                }
            )
    }

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
            this.setState({newsDisplay:this.state.news});
        } else {
            this.setState({newsDisplay: output});
        }
    }

    //function for when a filter option is selected
    onSelect = (selectedList, selectedItem) => { //note usage of arrow function here, necessary as this refers to multiselect, not newsfeed component see https://stackoverflow.com/questions/32317154/react-uncaught-typeerror-cannot-read-property-setstate-of-undefined?rq=1
        var tag = selectedItem['value'];
        // console.log(selectedItem['value']);
        // this.setState({tagsSelected: {tag:true}});
        this.state.tagsSelected[tag] = true;
        console.log(JSON.stringify(this.state.tagsSelected));
        // console.log("list: " + JSON.stringify(selectedList) + " item: " + JSON.stringify(selectedItem));
    }

    //function for when a filter option is removed
    onRemove = (selectedList, removedItem) => {
        var tag = removedItem['value'];
        // this.setState({tagsSelected: {tag:false}});
        this.state.tagsSelected[tag] = false;
        console.log(JSON.stringify(this.state.tagsSelected));
        // console.log("list: " + JSON.stringify(selectedList) + " item: " + JSON.stringify(removedItem));
    }

    //root component of NewsFeed that displays header, footer, and news cards
    render () {

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
            newsFeedColumn = <Row xs={1} md={1} className="g-4">
                {Array.from(this.state.newsDisplay, (_, idx) => (
                    <Col>
                        <Card bg={'dark'}>
                            {/*<Card.Img variant="top" src="holder.js/100px160" />*/}
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
                                <small className="text-muted">{this.processTags(this.state.newsDisplay[idx].tagList)}</small>
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
            selectedValues={this.state.selectedValue} // Preselected value to persist in dropdown
            onSelect={this.onSelect} // Function will trigger on select event
            onRemove={this.onRemove} // Function will trigger on remove event
            displayValue="value" // Property name to display in the dropdown options
        />


        //==========Final render==========//
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
                        <Col xs={8}>
                            {dropdown}
                        </Col>
                        <Col>
                            <button className = "btn btn-success" onClick={() => this.showFilteredClicked()}>Filter</button>
                        </Col>
                        <Col>
                                <button className = "btn btn-secondary" onClick={() => this.apiButtonClicked()}>Fetch News</button>
                                {/*//todo: make buttons toggle colour when toggled*/}
                        </Col>
                    </Row>
                    <Row>

                        {/*news column*/}
                        {/*see newsFeedColumn definition for more information*/}
                        {newsFeedColumn}

                    </Row>

                </Container>
            </div>
        )
    }
}

export default NewsFeedComponent;