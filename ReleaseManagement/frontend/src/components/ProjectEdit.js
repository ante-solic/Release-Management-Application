import React, { useEffect, useState } from 'react'
import axios from "axios"
import {Link, useNavigate, useParams } from "react-router-dom"

export default function ProjectEdit() {

    let navigate = useNavigate()

    const{id}=useParams()

    const [project,setProject] = useState({
        name:""
    })

    const{name} = project

    const onInputChange=(e)=>{

        setProject({...project,[e.target.name]:e.target.value})

    };

    useEffect(()=>{
        const token = localStorage.getItem('jwtToken');
        console.log('Token from local storage:', token);
    
        if (token) {
            axios.defaults.headers.common['Authorization'] = `Bearer ${token}`;
            console.log('Authorization header set:', axios.defaults.headers.common['Authorization']);
        }
        loadProject();
    },[]);

    const onSubmit=async (e)=>{
        e.preventDefault();
        await axios.put(`/project/update/${id}`,project)
        navigate("/project/view/all")
    }

    const loadProject = async ()=>{
        const result = await axios.get(`/project/${id}`)
        setProject(result.data)
    };
    
  return (
    <div className='container'>
        <div className="row">
            <div className='col-md-6 offset-md-3 border rounded p-4 mt-2 shadow'>
                <h2 className='text-center m-4'>Edit User</h2>
                <form onSubmit={(e)=>onSubmit(e)}>
                <div className='mb-3'>
                    <label htmlFor='Name' className='form-label'>Name</label>
                    <input type={"text"}  className='form-control' placeholder='Enter name' name="name"  value={name} onChange={(e)=>onInputChange(e)}></input>
                </div>
                <button type='submit' className='btn btn-outline-primary'>Submit</button>
                <Link className='btn btn-outline-danger mx-2' to="/project/view/all">Cancel</Link>
                </form>
            </div>
        </div>
    </div>
  )
}
