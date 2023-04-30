import './App.css';
import Home from "./components/Home";
import { Route, Routes } from "react-router-dom";
import Transactions from "./components/Transactions";
import UserList from "./components/UserList";
import Login from "./components/Login";
import LandingPage from "./components/LandingPage";
import Register from './components/Register';

function App() {
  return (
    <div>
      <Routes>
        <Route path="/" element={<LandingPage />} />
        <Route exact path="/login" element={<Login/>} />
        <Route exact path="/register" element={<Register/>} />
        <Route path="/home" element={<Home/>} />
        <Route path="/transactions" element={<Transactions/>} />
        <Route path="/userlist" element={<UserList/>} />
      </Routes>
    </div>
  );
}

export default App;
