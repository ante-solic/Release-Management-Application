import React, { useEffect, useState } from 'react'
import axios from "axios"
import {Link, useNavigate, useParams } from "react-router-dom"

export default function FeatureEdit() {

    let navigate = useNavigate()

    const{id}=useParams()

    const [feature,setFeature] = useState({
        name:"",
        description:"",
        release: ""
    })

    const{name,description,release} = feature

    const onInputChange=(e)=>{

        setFeature({...feature,[e.target.name]:e.target.value})

    };

    useEffect(()=>{
        loadFeature();
    },[]);

    const onSubmit=async (e)=>{
        e.preventDefault();
        await axios.put(`/feature/update/${id}`,feature)
        navigate("/feature/view/all")
    }

    const loadFeature = async ()=>{
        const response = await axios.get(`/feature/${id}`);
        const featureData = response.data;
        
        const releaseResponse = await axios.get(`/release/${featureData.release.id}`);
        const releaseData = releaseResponse.data;

        setFeature({ ...featureData, release: releaseData });
    };
    
  return (
    <div className='container'>
        <div className="row">
            <div className='col-md-6 offset-md-3 border rounded p-4 mt-2 shadow'>
                <h2 className='text-center m-4'>Edit Feature</h2>
                <form onSubmit={(e)=>onSubmit(e)}>
                <div className='mb-3'>
                    <label htmlFor='Name' className='form-label'>Name</label>
                    <input type={"text"}  className='form-control' placeholder='Enter feature name' name="name"  value={name} onChange={(e)=>onInputChange(e)}></input>
                </div>
                <div className='mb-3'>
                    <label htmlFor='Description' className='form-label'>Description</label>
                    <input type={"text"}  className='form-control' placeholder='Enter feature description' name="description" value={description} onChange={(e)=>onInputChange(e)}></input>
                </div>
                <div className='mb-3'>
                    <label htmlFor='Release' className='form-label'>Release</label>
                    <p>{release.name}</p>
                </div>
                <button type='submit' className='btn btn-outline-primary'>Submit</button>
                <Link className='btn btn-outline-danger mx-2' to="/feature/view/all">Cancel</Link>
                </form>
            </div>
        </div>
    </div>
  )
}
