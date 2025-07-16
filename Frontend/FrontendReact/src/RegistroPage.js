import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import './RegistroPage.css'; // Usaremos um estilo parecido com o do login

function RegistroPage() {
    const [nome, setNome] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [confirmPassword, setConfirmPassword] = useState('');
    const navigate = useNavigate();

    const handleSubmit = async (event) => {
        event.preventDefault();

        if (password !== confirmPassword) {
            alert("As senhas não coincidem!");
            return;
        }

        const novoUsuario = { nome: nome, email: email, senha: password };

        try {
            const response = await fetch('http://localhost:8080/usuarios', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(novoUsuario)
            });

            if (response.ok) {
                alert('Usuário registrado com sucesso! Você será redirecionado para a página de login.');
                navigate('/login'); // Redireciona para o login após o sucesso
            } else {
                alert('Erro ao registrar. Verifique se o e-mail já está em uso.');
            }
        } catch (error) {
            console.error("Erro de conexão:", error);
            alert('Não foi possível conectar ao servidor.');
        }
    };

    return (
        <div className="registro-container">
            <form className="registro-form" onSubmit={handleSubmit}>
                <h2>Criar Conta</h2>
                <div className="input-group">
                    <label htmlFor="nome">Nome</label>
                    <input type="text" id="nome" value={nome} onChange={(e) => setNome(e.target.value)} required />
                </div>
                <div className="input-group">
                    <label htmlFor="email">Email</label>
                    <input type="email" id="email" value={email} onChange={(e) => setEmail(e.target.value)} required />
                </div>
                <div className="input-group">
                    <label htmlFor="password">Senha</label>
                    <input type="password" id="password" value={password} onChange={(e) => setPassword(e.target.value)} required />
                </div>
                <div className="input-group">
                    <label htmlFor="confirmPassword">Confirmar Senha</label>
                    <input type="password" id="confirmPassword" value={confirmPassword} onChange={(e) => setConfirmPassword(e.target.value)} required />
                </div>
                <button type="submit" className="registro-button">Registrar</button>
                <div className="login-link">
                    <p>Já tem uma conta? <Link to="/login">Faça o login</Link></p>
                </div>
            </form>
        </div>
    );
}

export default RegistroPage;