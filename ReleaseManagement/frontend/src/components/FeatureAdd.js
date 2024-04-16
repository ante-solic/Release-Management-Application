import React, { useEffect, useState } from 'react'
import axios from "axios"
import {Link, useNavigate, useParams } from "react-router-dom"

export default function FeatureAdd() {

    let navigate = useNavigate()

    const {id} = useParams()

    const [feature, setFeature] = useState({
        name:"",
        description:"",
        release: ""
    })

    useEffect(() => {
        loadRelease();
    }, []);

    const loadRelease = async ()=> {
        const result = await axios.get(`/release/${id}`);
        const releaseData = result.data;
        setFeature({...feature, release:releaseData});
    }

    const{name, description, release} = feature

    const onInputChange=(e)=>{

        setFeature({...feature,[e.target.name]:e.target.value})

    };

    const onSubmit=async (e)=>{
        e.preventDefault();
        await axios.post("/feature/create", feature)
        navigate("/release/view/all")
    }
    
  return (
    <div className='container'>
        <div className="row">
            <div className='col-md-6 offset-md-3 border rounded p-4 mt-2 shadow'>
                <h2 className='text-center m-4'>Add Feature</h2>
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
                    <label htmlFor='Release' className='form-label'>Release</label>
                    <label>{release.name}</label>
                </div>
                <button type='submit' className='btn btn-outline-primary'>Submit</button>
                <Link className='btn btn-outline-danger mx-2' to="/release/view/all">Cancel</Link>
                </form>
            </div>
        </div>
    </div>
  )
}
