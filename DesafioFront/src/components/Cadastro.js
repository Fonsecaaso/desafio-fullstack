import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import './styles.css';

function Cadastro() {
  const navigate = useNavigate();
  const [username, setUsername] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [userType, setUserType] = useState("");
  // const [loggedIn, setLoggedIn] = useState(false);

  const handleLogin = () => {
    console.log(userType);
    axios
      .post("http://0.0.0.0:8080/api/auth/register?role="+userType, { username, password, email })
      .then((response) => {
        localStorage.setItem("token", response.data.accessToken);
        localStorage.setItem("username", username);
        navigate("/login"); // redireciona para a página principal
      })
      .catch((error) => {
        console.log(error);
      });
  };

  return (
    <div className="login-container">
        <h2>Cadastro</h2>
        <div className="form-group">
            <label htmlFor="username">Username:</label>
            <input type="text" id="username" value={username} onChange={(e) => setUsername(e.target.value)} />
        </div>
        <div className="form-group">
            <label htmlFor="username">E-mail:</label>
            <input type="text" id="email" value={email} onChange={(e) => setEmail(e.target.value)} />
        </div>
        <div className="form-group">
            <label htmlFor="password">Password:</label>
            <input type="password" id="password" value={password} onChange={(e) => setPassword(e.target.value)} />
        </div>
        <div className="form-group">
            <label>Tipo de usuário:</label>
            <div>
            <label htmlFor="Produtor">
                <input type="radio" id="PRODUTOR" name="userType" value="PRODUTOR" checked={userType === "PRODUTOR"} onChange={(e) => setUserType(e.target.value)} />
                Produtor
            </label>
            <label htmlFor="Afiliado">
                <input type="radio" id="AFILIADO" name="userType" value="AFILIADO" checked={userType === "AFILIADO"} onChange={(e) => setUserType(e.target.value)} />
                Afiliado
            </label>
            </div>
        </div>
        <button onClick={handleLogin}>cadastrar</button>
    </div>
  );
  
}

export default Cadastro;
