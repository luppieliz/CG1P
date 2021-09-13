import React, {Component} from 'react';

// Generic logout component
class LogoutComponent extends Component {
    render() {
        return (
            <div>
                <h1>You are logged out</h1>
                <div className="container">
                    Thank you for using the application.
                </div>
            </div>
        );
    }
}

export default LogoutComponent;