import React from 'react';
import axios from "axios";
import Edit from './edit.component.jsx';
import Add from './add.component.jsx';
import AuthService from './../services/auth.service'
import { Redirect } from 'react-router-dom'
import Nav from './nav.component.jsx';

export default class Users extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      users: [],
      edit: false,
      add: false
    };
    this.change = this.change.bind(this);
  }

  componentDidMount() {
    this.loadUsers();
    console.log(this.state.users);
  }

  loadUsers() {
    axios.get("http://localhost:8080/users", {
      headers: JSON.parse(localStorage.getItem("headers"))
    })
        .then(
          (response) =>
            this.setState({
              isLoaded: true,
              users: response.data
            })
        );

  }

  change(user){
    if(user.roles.length == 1) {
      user.roles.push({
        id: 1,
        name: "admin"
      });
    } else {
      user.roles = [];
      user.roles.push({
        id: 2,
        name: "user"
      });
    }
    axios.put("http://localhost:8080/users/"+user.id, user,{
      headers: JSON.parse(localStorage.getItem("headers"))
    });
    setTimeout(() => {  this.loadUsers() }, 500);
 }

 edit(user) {
   this.setState({
     edit: !this.state.edit,
     add: false,
     editUser: user
   })
 }

 add() {
   this.setState({
     edit: false,
     add: !this.state.add
   })
 }

 delete(user){
   axios.delete("http://localhost:8080/users/"+user.id,{
     headers: JSON.parse(localStorage.getItem("headers"))
   });
   setTimeout(() => {  this.loadUsers() }, 500);
 }

 render() {
   const { error, isLoaded, users } = this.state;
       if (error) {
         return <div>Error: {error.message}</div>;
       } else if (!isLoaded) {
         return <div>Loading...</div>;
       } else if(AuthService.isAdmin){
      return (
        <div>
        <Nav isLoggedin={AuthService.authenticated}/>
        <table className="table">
            <thead key="tbody">
              <tr>
                <th scope="col">#</th>
                <th scope="col">Username</th>
                <th scope="col">Email</th>
                <th scope="col">Password</th>
                <th scope="col">Birthday</th>
                <th scope="col">Gender</th>
                <th scope="col">Roles</th>
                <th scope="col">Assign role</th>
                <th scope="col">Edit</th>
                <th scope="col">Delete</th>
              </tr>
            </thead>
            <tbody>
              {users.map(user => (
                <tr key={user.id}>
                  <td>{user.id}</td>
                  <td>{user.username}</td>
                  <td>{user.email}</td>
                  <td>{user.password}</td>
                  <td>
                  {new Intl.DateTimeFormat("en-GB", {
                    year: "numeric",
                    month: "long",
                    day: "2-digit"
                  }).format(+user.birthday)}
                  </td>
                  <td>{user.gender.name}</td>
                  <td>
                    {user.roles.map(role => (
                      <div key={role.id}>{role.name}</div>
                    ))}
                  </td>
                  <td><button onClick={()=>this.change(user)} className="btn btn-primary">change</button></td>
                  <td><button onClick={()=>this.edit(user)} className="btn btn-primary">edit</button></td>
                  <td><button onClick={()=>this.delete(user)} className="btn btn-primary">Delete</button></td>
                </tr>
              ))}
            </tbody>
        </table>
        <button onClick={() => this.add()} className="btn btn-primary">Add</button>
        { this.state.edit ? <Edit user={this.state.editUser} props={this} /> : null }
        { this.state.add ? <Add props={this} /> : null }
        </div>
      );
    } else {
      return <Redirect to="/"/>;
    }
  }
}
