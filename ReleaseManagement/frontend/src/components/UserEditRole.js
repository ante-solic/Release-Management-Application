import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { Link, useParams, useNavigate } from 'react-router-dom';

export default function UserEditRole() {
    const { id } = useParams();
    const navigate = useNavigate();
    const [user, setUser] = useState(null);
    const [roles, setRoles] = useState([]);
    const [selectedRole, setSelectedRole] = useState(null);

    useEffect(() => {
        const loadUser = async () => {
            try {
                const result = await axios.get(`/user/${id}`);
                setUser(result.data);
                setSelectedRole(result.data.roles[0]?.id || null); // Assuming user has exactly one role
            } catch (error) {
                console.error('Error loading user:', error.response ? error.response.data : error.message);
            } 
        };

        const loadRoles = async () => {
            const result = await axios.get('/roles'); 
            setRoles(result.data);
        };

        loadUser();
        loadRoles();
    }, [id]);

    const handleRoleChange = (event) => {
        setSelectedRole(event.target.value);
    };

    const handleSave = async () => {
        if (selectedRole) {
            try {
                await axios.put(`/user/update/role/${id}`, selectedRole, {
                    headers: {
                        'Content-Type': 'application/json'
                    }
                });
                navigate(`/user/view/${id}`);
            } catch (error) {
                console.error('Error updating user role:', error.response ? error.response.data : error.message);
                alert('Error updating user role. Please check the console for more details.');
            }
        }
    };
    
    

    if (!user) {
        return <div>Loading...</div>;
    }

    return (
        <div className='container'>
            <h2>Edit Role for {user.username}</h2>
            <div className='col-md-6 offset-md-3 border rounded p-4 mt-2 shadow'>
                {roles.map(role => (
                    <div className='col-md-6 offset-md-3 border rounded p-4 mt-2 shadow' key={role.id}>
                        <input
                            type="radio"
                            id={role.id}
                            name="role"
                            value={role.id}
                            checked={selectedRole === role.id}
                            onChange={handleRoleChange}
                        />
                        <label htmlFor={role.id}>{role.name}</label>
                    </div>
                ))}
            </div>
            <button className='btn btn-primary mx-2' onClick={handleSave}>Save</button>
            <Link className='btn btn-danger my-2 mx-2' to={"/project/view/all"}>Back</Link>
        </div>
    );
}
