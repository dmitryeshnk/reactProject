import ReactDOM from 'react-dom';
import React from 'react';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import { Redirect, useHistory } from 'react-router-dom'
import AuthService from './services/auth.service.js';

import Users from './components/users.component.jsx';
import Home from './components/home.component.jsx';
import Login from './components/login.component.jsx';
import Register from './components/register.component.jsx';

class App extends React.Component {
  constructor(props) {
    super(props);
  }

  render() {
    return(
      <Router>
          <div>
              <Switch>
                  <Route path="/users" component={Users} />
                  <Route path="/login" component={Login} />
                  <Route path="/register" component={Register} />
                  <Route path="/logout">
                    <Login logout={true}/>
                  </Route>
                  <Route component={Home} />
              </Switch>
          </div>
      </Router>
    );
  }
}

ReactDOM.render( <App/>,
    document.getElementById("app")
)
