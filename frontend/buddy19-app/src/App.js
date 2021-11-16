import './App.css';
import './Bootstrap.css'
// Default export from React (React) is directly usable, but Component is not default so it has to be in curly brace.
import React, { Component } from 'react';

import Buddy19App from './Components/Buddy19App'

// Root Component of entire React App. Calls Buddy19App.
class App extends Component {
  render() {
    return (
      <div className="App">
        {/*<Counter/>*/}
        <Buddy19App/>
      </div>
    );
  }
}



export default App;
