import React, {Component} from 'react';
import Col from 'react-bootstrap/Col'
import Card from 'react-bootstrap/Card'
import Row from 'react-bootstrap/Row'
import {BsNewspaper} from "@react-icons/all-files/bs/BsNewspaper";
import {BsPerson} from "@react-icons/all-files/bs/BsPerson";
import {AiOutlineHome} from "@react-icons/all-files/ai/AiOutlineHome";
import {FaQuestion} from "@react-icons/all-files/fa/FaQuestion";


// Footer   

class FooterComponent extends Component {
    render() {
        return [
            'Transparent',
        ].map((variant, idx) => (
            <footer className="footer">
                <Row>
                    <Col>
                        <Card bg={variant.toLowerCase()}>
                            <h4 className="cg1p-header text-white text-center">BUDDY-19</h4>
                            <h5 className=" cg1p-header text-white text-left">Buddy-19 serves as both
                                a one-stop information hub and a companion tool,
                                providing services for business owners to better
                                understand and manage the measures they have to
                                take in the face of the pandemic.</h5>
                        </Card>
                    </Col>
                    <Col>
                        <Card bg={variant.toLowerCase()}>
                            <h4 className="cg1p-header text-white text-center">Site Links</h4>
                            <p className="text-center">
                                <AiOutlineHome/>{" "}Home<br/><BsPerson/>{" "}Employees<br/><BsNewspaper/>{" "}News<br/><FaQuestion/>{" "}Faq
                            </p>

                        </Card>
                    </Col>
                    <Col>
                        <Card bg={variant.toLowerCase()}>
                            <h4 className="cg1p-header text-white text-center">Location</h4>
                            <p className="text-center">81 Victoria St, Singapore 188065</p>
                            <h4 className="cg1p-header text-white text-center">Contact Us</h4>
                            <p className="text-center">Hello@Buddy19.com.sg <br/> +65 61234567</p>
                        </Card>
                    </Col>
                    <Col>
                        <Card bg={variant.toLowerCase()}>

                            <iframe width="400" height="150" scrolling="no"
                                    src="https://maps.google.com/maps?width=100%25&amp;height=600&amp;hl=en&amp;q=81%20Victoria%20St,%20Singapore%20188065+(Your%20Business%20Name)&amp;t=&amp;z=14&amp;ie=UTF8&amp;iwloc=B&amp;output=embed">
                                <a href="https://www.mapsdirections.info/en/measure-map-radius/">www.mapsdirections.info</a>
                            </iframe>
                        </Card>
                    </Col>
                    <span className="text-muted">Buddy-19. All Rights Reserved.</span>
                </Row>


            </footer>
        ))
    }
}

export default FooterComponent;