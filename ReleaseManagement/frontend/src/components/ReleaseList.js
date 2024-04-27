import React, { Component, useEffect, useState } from 'react';
import '../App.css';
import axios from "axios";
import {Link, useNavigate, useParams} from "react-router-dom"


export default function ReleaseList() {

    const [releases,setReleases]=useState([])

    const {id} = useParams() 

    useEffect(()=>{
        const token = localStorage.getItem('jwtToken');
        console.log('Token from local storage:', token);
    
        if (token) {
            axios.defaults.headers.common['Authorization'] = `Bearer ${token}`;
            console.log('Authorization header set:', axios.defaults.headers.common['Authorization']);
        }
        loadReleases();
    },[]);

    const loadReleases=async()=>{
        const result = await axios.get("/release/find/all");
        setReleases(result.data);
    };

    const deleteRelease=async (id)=>{
        await axios.delete(`/release/delete/${id}`)
        loadReleases()
    }

    return (
        <div className='container'>
            <div className='py-4'>
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
                                    <Link className='btn btn-outline-primary mx-2' to={`/release/edit/${release.id}`}>Edit</Link>
                                    <button className='btn btn-danger mx-2' onClick={ () => deleteRelease(release.id) } >Delete</button>
                                </td>
                                </tr>
                            ))
                        }
                    </tbody>
                </table>
            </div>
        </div>
    )
}