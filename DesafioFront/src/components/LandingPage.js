import '../styles/landingpage.css';
import hubla from '../images/hubla.png'
import logo from '../images/hubla.svg'

function LandingPage() {  
  return (
    <div>
    <body>
      <header>
        <a href="https://hub.la/">
        <img src={logo} width={100} alt="Hub.la logo"/>
        </a>
        <div class="btn-group">
          <button className="underline-on-hover">Blog</button>
          <button className="underline-on-hover">Ajuda</button>
          <button className="underline-on-hover">Podcast</button>
        </div>
        <div>
          <a href="/login" class="btn-login">Já sou membro</a>
        </div>
        <div>
          <a href="/register" class="btn-cadastro">Comece agora</a>
        </div>
      </header>
      <img src={hubla} alt="Hub.la website screenshot"/>
    </body>  
    </div>
  );
}

export default LandingPage;
