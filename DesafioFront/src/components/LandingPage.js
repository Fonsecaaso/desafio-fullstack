import '../styles/landingpage.css';
import hubla from '../images/hubla.png'
import logo from '../images/hubla.svg'
import { Link, Navigate } from "react-router-dom";

function LandingPage() {  
  return (
    <div>
    <body>
      <header>
        <a href="https://hub.la/" target="_blank">
        <img src={logo} width={100}/>
        </a>
        <div class="btn-group">
          <button className="btn-landing">Blog</button>
          <button className="btn-landing">Ajuda</button>
          <button className="btn-landing">Podcast</button>
        </div>
        <div>
          <a href="/login" class="btn-login">Já sou membro</a>
        </div>
        <div>
          <a href="/register" class="btn-cadastro">Comece agora</a>
        </div>
      </header>
      <img src={hubla} />
    </body>  
    </div>
  );
}

export default LandingPage;
