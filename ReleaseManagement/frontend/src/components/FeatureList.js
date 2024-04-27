import React, { Component, useEffect, useState } from 'react';
import '../App.css';
import axios from "axios";
import {Link, useNavigate, useParams} from "react-router-dom"


export default function FeatureList() {

    const [features,setFeatures]=useState([])

    const {id} = useParams() 

    useEffect(()=>{
        const token = localStorage.getItem('jwtToken');
        console.log('Token from local storage:', token);
    
        if (token) {
            axios.defaults.headers.common['Authorization'] = `Bearer ${token}`;
            console.log('Authorization header set:', axios.defaults.headers.common['Authorization']);
        }
        loadFeatures();
    },[]);

    const loadFeatures=async()=>{
        const result = await axios.get("/feature/find/all");
        setFeatures(result.data);
    };

    const deleteFeature=async (id)=>{
        await axios.delete(`/feature/delete/${id}`)
        loadFeatures()
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
                        <th scope="col">Action</th>
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
                                    <Link className='btn btn-primary mx-2' to={`/feature/view/${feature.id}`}>View</Link>
                                    <Link className='btn btn-outline-primary mx-2' to={`/feature/edit/${feature.id}`}>Edit</Link>
                                    <button className='btn btn-danger mx-2' onClick={ () => deleteFeature(feature.id) } >Delete</button>
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