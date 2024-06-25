import React, { useEffect, useState } from 'react'
import axios from "axios"
import {Link, useNavigate, useParams } from "react-router-dom"

export default function ReleaseEdit() {

    let navigate = useNavigate()
    const [isAuthenticated, setIsAuthenticated] = useState(true);

    const{id}=useParams()

    const [release,setRelease] = useState({
        name:"",
        description:"",
        createDate:null,
        releaseDate:null,
        project: ""
    })

    const{name,description,createDate,releaseDate,project} = release

    const onInputChange=(e)=>{

        setRelease({...release,[e.target.name]:e.target.value})

    };

    useEffect(()=>{
        const token = localStorage.getItem('jwtToken');
        console.log('Token from local storage:', token);
    
        if (token) {
            axios.defaults.headers.common['Authorization'] = `Bearer ${token}`;
            console.log('Authorization header set:', axios.defaults.headers.common['Authorization']);
        } else {
            setIsAuthenticated(false);
        }
        loadRelease();
    },[]);

    if (!isAuthenticated) {
        navigate("/login")
    }
    
    const onSubmit=async (e)=>{
        e.preventDefault();
        await axios.put(`/release/update/${id}`,release)
        navigate("/release/view/all")
    }

    const loadRelease = async ()=>{
        const response = await axios.get(`/release/${id}`);
        const releaseData = response.data;
        
        const projectResponse = await axios.get(`/project/${releaseData.project.id}`);
        const projectData = projectResponse.data;

        setRelease({ ...releaseData, project: projectData });
    };
    
  return (
    <div className='container'>
        <div className="row">
            <div className='col-md-6 offset-md-3 border rounded p-4 mt-2 shadow'>
                <h2 className='text-center m-4'>Edit Release</h2>
                <form onSubmit={(e)=>onSubmit(e)}>
                <div className='mb-3'>
                    <label htmlFor='Name' className='form-label'>Name</label>
                    <input type={"text"}  className='form-control' placeholder='Enter release name' name="name"  value={name} onChange={(e)=>onInputChange(e)}></input>
                </div>
                <div className='mb-3'>
                    <label htmlFor='Description' className='form-label'>Description</label>
                    <input type={"text"}  className='form-control' placeholder='Enter release description' name="description" value={description} onChange={(e)=>onInputChange(e)}></input>
                </div>
                <div className='mb-3'>
                    <label htmlFor='CreateDate' className='form-label'>Create Date</label>
                    <input type={"date"}  className='form-control' placeholder='Enter create date' name="createDate" value={createDate} onChange={(e)=>onInputChange(e)}></input>
                </div>
                <div className='mb-3'>
                    <label htmlFor='ReleaseDate' className='form-label'>Release Date</label>
                    <input type={"date"}  className='form-control' placeholder='Enter release date' name="releaseDate" value={releaseDate} onChange={(e)=>onInputChange(e)}></input>
                </div>
                <div className='mb-3'>
                    <label htmlFor='Project' className='form-label'>Project</label>
                    <p>{project.name}</p>
                </div>
                <button type='submit' className='btn btn-outline-primary'>Submit</button>
                <Link className='btn btn-outline-danger mx-2' to="/release/view/all">Cancel</Link>
                </form>
            </div>
        </div>
    </div>
  )
}
