import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { useAuth } from './context/AuthContext';
import './HomePage.css';

function HomePage() {
    const { currentUser } = useAuth();
    const [filmes, setFilmes] = useState([]);
    const [minhaListaIds, setMinhaListaIds] = useState(new Set());

    useEffect(() => {
        if (currentUser) {
            const idUsuario = currentUser.idUsuario;

            async function carregarDados() {
                try {
                    const [responseFilmes, responseLista] = await Promise.all([
                        fetch('http://localhost:8080/filmes'),
                        fetch(`http://localhost:8080/listas/usuario/${idUsuario}`)
                    ]);

                    if (!responseFilmes.ok || !responseLista.ok) {
                        if (responseLista.status !== 404 && !responseLista.ok)
                            throw new Error('Falha na requisição da lista do usuário');
                        if (!responseFilmes.ok)
                            throw new Error('Falha na requisição de filmes');
                    }

                    const dataFilmes = await responseFilmes.json();
                    let dataLista = [];

                    if (responseLista.ok) {
                        dataLista = await responseLista.json();
                    }

                    const ids = new Set(dataLista.map(item => item.filme.idFilme));

                    setFilmes(dataFilmes);
                    setMinhaListaIds(ids);

                } catch (error) {
                    console.error("Falha ao carregar dados da página inicial:", error);
                }
            }

            carregarDados();
        }
    }, [currentUser]);

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

    const handleDeletarFilme = async (idFilme) => {
        if (window.confirm("Você tem certeza que quer apagar este filme permanentemente do banco de dados?")) {
            try {
                const response = await fetch(`http://localhost:8080/filmes/${idFilme}`, {
                    method: 'DELETE'
                });

                if (response.ok) {
                    alert('Filme removido com sucesso.');
                    setFilmes(prevFilmes => prevFilmes.filter(f => f.idFilme !== idFilme));
                } else {
                    alert('Erro ao remover o filme.');
                }
            } catch (error) {
                console.error("Erro de conexão:", error);
            }
        }
    };

    return (
        <div className="homepage-container">
            <header className="homepage-header">
                <h1>CineRecomenda</h1>
                <p>Sua lista de filmes definitiva.</p>
            </header>

            <main className="movie-grid">
                {filmes.map(filme => (
                    <div key={filme.idFilme} className="movie-card">
                        {filme.posterUrl && (
                            <img src={`http://localhost:8080${filme.poster_url}`} alt={`Pôster de ${filme.nome}`} className="movie-poster" />

                        )}

                        <div className="movie-card-content">
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

                            {currentUser && currentUser.role === 'ADMIN' && (
                                <button onClick={() => handleDeletarFilme(filme.idFilme)} className="delete-admin-button">
                                    Apagar Filme (Admin)
                                </button>
                            )}
                        </div>
                    </div>
                ))}
            </main>
        </div>
    );
}

export default HomePage;
