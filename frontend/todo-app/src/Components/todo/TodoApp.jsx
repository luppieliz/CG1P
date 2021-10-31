// import react
import React, { Component } from 'react'
// auth route, make sure route is valid for user
import AuthenticatedRoute from './AuthenticatedRoute'
// import routing features
import {BrowserRouter as Router, Route, Switch} from 'react-router-dom'
// import refactored components
import LoginComponent from './LoginComponent.jsx'
import LogoutComponent from './LogoutComponent.jsx'
import ListTodosComponent from './ListTodosComponent.jsx'
import HeaderComponent from './HeaderComponent.jsx'
import FooterComponent from './FooterComponent.jsx'
import ErrorComponent from './ErrorComponent.jsx'
import WelcomeComponent from './WelcomeComponent.jsx'
import TodoComponent from './TodoComponent.jsx'
import SignupComponent from './SignupComponent.jsx'
import OwnerSignupComponent from './OwnerSignupComponent.jsx'
import HomeComponent from './HomeComponent.jsx'
import Faqcomponent from './GeneralFaq'
import NewsFeedComponent from "./NewsFeedComponent";



class TodoApp extends Component {

    // root component of TodoApp that displays header, footer, and the body depending on the route.
    // using react router dom dependency to route pages
    // default route (invalid page to ErrorComponent)
    // switch ensures only one of the path is launched (else pages will be merged)
    render() {
        return (
            <div className="TodoApp">
                <Router>
                    <HeaderComponent />
                    
                    <Switch>
                        <Route path="/" exact component={HomeComponent} />
                        <Route path="/login" component={LoginComponent} />
                        <Route path="/faq" exact component={ Faqcomponent } />
                        <Route path="/news" exact component={NewsFeedComponent} />
                        <AuthenticatedRoute path="/logout" component={LogoutComponent} />
                        <AuthenticatedRoute path="/welcome" component={WelcomeComponent} />
                        <AuthenticatedRoute path="/todos/:id" component={TodoComponent} />
                        <AuthenticatedRoute path="/todos" component={ListTodosComponent} />
                        <Route path="/signup" component={SignupComponent}/>
                        <Route path="/signupbusiness" component={OwnerSignupComponent}/>
                        <Route component={ErrorComponent} />
                    </Switch>
                    {/*<FooterComponent />*/}
                </Router>
            </div>
        )
    }
}

export default TodoApp