import './App.css';
import './Bootstrap.css'
// Default export from React (React) is directly usable, but Component is not default so it has to be in curly brace.
import React, { Component } from 'react';

import TodoApp from './Components/TodoApp'

// Root Component of entire React App. Calls TodoApp.
class App extends Component {
  render() {
    return (
      <div className="App">
        {/*<Counter/>*/}
        <TodoApp/>
      </div>
    );
  }
}



export default App;
