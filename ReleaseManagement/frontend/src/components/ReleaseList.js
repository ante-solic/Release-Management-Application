import React, { Component, useEffect, useState } from 'react';
import '../App.css';
import axios from "axios";
import {Link, useNavigate, useParams} from "react-router-dom"
import { jwtDecode } from "jwt-decode";


export default function ReleaseList() {
    let navigate = useNavigate();
    const [isAuthenticated, setIsAuthenticated] = useState(true);

    const [releases,setReleases]=useState([])
    const [page, setPage] = useState(0);
    const [size, setSize] = useState(3);
    const [sortBy, setSortBy] = useState('id');
    const [sortDir, setSortDir] = useState('asc');
    const [filter, setFilter] = useState('');
    const [totalPages, setTotalPages] = useState(0);
    const {id} = useParams()
    const [isAdmin, setIsAdmin] = useState(false); 
    const [isReleaseManager, setIsReleaseManager] = useState(false);  

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
            if (rolesFromToken.includes('ROLE_RELEASE_MANAGER')) {
                setIsReleaseManager(true);
            }
        } else {
            setIsAuthenticated(false);
        }
        loadReleases();
    },[page, size, sortBy, sortDir, filter]);

    if (!isAuthenticated) {
        navigate("/login")
    }

    const loadReleases=async()=>{
        const result = await axios.get("/release/find/all", {
            params: {
                page,
                size,
                sortBy,
                sortDir,
                filter
            }
        });
        setReleases(result.data.content || []);
        setTotalPages(result.data.totalPages);
    };

    const deleteRelease=async (id)=>{
        await axios.delete(`/release/delete/${id}`)
        loadReleases()
    }

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
                                <option value='createDate'>CreateDate</option>
                                <option value='releaseDate'>ReleaseDate</option>
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
                        <th scope="col">Create Date</th>
                        <th scope="col">Release Date</th>
                        <th scope="col">Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        {
                            releases.map((release,index)=>(
                                <tr>
                                <th scope="row" key={index}>{index+1}</th>
                                <td>{release.name}</td>
                                <td>{release.description}</td>
                                <td>{release.createDate}</td>
                                <td>{release.releaseDate}</td>
                                <td>
                                    <Link className='btn btn-primary mx-2' to={`/release/view/${release.id}`}>View</Link>
                                    {isAdmin && (
                                    <>
                                    <Link className='btn btn-outline-primary mx-2' to={`/release/edit/${release.id}`}>Edit</Link>
                                    <button className='btn btn-danger mx-2' onClick={ () => deleteRelease(release.id) } >Delete</button>
                                    </>
                                    )}
                                    {isReleaseManager && (
                                    <>
                                    <Link className='btn btn-outline-primary mx-2' to={`/release/edit/${release.id}`}>Edit</Link>
                                    <button className='btn btn-danger mx-2' onClick={ () => deleteRelease(release.id) } >Delete</button>
                                    </>
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