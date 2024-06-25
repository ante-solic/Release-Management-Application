import React, { useState, useEffect } from 'react'
import axios from "axios"
import {Link, useNavigate} from "react-router-dom"
import { jwtDecode } from "jwt-decode";

export default function ProjectAdd() {

    let navigate = useNavigate()
    const [isAuthenticated, setIsAuthenticated] = useState(true);

    const [project,setProject] = useState({
        name:"",
        username:""
    })

    const{name, username} = project
    
    useEffect(() => {
        const token = localStorage.getItem('jwtToken');
        if (token) {
            axios.defaults.headers.common['Authorization'] = `Bearer ${token}`;
            const decodedToken = jwtDecode(token);
            const username = decodedToken.username;
            setProject(prevProject => ({ ...prevProject, username }));
        } else {
            setIsAuthenticated(false);
        }
    }, []);

    if (!isAuthenticated) {
        navigate("/login")
    }

    const onInputChange=(e)=>{

        setProject({...project,[e.target.name]:e.target.value})

    };

    const onSubmit=async (e)=>{
        e.preventDefault();
        await axios.post("/project/create",project)
        navigate("/project/view/all")
    }
    
  return (
    <div className='container'>
        <div className="row">
            <div className='col-md-6 offset-md-3 border rounded p-4 mt-2 shadow'>
                <h2 className='text-center m-4'>Add Project</h2>
                <form onSubmit={(e)=>onSubmit(e)}>
                <div className='mb-3'>
                    <label htmlFor='Name' className='form-label'>Name</label>
                    <input type={"text"}  className='form-control' placeholder='Enter project name' name="name"  value={name} onChange={(e)=>onInputChange(e)}></input>
                </div>
                <button type='submit' className='btn btn-outline-primary'>Submit</button>
                <Link className='btn btn-outline-danger mx-2' to="/project/view/all">Cancel</Link>
                </form>
            </div>
        </div>
    </div>
  )
}
