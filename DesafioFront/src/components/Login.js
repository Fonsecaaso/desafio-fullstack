import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import './styles.css';

function Login() {
  const navigate = useNavigate();
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  // const [loggedIn, setLoggedIn] = useState(false);

  const handleLogin = () => {
    axios
      .post("http://localhost:8085/api/auth/login", { username, password })
      .then((response) => {
        localStorage.setItem("token", response.data.accessToken);
        localStorage.setItem("username", username);
        navigate("/home"); // redireciona para a página principal
      })
      .catch((error) => {
        console.log(error);
      });
  };

  return (
    <div className="login-container">
        <h2>Login</h2>
        <div className="form-group">
            <label htmlFor="username">Username:</label>
            <input type="text" id="username" value={username} onChange={(e) => setUsername(e.target.value)} />
        </div>
        <div className="form-group">
            <label htmlFor="password">Password:</label>
            <input type="password" id="password" value={password} onChange={(e) => setPassword(e.target.value)} />
        </div>
        <button className="btn" onClick={handleLogin}>Login</button>
    </div>
  );
  
}

export default Login;
