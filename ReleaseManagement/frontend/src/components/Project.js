import React, { useEffect, useState } from 'react'
import { Link , useParams} from 'react-router-dom'
import axios from "axios";

export default function User() {

  const [project,setProject]=useState({
    mame:""
  })

  const {id} = useParams()

  useEffect(() => {
    const token = localStorage.getItem('jwtToken');
    console.log('Token from local storage:', token);

    if (token) {
        axios.defaults.headers.common['Authorization'] = `Bearer ${token}`;
        console.log('Authorization header set:', axios.defaults.headers.common['Authorization']);
    }
    loadProject()
  },[]);

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
                  </ul>
                </div>
              </div>
              <Link className='btn btn-outline-primary my-2' to={`/release/create/${id}`}>Add Release</Link>
              <Link className='btn btn-primary my-2 mx-2' to={"/project/view/all"}>Back to Project List</Link>
        </div>
      </div>
    </div>
  )
}