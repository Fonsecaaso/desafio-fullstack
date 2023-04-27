import './App.css';
import Home from "./components/Home";
import { Route, Routes } from "react-router-dom";
import Transactions from "./components/Transactions";
import UserList from "./components/UserList";
import Login from "./components/Login";

function App() {
  return (
    <div>
      <Routes>
        <Route exact path="/" element={<Login/>} />
        <Route path="/home" element={<Home/>} />
        <Route path="/transactions" element={<Transactions/>} />
        <Route path="/userlist" element={<UserList/>} />
      </Routes>
    </div>
  );
}

export default App;
