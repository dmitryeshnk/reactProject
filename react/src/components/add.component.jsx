import React from "react";
import { useForm } from "react-hook-form";
import axios from "axios";

import moment from 'moment';


export default function Add({user, props}) {
  const { register, errors, handleSubmit } = useForm();

  const onSubmit = data => {
    data.gender = {
      id: data.genderId
    }
    axios.post("http://localhost:8080/users", data, {
      headers: JSON.parse(localStorage.getItem("headers"))
    });
    setTimeout(() => {  props.loadUsers() }, 500);
    props.setState({
      add: false
    })
  }

  return (
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
        <label>Email</label>
        <input
          className="form-control"
          name="email"
          ref={register({ required: true, pattern: /^([\w.%+-]+)@([\w-]+\.)+([\w]{2,})$/i  })}
          defaultValue=""
        />
        {errors.email &&
          <div className="alert alert-danger">
              Invalid email
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
        />
        {errors.password &&
          <div className="alert alert-danger">
              Must not be empty
          </div>
        }
      </div>
      <div className="form-group">
        <label>Birthday</label>
        <input
          className="form-control"
          type="date"
          name="birthday"
          max={moment().format("YYYY-MM-DD")}
          ref={register({ required: true })}
          defaultValue=""
        />
        {errors.birthday &&
          <div className="alert alert-danger">
              Must not be empty
          </div>
        }
      </div>
      <div className="form-group">
        <label>Gender</label>
        <select className="form-control" name="genderId" ref={register({required: true})} defaultValue={1}>
          <option value={1}>male</option>
          <option value={2}>female</option>
        </select>
      </div>
      <div className="form-group">
        <input className="btn btn-default" type="submit" />
      </div>
    </form>
  );
}
