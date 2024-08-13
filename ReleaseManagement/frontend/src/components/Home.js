import React, { Component, useEffect, useState } from 'react';
import '../App.css';
import axios from "axios";
import {Link, useNavigate, useParams} from "react-router-dom"


export default function Home() {
    let navigate = useNavigate();
    const [isAuthenticated, setIsAuthenticated] = useState(true);

    useEffect(()=>{
        const token = localStorage.getItem('jwtToken');
        console.log('Token from local storage:', token);
    
        if (token) {
            axios.defaults.headers.common['Authorization'] = `Bearer ${token}`;
            console.log('Authorization header set:', axios.defaults.headers.common['Authorization']);
        } else {
            setIsAuthenticated(false);
        }
    },[]);

    if (!isAuthenticated) {
        navigate("/login")
    }

    return (
        <div className="container">
            <div className="welcome">
                <h1>Welcome to the App!</h1>
                <p>Use the sections below to navigate through the application.</p>
                <div className="card-group">
                    <Link className="table border shadow" to="/project/view/all">
                        <div className="card-content">
                            <h2>Projects</h2>
                            <p>Manage your projects here</p>
                        </div>
                    </Link>
                    <Link className="table border shadow" to="/feature/view/all">
                        <div className="card-content">
                            <h2>Features</h2>
                            <p>View and add features</p>
                        </div>
                    </Link>
                    <Link className="table border shadow" to="/release/view/all">
                        <div className="card-content">
                            <h2>Releases</h2>
                            <p>Track release schedules</p>
                        </div>
                    </Link>
                </div>
            </div>
        </div>
    );
}