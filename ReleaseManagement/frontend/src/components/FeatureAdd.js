import React, { useEffect, useState } from 'react'
import axios from "axios"
import {Link, useNavigate, useParams } from "react-router-dom"

export default function FeatureAdd() {

    let navigate = useNavigate()

    const {id} = useParams()

    const [feature, setFeature] = useState({
        name:"",
        description:"",
        release: "",
        status:"",
        enableType:""
    })

    useEffect(() => {
        const token = localStorage.getItem('jwtToken');
        console.log('Token from local storage:', token);
    
        if (token) {
            axios.defaults.headers.common['Authorization'] = `Bearer ${token}`;
            console.log('Authorization header set:', axios.defaults.headers.common['Authorization']);
        }
        loadRelease();
    }, []);

    const loadRelease = async ()=> {
        const result = await axios.get(`/release/${id}`);
        const releaseData = result.data;
        setFeature({...feature, release:releaseData});
    }

    const{name, description, release, status, enableType} = feature

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
                <div className='mb-3'>
                    <label htmlFor='status' className='form-label'>Status</label>
                    <select id='status' className='form-control' name='status' value={status} onChange={(e) => onInputChange(e)}>
                        <option value={true}>True</option>
                        <option value={false}>False</option>
                    </select>
                </div>
                <div>
                    <label htmlFor='enableType'>Enable Type:</label>
                    <select id='enableType' className='form-control' value={feature.enableType?.value} onChange={(e) => onInputChange(e)} name='enableType'>
                        <option value='ALL'>All</option>
                        <option value='PER_ACCOUNT'>Per Account</option>
                    </select>
                </div>
                <button type='submit' className='btn btn-outline-primary'>Submit</button>
                <Link className='btn btn-outline-danger mx-2' to="/release/view/all">Cancel</Link>
                </form>
            </div>
        </div>
    </div>
  )
}
