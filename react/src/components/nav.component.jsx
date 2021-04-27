import React from 'react';
import {Link, BrowserRouter}  from 'react-router-dom';
import AuthService from './../services/auth.service'

export default function Nav({isLoggedin}) {
  return <div>
          {isLoggedin && (<Link to='/logout'>Logout</Link>)}
          {isLoggedin && (<Link to='/users'>Users</Link>)}

          {!isLoggedin && (<Link to='/login'>Login</Link>)}
          {!isLoggedin && (<Link to='/register'>Register</Link>)}
        </div>;
  }
