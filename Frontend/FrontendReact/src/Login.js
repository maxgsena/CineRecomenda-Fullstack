import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useAuth } from './context/AuthContext';
import './Login.css';

function Login() {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const { login } = useAuth();
    const navigate = useNavigate();

    const handleSubmit = async (event) => {
        event.preventDefault();
        try {
            const success = await login(email, password);
            if (success) {
                navigate('/');
            } else {
                alert('Email ou senha inválidos.');
            }
        } catch (error) {
            console.error("Erro no login:", error);
            alert('Falha na comunicação com o servidor.');
        }
    };

    return (
        <div className="login-container">
            <form className="login-form" onSubmit={handleSubmit}>
                <h2>Login</h2>
                <div className="input-group">
                    <label htmlFor="email">Email</label>
                    <input type="email" id="email" value={email} onChange={(e) => setEmail(e.target.value)} required />
                </div>
                <div className="input-group">
                    <label htmlFor="password">Senha</label>
                    <input type="password" id="password" value={password} onChange={(e) => setPassword(e.target.value)} required />
                </div>
                <button type="submit" className="login-button">Entrar</button>
                <div className="register-link">
                    <p>Não tem uma conta? <Link to="/registro">Registre-se</Link></p>
                </div>
            </form>
        </div>
    );
}

export default Login;