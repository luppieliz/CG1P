import React from 'react'
import GLOBE from 'vanta/dist/vanta.globe.min'
import Button from 'react-bootstrap/Button'
import Container from 'react-bootstrap/Container'
import Row from 'react-bootstrap/Row'
import Col from 'react-bootstrap/Col'


import { Link } from 'react-router-dom'


 
class HomeComponent extends React.Component {
  constructor() {
    super()
    this.vantaRef = React.createRef()
  }
  componentDidMount() {
    this.vantaEffect = GLOBE({
      el: this.vantaRef.current,
      mouseControls: true,
      touchControls: true,
      gyroControls: false,
      minHeight: 200.0,
      minWidth: 200.0,
      scale: 1.0,
      scaleMobile: 1.0,
      color: 0xFF0000,
      color2: 0xFFFFFF,
      backgroundColor: 0x000000
    })
  }
  componentWillUnmount() {
    if (this.vantaEffect) this.vantaEffect.destroy()
  }
  render() {
    return <div style={{ height: "92vh", width: "100%"}}  ref={this.vantaRef}>
      <Container>
        <Row style={{ padding:'100px'}}><h1 className="text-white "> Welcome to </h1><h1 className="text-danger "> COVby</h1></Row>
       <Row style={{ padding:'50'}}>
       <Col><h3 class="text-white "> COVby serves as both a
            one-stop information hub and a companion tool, providing services for business owners to better understand and manage
            the measures they have to take in the face of the pandemic.
            </h3>
        </Col>
        <Col></Col>
       </Row>
       <Row style={{paddingTop:'100px'}}>
        <Col>
            <Link className="new user text-left" to="/signup"><Button variant="light" >Sign Up</Button></Link>
            <Link style={{ padding:'10px'}}className="new user text-left" to="/login"><Button variant="dark">Login In </Button></Link>
          </Col>
          <Col></Col>
         
         
       </Row>
      </Container>

    </div>
  }
}
export default HomeComponent;