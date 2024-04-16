import React, { useState } from 'react'
import axios from "axios"
import {Link, useNavigate} from "react-router-dom"

export default function ProjectAdd() {

    let navigate = useNavigate()

    const [project,setProject] = useState({
        name:""
    })

    const{name} = project

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
