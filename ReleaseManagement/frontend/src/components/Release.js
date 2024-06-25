import React, { useEffect, useState } from 'react'
import { Link , useParams, useNavigate} from 'react-router-dom'
import axios from "axios";
import { jwtDecode } from "jwt-decode";

export default function Release() {
  let navigate = useNavigate();
  const [isAuthenticated, setIsAuthenticated] = useState(true);

  const [release,setRelease]=useState({
    mame:"",
    description:"",
    createDate:null,
    releaseDate:null,
    project:""
  })
  const [isAdmin, setIsAdmin] = useState(false); 
  const [isDeveloper, setIsDeveloper] = useState(false);  
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
      if (rolesFromToken.includes('ROLE_DEVELOPER')) {
          setIsDeveloper(true);
      }
    } else {
      setIsAuthenticated(false);
    }
    loadRelease()
  },[]);

  if (!isAuthenticated) {
    navigate("/login")
  }
  
  const loadRelease = async ()=>{
    const result = await axios.get(`/release/${id}`)
    setRelease(result.data)
  }

  return (
    <div className='container'>
      <div className="row">
          <div className='col-md-6 offset-md-3 border rounded p-4 mt-2 shadow'>
              <h2 className='text-center m-4'>Release Details</h2>
              <div className='card'>
                <div className='card-header'>
                  Details of Release:
                  <ul className='list-group lisst-group-flush'>
                    <li className='list-group-item'>
                      <b>Name: </b>
                        {release.name}
                    </li>
                    <li className='list-group-item'>
                      <b>Description: </b>
                        {release.description}
                    </li>
                    <li className='list-group-item'>
                      <b>Create Date: </b>
                        {release.createDate}
                    </li>
                    <li className='list-group-item'>
                      <b>Release Date: </b>
                        {release.releaseDate}
                    </li>
                    <li className='list-group-item'>
                      <b>Project: </b>
                        {release.project.name}
                    </li>
                  </ul>
                </div>
              </div>
              {isAdmin && (
              <Link className='btn btn-outline-primary my-2' to={`/feature/create/${id}`}>Add Feature</Link>
              )}
              {isDeveloper && (
              <Link className='btn btn-outline-primary my-2' to={`/feature/create/${id}`}>Add Feature</Link>
              )}
              <Link className='btn btn-primary my-2 mx-2' to={"/release/view/all"}>Back to Release List</Link>
        </div>
      </div>
    </div>
  )
}