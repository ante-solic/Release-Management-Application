import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useParams, useNavigate } from 'react-router-dom';

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
                // Send the selectedRole directly as a UUID string
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
            <div>
                {roles.map(role => (
                    <div key={role.id}>
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
            <button onClick={handleSave}>Save</button>
        </div>
    );
}
