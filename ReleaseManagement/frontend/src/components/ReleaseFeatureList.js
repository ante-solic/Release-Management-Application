import React, { Component, useEffect, useState } from 'react';
import '../App.css';
import axios from "axios";
import {Link, useNavigate, useParams} from "react-router-dom"
import { jwtDecode } from "jwt-decode";


export default function ReleaseFeatureList() {
    let navigate = useNavigate();
    const [isAuthenticated, setIsAuthenticated] = useState(true);

    const [features,setFeatures]=useState([])
    const [page, setPage] = useState(0);
    const [size, setSize] = useState(3);
    const [sortBy, setSortBy] = useState('id');
    const [sortDir, setSortDir] = useState('asc');
    const [filter, setFilter] = useState('');
    const [totalPages, setTotalPages] = useState(0);
    const {id} = useParams()
    const [isAdmin, setIsAdmin] = useState(false); 
    const [isProjectManager, setIsProjectManager] = useState(false); 
    const [isReleaseManager, setIsReleaseManager] = useState(false);
    const [isDeveloper, setIsDeveloper] = useState(false);
    const [userId, setUserId] = useState('');

    useEffect(() => {
        const token = localStorage.getItem('jwtToken');
        const storedUserId = localStorage.getItem('userId');
        console.log('Token from local storage:', token);

        if (token) {
            axios.defaults.headers.common['Authorization'] = `Bearer ${token}`;
            const decodedToken = jwtDecode(token);
            const rolesFromToken = decodedToken.authorities ? decodedToken.authorities.split(',') : [];

            setIsAdmin(rolesFromToken.includes('ROLE_ADMIN'));
            setIsProjectManager(rolesFromToken.includes('ROLE_PROJECT_MANAGER'));
            setIsReleaseManager(rolesFromToken.includes('ROLE_RELEASE_MANAGER'));
            setIsDeveloper(rolesFromToken.includes('ROLE_DEVELOPER'));


            setUserId(storedUserId || ''); 
        } else {
            setIsAuthenticated(false);
        }
    }, []);

    useEffect(() => {
        if (!isAuthenticated) {
            navigate('/login');
            return;
        }
        loadReleaseFeatures();
    }, [page, size, sortBy, sortDir, filter, isAdmin, isReleaseManager, isProjectManager, isDeveloper, isAuthenticated]);

    const loadReleaseFeatures = async () => {
        try {
            const params = { page, size, sortBy, sortDir, filter };
            const result = await axios.get(`/feature/find/assigned/release/${id}`, { params });
            setFeatures(result.data.content || []);
            setTotalPages(result.data.totalPages);
        } catch (error) {
            console.error('Error loading features:', error);
        }
    };

    const handlePageChange = (newPage) => {
        setPage(newPage);
    };

    return (
        <div className='container'>
            <div className='py-4'>
                <div className='d-flex justify-content-between mb-3'>
                    <input
                        type='text'
                        placeholder='Filter by name'
                        value={filter}
                        onChange={(e) => setFilter(e.target.value)}
                    />
                    <div>
                        <label>
                            Size:
                            <select value={size} onChange={(e) => setSize(e.target.value)}>
                                <option value='1'>1</option>
                                <option value='3'>3</option>
                                <option value='5'>5</option>
                                <option value='10'>10</option>
                            </select>
                        </label>
                        <label>
                            Sort By:
                            <select value={sortBy} onChange={(e) => setSortBy(e.target.value)}>
                                <option value='id'>ID</option>
                                <option value='name'>Name</option>
                                <option value='status'>Status</option>
                            </select>
                        </label>
                        <label>
                            Sort Direction:
                            <select value={sortDir} onChange={(e) => setSortDir(e.target.value)}>
                                <option value='asc'>Ascending</option>
                                <option value='desc'>Descending</option>
                            </select>
                        </label>
                    </div>
                </div>
                <table className="table border shadow">
                    <thead>
                        <tr>
                        <th scope="col">#</th>
                        <th scope="col">Name</th>
                        <th scope="col">Description</th>
                        <th scope="col">Status</th>
                        </tr>
                    </thead>
                    <tbody>
                        {
                            features.map((feature,index)=>(
                                <tr>
                                <th scope="row" key={index}>{index+1}</th>
                                <td>{feature.name}</td>
                                <td>{feature.description}</td>
                                <td>
                                    <span style={{ color: feature.status ? 'green' : 'red' }}>
                                        {feature.status ? 'On' : 'Off'}
                                    </span>
                                </td>
                                </tr>
                            ))
                        }
                    </tbody>
                </table>
                <div className='d-flex justify-content-between'>
                    <button
                        className='btn btn-secondary'
                        onClick={() => handlePageChange(page - 1)}
                        disabled={page === 0}>
                        Previous
                    </button>
                    <button
                        className='btn btn-secondary'
                        onClick={() => handlePageChange(page + 1)}
                        disabled={page + 1 >= totalPages}>
                        Next
                    </button>
                </div>
            </div>
        </div>
    )
}