import React, { Component, useEffect, useState } from 'react';
import '../App.css';
import axios from "axios";
import {Link, useNavigate, useParams} from "react-router-dom"


export default function Home() {

    const [users,setUsers]=useState([])

    const {id} = useParams() 

    useEffect(()=>{
        const token = localStorage.getItem('jwtToken');
        console.log('Token from local storage:', token);
    
        if (token) {
            axios.defaults.headers.common['Authorization'] = `Bearer ${token}`;
            console.log('Authorization header set:', axios.defaults.headers.common['Authorization']);
        }
        loadUsers();
    },[]);

    const loadUsers=async()=>{
        const result = await axios.get("/user/find/all");
        setUsers(result.data);
    };

    const deleteUser=async (id)=>{
        await axios.delete(`/user/delete/${id}`)
        loadUsers()
    }

    return (
        <div className='container'>
            <div className='py-4'>
                <table className="table border shadow">
                    <thead>
                        <tr>
                        <th scope="col">#</th>
                        <th scope="col">Username</th>
                        <th scope="col">E-mail</th>
                        <th scope="col">First Name</th>
                        <th scope="col">Last Name</th>
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
                                    <Link className='btn btn-primary mx-2' to={`/user/view/${user.id}`}>View</Link>
                                    <Link className='btn btn-outline-primary mx-2' to={`/user/edit/${user.id}`}>Edit</Link>
                                    <button className='btn btn-danger mx-2' onClick={ () => deleteUser(user.id) } >Delete</button>
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