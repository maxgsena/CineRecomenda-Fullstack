import React, { useState, useEffect, useCallback } from 'react';
import { Link } from 'react-router-dom';
import { useAuth } from './context/AuthContext';
import './MinhaLista.css';

function MinhaLista() {
    const [lista, setLista] = useState([]);
    const { currentUser } = useAuth();

    const buscarLista = useCallback(() => {
        if (currentUser) {
            fetch(`http://localhost:8080/listas/usuario/${currentUser.idUsuario}`)
                .then(response => response.json())
                .then(data => setLista(data))
                .catch(error => console.error("Erro ao buscar a lista do usuário:", error));
        }
    }, [currentUser]);

    useEffect(() => {
        buscarLista();
    }, [buscarLista]);

    // --- NOVA FUNÇÃO PARA MARCAR COMO ASSISTIDO ---
    const handleMarcarComoAssistido = async (idFilme) => {
        if (!currentUser) return;

        const dados = {
            idUsuario: currentUser.idUsuario,
            idFilme: idFilme,
            estado: "Já Assisti" // O novo estado
        };

        try {
            const response = await fetch('http://localhost:8080/listas', {
                method: 'PUT', // Usando o método PUT
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(dados)
            });

            if (response.ok) {
                alert('Filme marcado como assistido!');
                // Atualiza a lista localmente para o usuário ver a mudança na hora
                setLista(listaAtual => listaAtual.map(item => 
                    item.filme.idFilme === idFilme ? { ...item, estado: "Já Assisti" } : item
                ));
            } else {
                alert('Erro ao atualizar o estado do filme.');
            }
        } catch (error) {
            console.error("Erro de conexão:", error);
        }
    };

    return (
        <div className="minha-lista-container">
            <div className="minha-lista-header">
                <h1>Minha Lista de Filmes</h1>
                <a href={`http://localhost:8080/pdf/lista/usuario/${currentUser?.idUsuario}`} className="pdf-download-button" target="_blank" rel="noopener noreferrer">
                    Baixar Lista em PDF
                </a>
            </div>
            {lista.length > 0 ? (
                <div className="lista-grid">
                    {lista.map(item => (
                        <div key={item.id} className="item-card">
                            <h2>{item.filme.nome}</h2>
                            <p><strong>Estado:</strong> <span className="estado-tag">{item.estado}</span></p>
                            
                            {/* --- LÓGICA DO NOVO BOTÃO --- */}
                            {item.estado === "Quero Assistir" && (
                                <button onClick={() => handleMarcarComoAssistido(item.filme.idFilme)} className="action-button">
                                    Marcar como Assistido
                                </button>
                            )}
                            
                            <Link to={`/filmes/${item.filme.idFilme}`} className="details-link">Ver Detalhes</Link>
                        </div>
                    ))}
                </div>
            ) : (
                <p>Sua lista está vazia. Adicione filmes na página inicial!</p>
            )}
        </div>
    );
}

export default MinhaLista;