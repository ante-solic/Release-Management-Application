import React, { useEffect, useState } from 'react'
import axios from "axios"
import {Link, useNavigate, useParams } from "react-router-dom"

export default function ReleaseAdd() {

    let navigate = useNavigate()
    const [isAuthenticated, setIsAuthenticated] = useState(true);

    const {id} = useParams()

    const [release,setRelease] = useState({
        name:"",
        description:"",
        createDate:null,
        releaseDate:null,
        project: ""
    })

    useEffect(() => {
        const token = localStorage.getItem('jwtToken');
        console.log('Token from local storage:', token);
    
        if (token) {
            axios.defaults.headers.common['Authorization'] = `Bearer ${token}`;
            console.log('Authorization header set:', axios.defaults.headers.common['Authorization']);
        } else {
            setIsAuthenticated(false);
        }
        loadProject();
    }, []);
    
    if (!isAuthenticated) {
        navigate("/login")
    }

    const loadProject = async ()=> {
        const result = await axios.get(`/project/${id}`);
        const projectData = result.data;
        setRelease({...release, project:projectData});
    }

    const{name, description, createDate, releaseDate, project} = release

    const onInputChange=(e)=>{

        setRelease({...release,[e.target.name]:e.target.value})

    };

    const onSubmit=async (e)=>{
        e.preventDefault();
        await axios.post("/release/create",release)
        navigate("/project/view/all")
    }
    
  return (
    <div className='container'>
        <div className="row">
            <div className='col-md-6 offset-md-3 border rounded p-4 mt-2 shadow'>
                <h2 className='text-center m-4'>Add Release</h2>
                <form onSubmit={(e)=>onSubmit(e)}>
                <div className='mb-3'>
                    <label htmlFor='Name' className='form-label'>Name</label>
                    <input type={"text"}  className='form-control' placeholder='Enter release name' name="name"  value={name} onChange={(e)=>onInputChange(e)}></input>
                </div>
                <div className='mb-3'>
                    <label htmlFor='Description' className='form-label'>Description</label>
                    <input type={"text"}  className='form-control' placeholder='Enter release description' name="description"  value={description} onChange={(e)=>onInputChange(e)}></input>
                </div>
                <div className='mb-3'>
                    <label htmlFor='CreateDate' className='form-label'>CreateDate</label>
                    <input type={"date"}  className='form-control' placeholder='Enter create date' name="createDate"  value={createDate} onChange={(e)=>onInputChange(e)}></input>
                </div>
                <div className='mb-3'>
                    <label htmlFor='ReleaseDate' className='form-label'>ReleaseDate</label>
                    <input type={"date"}  className='form-control' placeholder='Enter release date' name="releaseDate"  value={releaseDate} onChange={(e)=>onInputChange(e)}></input>
                </div>
                <div className='mb-3'>
                    <label htmlFor='Project' className='form-label'>Project</label>
                    <label>{project.name}</label>
                </div>
                <button type='submit' className='btn btn-outline-primary'>Submit</button>
                <Link className='btn btn-outline-danger mx-2' to="/project/view/all">Cancel</Link>
                </form>
            </div>
        </div>
    </div>
  )
}
