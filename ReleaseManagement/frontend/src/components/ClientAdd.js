import React, { useEffect, useState } from 'react'
import axios from "axios"
import {Link, useNavigate, useParams } from "react-router-dom"

export default function ClientAdd() {

    let navigate = useNavigate()

    const [client, setClient] = useState({
        accountId:"",
        name:""
    });

    const{accountId, name} = client

    useEffect(() => {
        const token = localStorage.getItem('jwtToken');
        if (token) {
            axios.defaults.headers.common['Authorization'] = `Bearer ${token}`;
        }
    }, [])

    const onInputChange=(e)=>{

        setClient({...client,[e.target.name]:e.target.value})

    };

    const onSubmit=async (e)=>{
        e.preventDefault();
        console.log("Submitting client:", client);
        await axios.post("/client/", client)
        navigate("/client/view/all")
    }
    
  return (
    <div className='container'>
        <div className="row">
            <div className='col-md-6 offset-md-3 border rounded p-4 mt-2 shadow'>
                <h2 className='text-center m-4'>Add Client</h2>
                <form onSubmit={(e)=>onSubmit(e)}>
                <div className='mb-3'>
                    <label htmlFor='name' className='form-label'>Account Name</label>
                    <input type="text" className='form-control' placeholder='Enter account name' name="name" value={name} onChange={(e) => onInputChange(e)}></input>
                    <label htmlFor='accountId' className='form-label'>Account ID</label>
                    <input type={"text"}  className='form-control' placeholder='Enter account id' name="accountId"  value={accountId} onChange={(e)=>onInputChange(e)}></input>
                </div>
                <button type='submit' className='btn btn-outline-primary'>Submit</button>
                <Link className='btn btn-outline-danger mx-2' to="/client/view/all">Cancel</Link>
                </form>
            </div>
        </div>
    </div>
  )
}
