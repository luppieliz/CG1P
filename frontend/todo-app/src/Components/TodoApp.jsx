// import react
import React, { Component } from 'react'
// auth route, make sure route is valid for user
import AuthenticatedRoute from './fixed/AuthenticatedRoute'
// import routing features
import {BrowserRouter as Router, Route, Switch} from 'react-router-dom'
// import refactored components
import LoginComponent from './fixed/LoginComponent.jsx'
import LogoutComponent from './fixed/LogoutComponent.jsx'
import HeaderComponent from './fixed/HeaderComponent.jsx'
import FooterComponent from './fixed/FooterComponent.jsx'
import ErrorComponent from './fixed/ErrorComponent.jsx'
import WelcomeComponent from './fixed/WelcomeComponent.jsx'
import SignupComponent from './fixed/SignupComponent.jsx'

import HomeComponent from './todo/HomeComponent.jsx'
import TodoComponent from './todo/TodoComponent.jsx'
import Faqcomponent from './todo/GeneralFaq'
import NewsFeedComponent from "./todo/NewsFeedComponent";
import ListTodosComponent from "./todo/ListTodosComponent";
import SideBarComponent from "./todo/SideBarComponent";


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
                        <AuthenticatedRoute path="/welcome/:name" component={WelcomeComponent} />
                        <AuthenticatedRoute path="/todos/:id" component={TodoComponent} />
                        <AuthenticatedRoute path="/todos" component={ListTodosComponent} />
                        <AuthenticatedRoute path="/mylist" component={SideBarComponent} />
                       
                        <Route path="/signup" component={SignupComponent}/>
                        <Route component={ErrorComponent} />
                    </Switch>
                    <FooterComponent />
                </Router>
            </div>
        )
    }
}

export default TodoApp;