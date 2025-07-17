import React from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useAuth } from './context/AuthContext';
import './Navbar.css';

function Navbar() {
    // 1. Pega não só o logout, mas também o currentUser do nosso contexto
    const { currentUser, logout } = useAuth();
    const navigate = useNavigate();

    const handleLogout = () => {
        logout();
        navigate('/login');
    };

    return (
        <nav className="navbar">
            <Link to="/" className="nav-brand">CineRecomenda</Link>
            
            <div className="nav-links">
                {/* 2. Adiciona a mensagem de boas-vindas */}
                {currentUser && (
                    <span className="welcome-message">
                        Bem-vindo, {currentUser.nome}!
                    </span>
                )}

                <Link to="/" className="nav-link">Home</Link>
                <Link to="/minha-lista" className="nav-link">Minha Lista</Link>
                <Link to="/relatorio" className="nav-link">Relatório</Link>

                {currentUser && currentUser.role === 'ADMIN' && (
                    <Link to="/admin/add-film" className="nav-link-admin">Adicionar Filme</Link>
                )}

                <button onClick={handleLogout} className="logout-button">
                    Sair
                </button>
            </div>
        </nav>
    );
}

export default Navbar;