import { Component, useEffect, useState } from 'react';
import './App.css';
import Home from './components/Home';
import UserList from './components/UserList';
import {BrowserRouter as Router, Route, Routes, Navigate} from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';
import UserEdit from './components/UserEdit';
import Navbar from './components/AppNavbar';
import UserAdd from './components/UserAdd';
import User from './components/User';

function App() {
  return <div className='App'>
    <Router>
      <Navbar></Navbar>
      <Routes>
        <Route exact path="/" element={<Home></Home>}/>
        <Route exact path="/user/add" element={<UserAdd></UserAdd>}/>
        <Route exact path="/user/edit/:id" element={<UserEdit></UserEdit>}/>
        <Route exact path="/user/view/:id" element={<User></User>}/>
      </Routes>
    </Router>
  </div>;
}

export default App;
