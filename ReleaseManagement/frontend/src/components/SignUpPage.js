import React, { useEffect, useState } from 'react'; 
import axios from 'axios'; 
import { useNavigate } from 'react-router-dom'; // Import useHistory hook 
import { 
    MDBContainer, 
    MDBInput, 
    MDBBtn, 
} from 'mdb-react-ui-kit'; 

function SignupPage(){

    const [roles, setRoles] = useState([]);

    useEffect(() => {
        const fetchRoles = async () => {
            try {
                const response = await axios.get('/roles');
                setRoles(response.data);
            }   catch(error) {
                console.error("Error fetching roles: ", error);
            }
        }

        fetchRoles();
    },[]);

    const [username, setUsername] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [confirmPassword, setConfirmPassword] = useState('');
    const [role, setRole] = useState('ROLE_PROJECT_MANAGER');
    const [firstname, setFirstname] = useState('');
    const [lastname, setLastname] = useState('');
    const [error, setError] = useState('');
    const history = useNavigate();

    const handleSignup = async () => {
        try{
            if(!username || !email || !password || !confirmPassword || !firstname || !lastname){
                setError('Please fill in all the fields.');
                return;
            }

            if(!role){
                setError('Please pick a role.')
            }

            if(password !== confirmPassword){
                throw new Error("Passwords do not match");
            }

            const response = await axios.post('/auth/signup', {
                username,
                email,
                password,
                role,
                firstname,
                lastname
            });

            console.log(response.data);
            history('/login');
        } catch (error){
            console.error('Signup failed:', error.response ? error.response.data : error.message);
            setError(error.response ? error.response.data : error.message);
        }
    };

    return(
        <div className="d-flex justify-content-center align-items-center vh-100"> 
            <div className="border rounded-lg p-4" style={{width: '600px', height: 'auto'}}> 
                <MDBContainer className="p-3"> 
                    <h2 className="mb-4 text-center">Sign Up Page</h2> 
                    {/* Render error message if exists */} 
                    {error && <p className="text-danger">{error}</p>} 
                    <MDBInput wrapperClass='mb-3' id='username' placeholder={"Username"} value={username} type='text'
                              onChange={(e) => setUsername(e.target.value)}/> 
                    <MDBInput wrapperClass='mb-3' placeholder='Email Address' id='email' value={email} type='email'
                              onChange={(e) => setEmail(e.target.value)}/> 
                    <MDBInput wrapperClass='mb-3' placeholder='Password' id='password' type='password' value={password} 
                              onChange={(e) => setPassword(e.target.value)}/> 
                    <MDBInput wrapperClass='mb-3' placeholder='Confirm Password' id='confirmPassword' type='password'
                              value={confirmPassword} 
                              onChange={(e) => setConfirmPassword(e.target.value)}/> 
  
  
                    <MDBInput wrapperClass='mb-2' placeholder='Firstname' id='firstname' value={firstname} 
                              type='text'
                              onChange={(e) => setFirstname(e.target.value)}/> 
                    <MDBInput wrapperClass='mb-2' placeholder='Lastname' id='lastname' value={lastname} 
                              type='text'
                              onChange={(e) => setLastname(e.target.value)}/> 

                    <label className="form-label mb-1">Role:</label> 
                    <select className="form-select mb-4" value={role} onChange={(e) => setRole(e.target.value)}> 
                        {
                            roles.map((role) => (
                                <option key={role.name} value={role.name}>
                                    {role.name || role}
                                </option>
                            ))
                        }
                    </select> 
                    <button className="mb-4 d-block mx-auto fixed-action-btn btn-primary"
                            style={{height: '40px', width: '100%'}} 
                            onClick={handleSignup}>Sign Up 
                    </button> 
  
                    <div className="text-center"> 
                        <p>Already Registered? <a href="/login">Login</a></p> 
                    </div> 
  
                </MDBContainer> 
            </div> 
        </div> 
    );

}

export default SignupPage; 
