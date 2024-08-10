import React, { useEffect, useState } from 'react'
import axios from "axios"
import {Link, useNavigate, useParams } from "react-router-dom"

export default function UserAssign() {

    let navigate = useNavigate()
    const [isAuthenticated, setIsAuthenticated] = useState(true);
    
    const [users, setUsers] = useState([]);

    const {id} = useParams();

    useEffect(() => {
        const token = localStorage.getItem('jwtToken');
        console.log('Token from local storage:', token);

        if (token) {
            axios.defaults.headers.common['Authorization'] = `Bearer ${token}`;
            console.log('Authorization header set:', axios.defaults.headers.common['Authorization']);
        } else {
            setIsAuthenticated(false);
        }

        const fetchUsers = async () => {
            try {
                const response = await axios.get(`/user/find/all/Unassigned/${id}`);
                setUsers(response.data);
            }   catch(error) {
                console.error("Error fetching users: ", error);
            }
        }

        fetchUsers();
    },[]);
    
    if (!isAuthenticated) {
        navigate("/login")
    }

    const [user, setUser] = useState({});

    useEffect(() => {
    if (users.length > 0) {
        setUser(users[0].id);
    }
    }, [users]);

    const onSubmit=async (e)=>{
        e.preventDefault();
        const userId = user;
        const projectId = id;
        await axios.post(`/user/assign/${userId}/${projectId}`)
        navigate(`/user/assign/list/${id}`)
    }
    
  return (
    <div className='container'>
        <div className="row">
            <div className='col-md-6 offset-md-3 border rounded p-4 mt-2 shadow'>
                <h2 className='text-center m-4'>Assign User</h2>
                <form onSubmit={(e)=>onSubmit(e)}>
                <label className="form-label mb-1">User:</label> 
                    <select className="form-select mb-4" value={user.id} onChange={(e) => setUser( e.target.value )}> 
                        {
                            users.map((user) => (
                                <option key={user.id} value={user.id}>
                                    {user.username || user}
                                </option>
                            ))
                        }
                    </select>
                <button type='submit' className='btn btn-outline-primary'>Submit</button>
                <Link className='btn btn-outline-danger mx-2' to={`/user/assign/list/${id}`}>Cancel</Link>
                </form>
            </div>
        </div>
    </div>
  )
}
