import React, { useEffect, useState } from 'react'
import axios from "axios"
import {Link, useNavigate, useParams } from "react-router-dom"

export default function UserEdit() {

    let navigate = useNavigate()

    const{id}=useParams()

    const [user,setUser] = useState({
        username:"",
        email:"",
        password:"",
        firstname:"",
        lastname:""
    })

    const{username,email,password,firstname,lastname} = user

    const onInputChange=(e)=>{

        setUser({...user,[e.target.name]:e.target.value})

    };

    useEffect(()=>{
        loadUser();
    },[]);

    const onSubmit=async (e)=>{
        e.preventDefault();
        await axios.put(`/user/update/${id}`,user)
        navigate("/")
    }

    const loadUser = async ()=>{
        const result = await axios.get(`/user/${id}`)
        setUser(result.data)
    };
    
  return (
    <div className='container'>
        <div className="row">
            <div className='col-md-6 offset-md-3 border rounded p-4 mt-2 shadow'>
                <h2 className='text-center m-4'>Edit User</h2>
                <form onSubmit={(e)=>onSubmit(e)}>
                <div className='mb-3'>
                    <label htmlFor='Username' className='form-label'>Username</label>
                    <input type={"text"}  className='form-control' placeholder='Enter username' name="username"  value={username} onChange={(e)=>onInputChange(e)}></input>
                </div>
                <div className='mb-3'>
                    <label htmlFor='Email' className='form-label'>Email</label>
                    <input type={"email"}  className='form-control' placeholder='Enter email' name="email" value={email} onChange={(e)=>onInputChange(e)}></input>
                </div>
                <div className='mb-3'>
                    <label htmlFor='Password' className='form-label'>Password</label>
                    <input type={"password"}  className='form-control' placeholder='Enter password' name="password" value={password} onChange={(e)=>onInputChange(e)}></input>
                </div>
                <div className='mb-3'>
                    <label htmlFor='Firstname' className='form-label'>Firstname</label>
                    <input type={"text"}  className='form-control' placeholder='Enter firstname' name="firstname" value={firstname} onChange={(e)=>onInputChange(e)}></input>
                </div>
                <div className='mb-3'>
                    <label htmlFor='Lastname' className='form-label'>Lastname</label>
                    <input type={"text"}  className='form-control' placeholder='Enter lastname' name="lastname" value={lastname} onChange={(e)=>onInputChange(e)}></input>
                </div>
                <button type='submit' className='btn btn-outline-primary'>Submit</button>
                <Link className='btn btn-outline-danger mx-2' to="/">Cancel</Link>
                </form>
            </div>
        </div>
    </div>
  )
}
