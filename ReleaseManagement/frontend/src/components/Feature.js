import React, { useEffect, useState } from 'react'
import { Link , useParams} from 'react-router-dom'
import axios from "axios";

export default function Feature() {

  const [feature,setFeature]=useState({
    mame:"",
    description:"",
    release:""
  })

  const {id} = useParams()

  useEffect(() => {
    loadFeature()
  },[]);

  const loadFeature = async ()=>{
    const result = await axios.get(`/feature/${id}`)
    setFeature(result.data)
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
                  </ul>
                </div>
              </div>
              <Link className='btn btn-primary my-2 mx-2' to={"/feature/view/all"}>Back to Feature List</Link>
        </div>
      </div>
    </div>
  )
}