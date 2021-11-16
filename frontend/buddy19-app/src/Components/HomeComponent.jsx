import React from 'react'
import GLOBE from 'vanta/dist/vanta.net.min'
import Button from 'react-bootstrap/Button'
import Container from 'react-bootstrap/Container'
import Row from 'react-bootstrap/Row'
import Col from 'react-bootstrap/Col'
import Placeholder from 'react-bootstrap/Placeholder'


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
      color: 0x777777,
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
      <Placeholder xs={12} bg="transparent" />
      <Placeholder xs={12} bg="transparent" />
      <Placeholder xs={12} bg="transparent" />
      <Placeholder xs={12} bg="transparent" />
      <Placeholder xs={12} bg="transparent" />
  
      
        <Row style={{ padding:'100px'}}><h1 className="text-white"> Welcome to </h1><h0 className="text-warning "> Buddy-19</h0></Row>

       <Row style={{paddingTop:'10px'}}>
        <Col>
            <Link className="new user text-left" to="/signup"><Button variant="light" >Sign Up</Button></Link>
            <Link style={{ padding:'10px'}}className="new user text-left" to="/login"><Button variant="dark">Login</Button></Link>
          </Col>

         
         
       </Row>
      </Container>

    </div>
  }
}
export default HomeComponent;