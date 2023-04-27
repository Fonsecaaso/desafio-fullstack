import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';
import reportWebVitals from './reportWebVitals';
import Home from "./components/Home";
import Transactions from "./components/Transactions";
import UserList from "./components/UserList";
import { BrowserRouter, Route, Routes } from "react-router-dom";

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
    <BrowserRouter>
        <Routes>
        <Route index element={<App />} />
        <Route path="home" element={<Home />} />
        <Route path="transactions" element={<Transactions />} />
        <Route path="userlist" element={<UserList />} />
      </Routes>
    </BrowserRouter>
  </React.StrictMode>
);

reportWebVitals();
