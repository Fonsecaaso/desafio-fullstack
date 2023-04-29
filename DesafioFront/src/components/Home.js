import React from "react";
import axios from "axios";
import './styles.css';
import { Link, Navigate } from "react-router-dom";

function Home(props) {
  const userToken = localStorage.getItem("token");
  const username = localStorage.getItem("username");

  if (userToken == null) {
    return <Navigate to="/login" />;
  }

  const handleFileSelect = (event) => {
    const file = event.target.files[0];
    const reader = new FileReader();
    
    reader.onload = (event) => {
      const fileContents = event.target.result;
      axios
          .post("http://localhost:8085/transactions", { file: fileContents })
          .then((response) => {
            if(response.status===202){
              console.log(response.data);
              window.alert(response.data.successfulCases + " transações cadastradas \nupload bem sucedido");
            }else{
              const messages = response.data.errors.map(pessoa => pessoa.message + " - linha " + pessoa.linha);
              window.alert(response.data.successfulCases + " transações cadastradas" + messages.join("\n"));
            }
          })
          .catch((error) => {
            console.log(error);
          });
    };
  
    reader.readAsText(file);
  };

  return (
    <div className="container">
        <form>
            <Link to="/transactions">
                <button>Ver histórico de transações</button>
            </Link>
            <Link to="/userlist">
                <button>Ver saldo dos usuários</button>
            </Link>
        </form>
      <h2>Bem-vindo, {username}!</h2>
      <form>
        <div>
            <label for="arquivo">Enviar arquivo .txt: </label>
            <input type="file" onChange={handleFileSelect}/>
        </div>
      </form>

    </div>
  );
}

export default Home;


