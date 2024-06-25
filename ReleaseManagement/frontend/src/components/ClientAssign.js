import React, { useEffect, useState } from 'react'
import axios from "axios"
import {Link, useNavigate, useParams } from "react-router-dom"

export default function ClientAssign() {

    let navigate = useNavigate()
    const [isAuthenticated, setIsAuthenticated] = useState(true);
    
    const [clients, setClients] = useState([]);

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

        const fetchClients = async () => {
            try {
                const response = await axios.get('/client/find/all');
                setClients(response.data);
            }   catch(error) {
                console.error("Error fetching clients: ", error);
            }
        }

        fetchClients();
    },[]);
    
    if (!isAuthenticated) {
        navigate("/login")
    }

    const [client, setClient] = useState({});

    useEffect(() => {
    if (clients.length > 0) {
        setClient(clients[0].id);
    }
    }, [clients]);

    const onSubmit=async (e)=>{
        e.preventDefault();
        const clientId = client;
        const featureId = id;
        await axios.post(`/client/assign/${clientId}/${featureId}`)
        navigate("/feature/view/all")
    }
    
  return (
    <div className='container'>
        <div className="row">
            <div className='col-md-6 offset-md-3 border rounded p-4 mt-2 shadow'>
                <h2 className='text-center m-4'>Assign Client</h2>
                <form onSubmit={(e)=>onSubmit(e)}>
                <label className="form-label mb-1">Client:</label> 
                    <select className="form-select mb-4" value={client.id} onChange={(e) => setClient( e.target.value )}> 
                        {
                            clients.map((client) => (
                                <option key={client.accountId} value={client.id}>
                                    {client.name || client}
                                </option>
                            ))
                        }
                    </select>
                <button type='submit' className='btn btn-outline-primary'>Submit</button>
                <Link className='btn btn-outline-danger mx-2' to="/feature/view/all">Cancel</Link>
                </form>
            </div>
        </div>
    </div>
  )
}
