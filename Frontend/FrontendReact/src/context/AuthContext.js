import React, { createContext, useState, useContext } from 'react';

// Cria o contexto
const AuthContext = createContext(null);

// Componente Provedor: ele vai "prover" os dados de autenticação para toda a app
export const AuthProvider = ({ children }) => {
    const [currentUser, setCurrentUser] = useState(null); // Estado para guardar o usuário logado

    const login = async (email, password) => {
        const response = await fetch('http://localhost:8080/usuarios/login', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ email, senha: password })
        });

        if (response.ok) {
            const user = await response.json();
            setCurrentUser(user); // Guarda o usuário logado no estado
            return true;
        }
        return false;
    };

    const logout = () => {
        setCurrentUser(null); // Limpa o usuário do estado
    };

    // O valor que será compartilhado com os componentes filhos
    const value = { currentUser, login, logout };

    return (
        <AuthContext.Provider value={value}>
            {children}
        </AuthContext.Provider>
    );
};

// Hook customizado para facilitar o uso do contexto
export const useAuth = () => {
    return useContext(AuthContext);
};