import React, { useEffect, useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { jwtDecode } from "jwt-decode";
import axios from 'axios';

export default function Navbar() {
    const [isLoggedIn, setIsLoggedIn] = useState(false);
    const [roles, setRoles] = useState([]);
    const [userName, setUserName] = useState('');
    const [userId, setUserId] = useState('');
    const navigate = useNavigate();

    useEffect(() => {
        const token = localStorage.getItem('jwtToken');
        const storedUserId = localStorage.getItem('userId');
        if (token) {
            setIsLoggedIn(true);
            try {
                const decodedToken = jwtDecode(token);
                const rolesFromToken = decodedToken.authorities ? decodedToken.authorities.split(',') : [];
                setRoles(rolesFromToken);
                setUserName(decodedToken.username); 

                setUserId(storedUserId || ''); 
                if (!storedUserId) {
                    fetchUserId(decodedToken.username);
                }
            } catch (error) {
                console.error('Error decoding JWT:', error);
                setIsLoggedIn(false);
                setRoles([]);
            }
        } else {
            setIsLoggedIn(false);
            setRoles([]);
        }
    }, [localStorage.getItem('jwtToken')]);

    const fetchUserId = async (userName) => {
        const result = await axios.get(`/user/username/${userName}`)
        setUserId(result.data.id)
        localStorage.setItem('userId', result.data.id);
    };

    const handleLogout = () => {
        localStorage.removeItem('jwtToken');
        localStorage.removeItem('userId');
        setIsLoggedIn(false);
        setRoles([]);
        navigate('/login');
    };

    const isAdmin = roles.includes('ROLE_ADMIN');

    return (
        <nav className="navbar navbar-expand-lg navbar-dark bg-primary">
            <div className="container-fluid">
                <a className="navbar-brand">Release Management</a>
                <button className="navbar-toggler" type="button" data-bs-toggle="collapse"
                    data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent"
                    aria-expanded="false" aria-label="Toggle navigation">
                    <span className="navbar-toggler-icon"></span>
                </button>
                <div className="collapse navbar-collapse" id="navbarSupportedContent">
                    <ul className="navbar-nav me-auto mb-2 mb-lg-0">
                        {isLoggedIn && (
                            <>
                                <li className="nav-item">
                                    <Link className="nav-link" to="/home">Home</Link>
                                </li>
                                <li className="nav-item">
                                    <Link className="nav-link" to="/project/view/all">Projects</Link>
                                </li>
                                <li className="nav-item">
                                    <Link className="nav-link" to="/release/view/all">Releases</Link>
                                </li>
                                <li className="nav-item">
                                    <Link className="nav-link" to="/feature/view/all">Features</Link>
                                </li>
                                {isAdmin && (
                                    <>
                                    <li className="nav-item">
                                        <Link className="nav-link" to="/user/view/all">Users</Link>
                                    </li>
                                    <li className="nav-item">
                                        <Link className="nav-link" to="/client/view/all">Clients</Link>
                                    </li>
                                    </>
                                )}
                            </>
                        )}
                    </ul>
                    <ul className="navbar-nav ml-auto mb-2 mb-lg-0">
                        {isLoggedIn ? (
                            <>
                            <li className="nav-item">
                                <Link className='btn btn-outline-light mx-2' to={`/user/edit/${userId}`}>Hi {userName}</Link>
                            </li>
                            <li className="nav-item">
                                <button className='btn btn-outline-light mx-2' onClick={handleLogout}>Logout</button>
                            </li>
                            </>
                        ) : (
                            <li className="nav-item">
                                <Link className='btn btn-outline-light' to="/login">Login</Link>
                            </li>
                        )}
                    </ul>
                </div>
            </div>
        </nav>
    );
}
