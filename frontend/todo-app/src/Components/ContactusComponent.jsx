
import Card from 'react-bootstrap/Card';
import React, { Component } from 'react';
import Image from 'react-bootstrap/Image';
import Container from 'react-bootstrap/Container';
import Placeholder from 'react-bootstrap/Placeholder';




class ContactusComponent extends Component {
    render() {
        return (
            <>
            <div style={{
                backgroundImage: "url(https://img.freepik.com/free-vector/blue-pink-halftone-background_53876-99004.jpg?size=626&ext=jpg"
                , backgroundPosition: 'center'
                , backgroundSize: 'cover'
                , backgroundRepeat: 'no-repeat'
                , width: '100%'
                , height: '48rem'
            }}>
                <Container>
                    <Placeholder xs={12} bg="transparent"/>
                    <h1  className="text-dark" >Contact Us</h1>
                    <Placeholder xs={12} bg="transparent"  />
                    <Placeholder xs={12} bg="transparent" />
                    <Image src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRxh-RcLjFJr8fs2qySGguzk0A4qYZv327tf47wAIJz9NLBkyZbezCaWBFvLpul_AoV0A&usqp=CAU" fluid />
                </Container>
                </div>
            </>
        )
    }
}

export default ContactusComponent;