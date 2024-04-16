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
import ProjectList from './components/ProjectList';
import ProjectEdit from './components/ProjectEdit';
import Project from './components/Project';
import ProjectAdd from './components/ProjectAdd';
import ReleaseAdd from './components/ReleaseAdd';
import ReleaseList from './components/ReleaseList';
import ReleaseEdit from './components/ReleaseEdit';
import Release from './components/Release';
import FeatureAdd from './components/FeatureAdd';
import FeatureList from './components/FeatureList';
import FeatureEdit from './components/FeatureEdit';
import Feature from './components/Feature';

function App() {
  return <div className='App'>
    <Router>
      <Navbar></Navbar>
      <Routes>
        <Route exact path="/" element={<Home></Home>}/>
        <Route exact path="/user/add" element={<UserAdd></UserAdd>}/>
        <Route exact path="/user/edit/:id" element={<UserEdit></UserEdit>}/>
        <Route exact path="/user/view/:id" element={<User></User>}/>

        <Route exact path="/project/view/all" element={<ProjectList></ProjectList>}/>
        <Route exact path="/project/edit/:id" element={<ProjectEdit></ProjectEdit>}/>
        <Route exact path="/project/create" element={<ProjectAdd></ProjectAdd>}/>
        <Route exact path="/project/view/:id" element={<Project></Project>}/>

        <Route exact path="/release/create/:id" element={<ReleaseAdd></ReleaseAdd>}/>
        <Route exact path="/release/view/all" element={<ReleaseList></ReleaseList>}/>
        <Route exact path="/release/edit/:id" element={<ReleaseEdit></ReleaseEdit>}/>
        <Route exact path="/release/view/:id" element={<Release></Release>}/>

        <Route exact path="/feature/create/:id" element={<FeatureAdd></FeatureAdd>}/>
        <Route exact path="/feature/view/all" element={<FeatureList></FeatureList>}/>
        <Route exact path="/feature/edit/:id" element={<FeatureEdit></FeatureEdit>}/>
        <Route exact path="/feature/view/:id" element={<Feature></Feature>}/>
      </Routes>
    </Router>
  </div>;
}

export default App;
