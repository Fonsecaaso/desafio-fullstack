import './App.css';
import Home from "./components/Home";
import { Route, Routes } from "react-router-dom";
import Transactions from "./components/Transactions";
import UserList from "./components/UserList";

function App() {
  return (
    <div>
      <Routes>
        <Route exact path="/" element={<Home/>} />
        <Route path="/home" element={<Home/>} />
        <Route path="/transactions" element={<Transactions/>} />
        <Route path="/userlist" element={<UserList/>} />
      </Routes>
    </div>
  );
}

export default App;
