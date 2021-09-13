import React from 'react'
import GLOBE from 'vanta/dist/vanta.globe.min'
import Button from 'react-bootstrap/Button'
import Container from 'react-bootstrap/Container'
import Row from 'react-bootstrap/Row'
import { Link } from 'react-router-dom'


 
class HomeComponent extends React.Component {
  constructor() {
    super()
    this.vantaRef = React.createRef()
  }
  componentDidMount() {
    this.vantaEffect = GLOBE({
      el: this.vantaRef.current
    })
  }
  componentWillUnmount() {
    if (this.vantaEffect) this.vantaEffect.destroy()
  }
  render() {
    return <div style={{ height: "92vh", width: "100%"}}  ref={this.vantaRef}>
      <Container>
        <Row style={{ padding:'100px'}}>
            <h1 class="text-white "> Welcome to ______ ,first time ?</h1>
       </Row>
       <Row style={{ padding:'100px'}}>
            <h3 class="text-white "> SOME introduction??? type something cool about our app idk</h3>
       </Row>
       <Row style={{ padding:'100px'}}>
            <h1 class="text-white "> what are you waiting for</h1>
            <Link className="new user text-right" to="/signup"><Button variant="light" >Sign Up</Button></Link>
            <Link className="new user text-right" to="/login"><Button variant="dark">Login In </Button></Link>
       </Row>
      </Container>

    </div>
  }
}
export default HomeComponent;