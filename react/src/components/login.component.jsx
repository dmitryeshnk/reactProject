import React from 'react';
import { useForm } from "react-hook-form";
import AuthService from './../services/auth.service';
import { Redirect } from 'react-router-dom';
import Nav from './nav.component.jsx';

export default function Login(props) {
  const { register, errors, handleSubmit } = useForm();
  var error;
  if(props.logout) {
    AuthService.logout();
    return(<Redirect to="/" />);
  }

  const onSubmit = data => {
    AuthService.login(data.username, data.password)
    .then((response) => {
      if(response.isAxiosError) {
        error = "Bad credentials";
      } else {
        AuthService.loginSuccess(response.data.authorities);
      }
    });
  };

    if(AuthService.authenticated) {
      return(<Redirect to="/" />);
    }
    return (
            <div>
            <Nav isLoggedin={AuthService.authenticated}/>
            {error ? <p className="alert alert-danger">login error</p> : null}
            <form onSubmit={handleSubmit(onSubmit)}>
              <div className="form-group">
                <label>Username</label>
                <input
                  className="form-control"
                  name="username"
                  ref={register({ required: true, minLength: 2 })}
                  defaultValue=""
                />
                {errors.username &&
                  <div className="alert alert-danger">
                      Must not be empty
                  </div>
                }
              </div>
              <div className="form-group">
                <label>Password</label>
                <input
                  className="form-control"
                  name="password"
                  ref={register({ required: true, minLength: 2 })}
                  defaultValue=""
                  type="password"
                />
                {errors.password &&
                  <div className="alert alert-danger">
                      Must not be empty
                  </div>
                }
              </div>
              <div className="form-group">
                <input className="btn btn-default" type="submit" />
              </div>
            </form>
            </div>
      );
}
