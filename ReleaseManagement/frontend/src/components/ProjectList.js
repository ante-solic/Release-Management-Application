import React, { Component, useEffect, useState } from 'react';
import '../App.css';
import axios from "axios";
import {Link, useNavigate, useParams} from "react-router-dom"


export default function ProjectList() {

    const [projects,setProjects]=useState([])

    const {id} = useParams() 

    useEffect(()=>{
        loadProjects();
    },[]);

    const loadProjects=async()=>{
        const result = await axios.get("/project/find/all");
        setProjects(result.data);
    };

    const deleteProject=async (id)=>{
        await axios.delete(`/project/delete/${id}`)
        loadProjects()
    }

    return (
        <div className='container'>
            <Link className='btn btn-primary my-2' to="/project/create">Add Project</Link>
            <div className='py-4'>
                <table className="table border shadow">
                    <thead>
                        <tr>
                        <th scope="col">#</th>
                        <th scope="col">Name</th>
                        <th scope="col">Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        {
                            projects.map((project,index)=>(
                                <tr>
                                <th scope="row" key={index}>{index+1}</th>
                                <td>{project.name}</td>
                                <td>
                                    <Link className='btn btn-primary mx-2' to={`/project/view/${project.id}`}>View</Link>
                                    <Link className='btn btn-outline-primary mx-2' to={`/project/edit/${project.id}`}>Edit</Link>
                                    <button className='btn btn-danger mx-2' onClick={ () => deleteProject(project.id) } >Delete</button>
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