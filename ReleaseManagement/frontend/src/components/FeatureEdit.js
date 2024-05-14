import React, { useEffect, useState } from 'react'
import axios from "axios"
import {Link, useNavigate, useParams } from "react-router-dom"

export default function FeatureEdit() {

    let navigate = useNavigate()

    const{id}=useParams()

    const [feature,setFeature] = useState({
        name:"",
        description:"",
        release: "",
        status:"",
        enableType: ""
    })

    const{name,description,release,status,enableType} = feature

    const onInputChange=(e)=>{

        setFeature({...feature,[e.target.name]:e.target.value})

    };

    useEffect(()=>{
        const token = localStorage.getItem('jwtToken');
        console.log('Token from local storage:', token);
    
        if (token) {
            axios.defaults.headers.common['Authorization'] = `Bearer ${token}`;
            console.log('Authorization header set:', axios.defaults.headers.common['Authorization']);
        }
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
                <div className='mb-3'>
                    <label htmlFor='Status' className='form-label'>Status</label>
                    <input type={"radio"}  id="statusTrue" className='form-control' placeholder='Enter feature status' name="status" value={true} checked={status} onChange={(e)=>onInputChange(e)}></input>
                    <label for="statusTrue">True</label>
                    <input type={"radio"}  id="statusFalse" className='form-control' placeholder='Enter feature status' name="status" value={false} checked={!status} onChange={(e)=>onInputChange(e)}></input>
                    <label for="statusFalse">False</label>
                </div>
                <div>
                    <label>Enable Type:</label>
                        <input type="radio" id="all" name="enableType" value="ALL" checked={feature.enableType?.value === "ALL"} onChange={(e) => onInputChange(e)} />
                        <label for="all">{feature.enableType?.name || "All"}</label>
                        <input type="radio" id="perAccount" name="enableType" value="PER_ACCOUNT" checked={feature.enableType?.value === "PER_ACCOUNT"} onChange={(e) => onInputChange(e)} />
                    <label for="perAccount">{feature.enableType?.name || "Per Account"}</label>
                </div>
                <button type='submit' className='btn btn-outline-primary'>Submit</button>
                <Link className='btn btn-outline-danger mx-2' to="/feature/view/all">Cancel</Link>
                </form>
            </div>
        </div>
    </div>
  )
}
