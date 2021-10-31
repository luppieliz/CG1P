import React, { Component } from 'react'
// import routing features
import { Link } from 'react-router-dom'
import HelloWorldService from '../api/todo/HelloWorldService'
import { SESSION_EMAIL } from '../Constants'

// Welcome landing page, that links to the Todos page.
class WelcomeComponent extends Component {
    constructor(props) {
        super(props)
        this.retrieveWelcomeMessage = this.retrieveWelcomeMessage.bind(this)
        this.handleSuccessfulResponse = this.handleSuccessfulResponse.bind(this)
        this.handleError = this.handleError.bind(this)

        this.state = {
            email: sessionStorage.getItem(SESSION_EMAIL),
            welcomeMessage: '',
            errorMessage: ''
        }
    }

    render() {
        return (
            <>
                <h1 className="text-white">Welcome</h1>
                <div className="container text-white">
                    Welcome {this.state.email}. You can manage your todos <Link to="/todos">here</Link>
                </div>
                <div className="container text-white">
                    Click here to get a customized welcome message.
                    <button onClick={this.retrieveWelcomeMessage} className="btn btn-success">Get Welcome Message</button>
                </div>
                <div className="container text-white">
                    {this.state.welcomeMessage}
                </div>
                <div className="container text-white">
                    {this.state.errorMessage}
                </div>
            </>
        )
    }

    retrieveWelcomeMessage() {

        // HelloWorldService.executeHelloWorldService()
        //     .then(response => this.handleSuccessfulResponse(response));

        // HelloWorldService.executeHelloWorldBeanService()
        //     .then(response => this.handleSuccessfulResponse(response));

        HelloWorldService.executeHelloWorldPathVariableService(this.state.email)
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

export default WelcomeComponent