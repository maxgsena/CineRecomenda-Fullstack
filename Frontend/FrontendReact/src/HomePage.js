import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import './HomePage.css';
import { useAuth } from './context/AuthContext';

function HomePage() {
    // 1. Pega o usuário logado do nosso contexto global
    const { currentUser } = useAuth(); 
    
    // Estados para guardar os dados da página
    const [filmes, setFilmes] = useState([]);
    const [minhaListaIds, setMinhaListaIds] = useState(new Set());

    // useEffect para carregar todos os dados necessários quando a página abre
    useEffect(() => {
        // Garante que o código só rode se houver um usuário logado
        if (currentUser) {
            const idUsuario = currentUser.idUsuario;

            async function carregarDados() {
                try {
                    const [responseFilmes, responseLista] = await Promise.all([
                        fetch('http://localhost:8080/filmes'),
                        fetch(`http://localhost:8080/listas/usuario/${idUsuario}`)
                    ]);

                    if (!responseFilmes.ok || !responseLista.ok) {
                        throw new Error('Falha em uma das requisições para a API');
                    }

                    const dataFilmes = await responseFilmes.json();
                    const dataLista = await responseLista.json();

                    const ids = new Set(dataLista.map(item => item.filme.idFilme));

                    setFilmes(dataFilmes);
                    setMinhaListaIds(ids);

                } catch (error) {
                    console.error("Falha ao carregar dados da página inicial:", error);
                }
            }

            carregarDados();
        }
    }, [currentUser]); // O efeito roda sempre que o 'currentUser' mudar

    // Função para adicionar um filme à lista do usuário
    const handleAdicionarNaLista = async (idFilme) => {
        if (!currentUser) return;
        
        const idUsuario = currentUser.idUsuario;
        const dados = { idUsuario, idFilme, estado: "Quero Assistir" };

        try {
            const response = await fetch('http://localhost:8080/listas', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(dados)
            });
            if (response.ok) {
                alert('Filme adicionado à sua lista!');
                setMinhaListaIds(prevIds => new Set(prevIds).add(idFilme));
            } else {
                alert('Erro ao adicionar filme à lista.');
            }
        } catch (error) {
            console.error("Erro de conexão:", error);
        }
    };

    // Função para remover um filme da lista do usuário
    const handleRemoverDaLista = async (idFilme) => {
        if (!currentUser) return;

        const idUsuario = currentUser.idUsuario;
        
        try {
            const response = await fetch(`http://localhost:8080/listas?idUsuario=${idUsuario}&idFilme=${idFilme}`, {
                method: 'DELETE'
            });
            if (response.ok) {
                alert('Filme removido da sua lista.');
                setMinhaListaIds(prevIds => {
                    const novosIds = new Set(prevIds);
                    novosIds.delete(idFilme);
                    return novosIds;
                });
            } else {
                alert('Erro ao remover filme da lista.');
            }
        } catch (error) {
            console.error("Erro de conexão:", error);
        }
    };

    // JSX para renderizar a página
    return (
        <div className="homepage-container">
            <header className="homepage-header">
                <h1>CineRecomenda</h1>
                <p>Sua lista de filmes definitiva.</p>
            </header>

            <main className="movie-grid">
                {filmes.map(filme => (
                    <div key={filme.idFilme} className="movie-card">
                        <Link to={`/filmes/${filme.idFilme}`} className="movie-card-link">
                            <h2>{filme.nome}</h2>
                        </Link>
                        {filme.anoLanc && <p><strong>Lançamento:</strong> {new Date(filme.anoLanc).toLocaleDateString()}</p>}
                        <p><strong>Duração:</strong> {filme.duracao}</p>
                        
                        {minhaListaIds.has(filme.idFilme) ? (
                            <div className="na-lista-container">
                                <span className="na-lista-texto">✔ Na sua Lista</span>
                                <button onClick={() => handleRemoverDaLista(filme.idFilme)} className="remove-list-button">
                                    Remover
                                </button>
                            </div>
                        ) : (
                            <button onClick={() => handleAdicionarNaLista(filme.idFilme)} className="add-list-button">
                                Adicionar à Lista
                            </button>
                        )}
                    </div>
                ))}
            </main>
        </div>
    );
}

export default HomePage;