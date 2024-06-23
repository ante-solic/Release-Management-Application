import React, { useEffect, useState } from 'react'
import { Link , useParams} from 'react-router-dom'
import axios from "axios";
import { jwtDecode } from "jwt-decode";

export default function Feature() {

  const [feature,setFeature]=useState({
    mame:"",
    description:"",
    release:"",
    status:"",
    enableType:""
  })

  const [clients, setClients] = useState([]);
  const [isAdmin, setIsAdmin] = useState(false); 
  const [isProjectManager, setIsProjectManager] = useState(false); 
  const {id} = useParams()

  useEffect(() => {
    const token = localStorage.getItem('jwtToken');
    console.log('Token from local storage:', token);

    if (token) {
      axios.defaults.headers.common['Authorization'] = `Bearer ${token}`;
      const decodedToken = jwtDecode(token);
      const rolesFromToken = decodedToken.authorities ? decodedToken.authorities.split(',') : [];
      if (rolesFromToken.includes('ROLE_ADMIN')) {
          setIsAdmin(true);
      }
      if (rolesFromToken.includes('ROLE_PROJECT_MANAGER')) {
          setIsProjectManager(true);
      }
    }
    loadFeature();
    loadClients();
  },[]);

  const loadFeature = async ()=>{
    const result = await axios.get(`/feature/${id}`)
    setFeature(result.data)
  }

  const loadClients =  async ()=>{
    const result = await axios.get(`/feature/clients/${id}`)
    setClients(result.data)
  }

  const handleUnassign = async (clientId) => {
    try {
      await axios.delete(`/client/unassign/${clientId}/${id}`);
      loadFeature();
      loadClients();
    } catch(error) {
      console.error("Error unassigning client:", error);
    }
  }

  return (
    <div className='container'>
      <div className="row">
          <div className='col-md-6 offset-md-3 border rounded p-4 mt-2 shadow'>
              <h2 className='text-center m-4'>Feature Details</h2>
              <div className='card'>
                <div className='card-header'>
                  Details of Feature:
                  <ul className='list-group lisst-group-flush'>
                    <li className='list-group-item'>
                      <b>Name: </b>
                        {feature.name}
                    </li>
                    <li className='list-group-item'>
                      <b>Description: </b>
                        {feature.description}
                    </li>
                    <li className='list-group-item'>
                      <b>Release: </b>
                        {feature.release.name}
                    </li>
                    <li className='list-group-item'>
                      <b>Status: </b>
                        <span style={{ color: feature.status ? 'green' : 'red' }}>
                            {feature.status ? 'On' : 'Off'}
                        </span>
                    </li>
                    <li className='list-group-item'>
                      <b>Enable Type: </b>
                        {feature.enableType}
                    </li>
                    <li className='list-group-item'>
                      <b>Clients: </b>
                        {clients.map((client, index) => (
                          <div key={index} style={{ marginBottom: '0.5rem' }}>
                            <span>{client.name}</span>
                            {isAdmin && (
                            <button className="btn btn-sm btn-danger ms-2" onClick={() => handleUnassign(client.id)}>
                              Unassign
                            </button>
                            )}
                            {isProjectManager && (
                            <button className="btn btn-sm btn-danger ms-2" onClick={() => handleUnassign(client.id)}>
                              Unassign
                            </button>
                            )}
                          </div>
                        ))}
                    </li>
                  </ul>
                </div>
              </div>
              {isAdmin && (
                <Link className='btn btn-primary my-2 mx-2' to={`/client/assign/${id}`}>Assign Client</Link>
              )}
              {isProjectManager && (
                <Link className='btn btn-primary my-2 mx-2' to={`/client/assign/${id}`}>Assign Client</Link>
              )}
              <Link className='btn btn-primary my-2 mx-2' to={"/feature/view/all"}>Back to Feature List</Link>
        </div>
      </div>
    </div>
  )
}