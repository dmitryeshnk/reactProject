import React from 'react';
import AuthService from './../services/auth.service'
import { Redirect } from 'react-router-dom'
import Nav from './nav.component.jsx';

export default class Home extends React.Component{
    render(){
      if(AuthService.authenticated) {
        return (
        <div>
        <Nav isLoggedin={AuthService.authenticated}/>
        <h2>Главная</h2>;
        </div>);
      }
      return (<Redirect to="/login" />)
    }
}
