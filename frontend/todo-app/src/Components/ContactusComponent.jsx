
import Card from 'react-bootstrap/Card';
import React, {Component} from 'react';




class ContactusComponent extends Component {
    render() {
        return (
            <>
            <Card className="text-dark">
                    <Card.Body>
                        <Card.Text>
                            <h1>Contact Us</h1>
                        </Card.Text>
                    </Card.Body>
                </Card>
            </>
        )
    }
}

export default ContactusComponent;