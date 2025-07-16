import React from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useAuth } from './context/AuthContext'; // Importa nosso hook de autenticação
import './Navbar.css';

function Navbar() {
    const { logout } = useAuth(); // Pega a função de logout do nosso contexto
    const navigate = useNavigate();

    const handleLogout = () => {
        logout(); // Limpa os dados do usuário no contexto
        navigate('/login'); // Redireciona o usuário para a página de login
    };

    return (
        <nav className="navbar">
            <Link to="/" className="nav-brand">CineRecomenda</Link>
            <div className="nav-links">
                <Link to="/" className="nav-link">Home</Link>
                <Link to="/minha-lista" className="nav-link">Minha Lista</Link>
                <Link to="/relatorio" className="nav-link">Relatório</Link>
                
                {/* NOVO BOTÃO DE LOGOUT */}
                <button onClick={handleLogout} className="logout-button">
                    Sair
                </button>
            </div>
        </nav>
    );
}

export default Navbar;