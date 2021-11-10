
import Card from 'react-bootstrap/Card';
import React, { Component } from 'react';
import Image from 'react-bootstrap/Image';
import Container from 'react-bootstrap/Container';
import Placeholder from 'react-bootstrap/Placeholder';
import Form from 'react-bootstrap/Form'
import Button from 'react-bootstrap/Button'
import {FiSend } from "@react-icons/all-files/fi/FiSend";



class ContactusComponent extends Component {
    render() {
        return (
            <>
            <div>
                <Container>
                    <Placeholder xs={12} bg="transparent"/>
                    <h1  className="text-dark cg1p-header" >Contact Us</h1>
                    <Placeholder xs={12} bg="transparent"  />
                    <Placeholder xs={12} bg="transparent" />
                    <Image src="https://media.istockphoto.com/photos/young-bearded-businessman-sitting-on-desk-and-posing-picture-id1322913815?k=20&m=1322913815&s=612x612&w=0&h=vDJxUO2lrV1YtG2VoM9IGcZnPJNJ4cvGzAOhaY76A2Y=" roundedCircle />
                    <Form className="text-dark">
                    <Placeholder xs={12} bg="transparent" />
                    <Placeholder xs={12} bg="transparent" />
                        <Form.Group className="mb-3" controlId="exampleForm.ControlInput1">
                            <Form.Label>Email address</Form.Label>
                            <Form.Control type="email" placeholder="name@example.com" />
                        </Form.Group>
                        <Form.Group className="mb-3" controlId="exampleForm.ControlTextarea1">
                            <Form.Label>Description</Form.Label>
                            <Form.Control as="textarea" rows={3} />
                        </Form.Group>
                        <div className="d-grid gap-2">
                        <Button variant="primary" type="submit" size="lg"><FiSend /></Button>
                        </div>
                        </Form>
                </Container>
                <Placeholder xs={12} bg="transparent" style={{ paddingBottom: "50vh" }} />
                </div>
            </>
        )
    }
}

export default ContactusComponent;