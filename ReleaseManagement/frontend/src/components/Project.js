import React, { useEffect, useState } from 'react'
import { Link , useParams, useNavigate } from 'react-router-dom'
import axios from "axios";
import { jwtDecode } from "jwt-decode";

export default function User() {
  let navigate = useNavigate();
  const [isAuthenticated, setIsAuthenticated] = useState(true);

  const [project,setProject]=useState({
    name:""
  })
  const [isAdmin, setIsAdmin] = useState(false); 
  const [isProjectManager, setIsProjectManager] = useState(false); 
  const [isReleaseManager, setIsReleaseManager] = useState(false); 
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
      if (rolesFromToken.includes('ROLE_RELEASE_MANAGER')) {
          setIsReleaseManager(true);
      }
      if (rolesFromToken.includes('ROLE_PROJECT_MANAGER')) {
        setIsProjectManager(true);
    }
    } else {
      setIsAuthenticated(false);
    }
    loadProject()
  },[]);

  if (!isAuthenticated) {
    navigate("/login")
  }

  const loadProject = async ()=>{
    const result = await axios.get(`/project/${id}`)
    setProject(result.data)
  }

  return (
    <div className='container'>
      <div className="row">
          <div className='col-md-6 offset-md-3 border rounded p-4 mt-2 shadow'>
              <h2 className='text-center m-4'>Project Details</h2>
              <div className='card'>
                <div className='card-header'>
                  Details of Project:
                  <ul className='list-group lisst-group-flush'>
                    <li className='list-group-item'>
                      <b>Name: </b>
                        {project.name}
                    </li>
                    <li className='list-group-item'>
                      <b>Users: </b>
                      {isAdmin && (
                        <Link className='btn btn-primary my-2 mx-2' to={`/user/assign/list/${id}`}>Assigned Users</Link>
                      )}
                      {isProjectManager && (
                        <Link className='btn btn-primary my-2 mx-2' to={`/user/assign/list/${id}`}>Assigned Users</Link>
                      )}
                    </li>
                  </ul>
                </div>
              </div>
              {isAdmin && (
                <Link className='btn btn-outline-primary my-2' to={`/release/create/${id}`}>Add Release</Link>
              )}
              {isReleaseManager && (
                <Link className='btn btn-outline-primary my-2' to={`/release/create/${id}`}>Add Release</Link>
              )}
              <Link className='btn btn-primary my-2 mx-2' to={"/project/view/all"}>Back to Project List</Link>
        </div>
      </div>
    </div>
  )
}