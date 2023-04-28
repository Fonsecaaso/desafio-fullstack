import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';
import reportWebVitals from './reportWebVitals';
import Home from "./components/Home";
import Transactions from "./components/Transactions";
import UserList from "./components/UserList";
import Login from "./components/Login";
import LandingPage from "./components/LandingPage";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import Cadastro from './components/Cadastro';

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
    <BrowserRouter>
        <Routes>
        <Route index element={<App />} />
        <Route path="login" element={<Login />} />
        <Route path="home" element={<Home />} />
        <Route path="transactions" element={<Transactions />} />
        <Route path="userlist" element={<UserList />} />
        <Route path="landing" element={<LandingPage />} />
        <Route path="cadastro" element={<Cadastro />} />
      </Routes>
    </BrowserRouter>
  </React.StrictMode>
);

reportWebVitals();
