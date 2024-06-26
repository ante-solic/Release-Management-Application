import React, { useEffect, useState } from 'react'
import { Link , useParams, useNavigate } from 'react-router-dom'
import axios from "axios";

export default function User() {
  let navigate = useNavigate();
  const [isAuthenticated, setIsAuthenticated] = useState(true);

  const [user,setUser]=useState({
    username:"",
    email:"",
    firstname:"",
    lastname:""
  })

  const {id} = useParams()

  useEffect(() => {
    const token = localStorage.getItem('jwtToken');
    console.log('Token from local storage:', token);

    if (token) {
        axios.defaults.headers.common['Authorization'] = `Bearer ${token}`;
        console.log('Authorization header set:', axios.defaults.headers.common['Authorization']);
    } else {
      setIsAuthenticated(false);
    }
    loadUser()
  },[]);

  if (!isAuthenticated) {
    navigate("/login")
  }

  const loadUser = async ()=>{
    const result = await axios.get(`/user/${id}`)
    setUser(result.data)
  }

  return (
    <div className='container'>
      <div className="row">
          <div className='col-md-6 offset-md-3 border rounded p-4 mt-2 shadow'>
              <h2 className='text-center m-4'>User Details</h2>
              <div className='card'>
                <div className='card-header'>
                  Details of user:
                  <ul className='list-group lisst-group-flush'>
                    <li className='list-group-item'>
                      <b>Username: </b>
                        {user.username}
                    </li>
                    <li className='list-group-item'>
                      <b>E-mail: </b>
                        {user.email}
                    </li>
                    <li className='list-group-item'>
                      <b>Firstname: </b>
                        {user.firstname}
                    </li>
                    <li className='list-group-item'>
                      <b>Lastname: </b>
                        {user.lastname}
                    </li>
                  </ul>
                </div>
              </div>
              <Link className='btn btn-primary my-2' to={"/user/view/all"}>Back to User List</Link>
        </div>
      </div>
    </div>
  )
}