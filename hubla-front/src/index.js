import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';
import reportWebVitals from './reportWebVitals';
import Home from "./components/Home";
import { BrowserRouter, Route, Routes } from "react-router-dom";

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
    <BrowserRouter>
        <Routes>
        <Route index element={<App />} />
        <Route path="home" element={<Home />} />
      </Routes>
    </BrowserRouter>
  </React.StrictMode>
);

reportWebVitals();
