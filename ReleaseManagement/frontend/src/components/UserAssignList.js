import React, { Component, useEffect, useState } from 'react';
import '../App.css';
import axios from "axios";
import {Link, useNavigate, useParams} from "react-router-dom"
import { jwtDecode } from "jwt-decode";

export default function UserAssignList() {
    let navigate = useNavigate();
    const [isAuthenticated, setIsAuthenticated] = useState(true);

    const [users,setUsers]=useState([])
    const [page, setPage] = useState(0);
    const [size, setSize] = useState(3);
    const [sortBy, setSortBy] = useState('id');
    const [sortDir, setSortDir] = useState('asc');
    const [filter, setFilter] = useState('');
    const [totalPages, setTotalPages] = useState(0);
    const {id} = useParams()
    const [isAdmin, setIsAdmin] = useState(false); 
    const [isProjectManager, setIsProjectManager] = useState(false); 

    useEffect(()=>{
        const token = localStorage.getItem('jwtToken');
        console.log('Token from local storage:', token);
    
        if (token) {
            axios.defaults.headers.common['Authorization'] = `Bearer ${token}`;
            const decodedToken = jwtDecode(token);
            const rolesFromToken = decodedToken.authorities ? decodedToken.authorities.split(',') : [];
            if (rolesFromToken.includes('ROLE_ADMIN')) {
                setIsAdmin(true);
            }
            if (rolesFromToken.includes('ROLE_PROJECT_MANAGER')) {
                setIsProjectManager(true);
            }
        } else {
            setIsAuthenticated(false);
        }

        loadUsers();
    },[page, size, sortBy, sortDir, filter]);

    if (!isAuthenticated) {
        navigate("/login")
    }
    
    const loadUsers=async()=>{
        const result = await axios.get(`/user/find/all/assigned/${id}`, {
            params: {
                page,
                size,
                sortBy,
                sortDir,
                filter
            }
        });
        setUsers(result.data.content || []);
        setTotalPages(result.data.totalPages);
    };

    const unassignUser=async (userId)=>{
        await axios.delete(`/user/unassign/${userId}/${id}`)
        loadUsers()
    }

    const handlePageChange = (newPage) => {
        setPage(newPage);
    };

    return (
        <div className='container'>
            {isAdmin && (
                <Link className='btn btn-primary my-2' to={`/user/assign/${id}`}>Assign New User</Link>
            )}
            {isProjectManager && (
                <Link className='btn btn-primary my-2' to={`/user/assign/${id}`}>Assign New User</Link>
            )}
            <div className='py-4'>
            <div className='d-flex justify-content-between mb-3'>
                    <input
                        type='text'
                        placeholder='Filter by username'
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
                                <option value='username'>Username</option>
                                <option value='email'>Email</option>
                                <option value='firstname'>Firstname</option>
                                <option value='lastname'>Lastname</option>
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
                        <th scope="col">Username</th>
                        <th scope="col">E-mail</th>
                        <th scope="col">First Name</th>
                        <th scope="col">Last Name</th>
                        <th scope="col">Role</th>
                        <th scope="col">Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        {
                            users.map((user,index)=>(
                                <tr>
                                <th scope="row" key={index}>{index+1}</th>
                                <td>{user.username}</td>
                                <td>{user.email}</td>
                                <td>{user.firstname}</td>
                                <td>{user.lastname}</td>
                                <td>
                                    {user.roles ? user.roles.map(role => role.name).join(', ') : 'No roles'}
                                </td>
                                <td>
                                    <Link className='btn btn-primary mx-2' to={`/user/view/${user.id}`}>View</Link>
                                    <Link className='btn btn-outline-primary mx-2' to={`/user/edit/role/${user.id}`}>Edit Role</Link>
                                    {isAdmin && (
                                        <button className='btn btn-danger mx-2' onClick={() => unassignUser(user.id)}>Unassign</button>
                                    )}
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

