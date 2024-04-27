import React, {Component, useEffect, useState} from 'react';
import {Link, useNavigate} from 'react-router-dom';

export default function Navbar(){
    const [isLoggedIn, setIsLoggedIn] = useState(false);
    const history = useNavigate();

    useEffect(() => {
        const token = localStorage.getItem('jwtToken');
        setIsLoggedIn(!!token);
    }, []);

    const handleLogout = () =>{
        localStorage.removeItem(`jwtToken`);
        history(`/login`);
    }

    return (
        <nav className="navbar navbar-expand-lg navbar-dark bg-primary">
        <div className="container-fluid">
            <a className="navbar-brand" href="#">Release Management</a>
            <button className="navbar-toggler" type="button" data-bs-toggle="collapse" 
            data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" 
            aria-expanded="false" aria-label="Toggle navigation">
            <span className="navbar-toggler-icon"></span>
            </button>
            {isLoggedIn ? (
                <button className='btn btn-outline-light mx-2' onClick={handleLogout} >Logout</button>
            ) : (
                <Link className='btn btn-outline-light' to="/login">Login</Link>
            )}
        </div>
        </nav>
    )
}