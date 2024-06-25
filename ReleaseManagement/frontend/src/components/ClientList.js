import React, { Component, useEffect, useState } from 'react';
import '../App.css';
import axios from "axios";
import {Link, useNavigate, useParams} from "react-router-dom"


export default function ClientList() {
    let navigate = useNavigate();
    const [isAuthenticated, setIsAuthenticated] = useState(true);
    const [clients,setClients]=useState([])

    const {id} = useParams() 

    useEffect(()=>{
        const token = localStorage.getItem('jwtToken');
        console.log('Token from local storage:', token);
    
        if (token) {
            axios.defaults.headers.common['Authorization'] = `Bearer ${token}`;
            console.log('Authorization header set:', axios.defaults.headers.common['Authorization']);
        } else {
            setIsAuthenticated(false);
        }
        loadClients();
    },[]);

    if (!isAuthenticated) {
        navigate("/login")
    }

    const loadClients=async()=>{
        const result = await axios.get("/client/find/all");
        setClients(result.data);
    };

    const deleteClient=async (id)=>{
        await axios.delete(`/client/${id}`)
        loadClients()
    }

    return (
        <div className='container'>
            <Link className='btn btn-primary my-2' to="/client/create">Add Client</Link>
            <div className='py-4'>
                <table className="table border shadow">
                    <thead>
                        <tr>
                        <th scope="col">#</th>
                        <th scope="col">Account Name</th>
                        <th scope="col">Account ID</th>
                        <th scope="col">Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        {
                            clients.map((client,index)=>(
                                <tr>
                                <th scope="row" key={index}>{index+1}</th>
                                <td>{client.name}</td>
                                <td>{client.accountId}</td>
                                <td>
                                    <button className='btn btn-danger mx-2' onClick={ () => deleteClient(client.id) } >Delete</button>
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