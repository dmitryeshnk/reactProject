import React from 'react';
import axios from "axios";

export default class AuthService {

  static authenticated;

  static isAdmin;

  static login(username, password) {
    let headers = { authorization: this.createToken(username, password)};
    localStorage.setItem("headers", JSON.stringify(headers));
    return axios.get('http://localhost:8080/user', {
      headers: JSON.parse(localStorage.getItem("headers"))
    });
  }

  static loginSuccess(authorities) {
      AuthService.authenticated = true;
      AuthService.isAdmin = authorities.find(role => role.name = 'admin');
  }

  static createToken(username, password) {
    return 'Basic ' + window.btoa(username+':'+password);
  }

  static logout() {
    AuthService.authenticated = false;
    AuthService.isAdmin = false;
  }
}
