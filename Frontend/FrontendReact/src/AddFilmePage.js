import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import './AddFilmePage.css';

function AddFilmePage() {
    const [filme, setFilme] = useState({
        nome: '',
        anoLanc: '',
        sinopse: '',
        duracao: ''
    });
    const [generos, setGeneros] = useState([]);
    const [generosSelecionados, setGenerosSelecionados] = useState(new Set());
    const [posterFile, setPosterFile] = useState(null);
    const navigate = useNavigate();

    useEffect(() => {
        fetch('http://localhost:8080/generos')
            .then(response => response.json())
            .then(data => setGeneros(data))
            .catch(error => console.error("Erro ao buscar gêneros:", error));
    }, []);

    const handleChange = (event) => {
        const { name, value } = event.target;
        setFilme(prevState => ({ ...prevState, [name]: value }));
    };

    const handleGenreChange = (genreId) => {
        setGenerosSelecionados(prevSelected => {
            const newSelected = new Set(prevSelected);
            if (newSelected.has(genreId)) {
                newSelected.delete(genreId);
            } else {
                newSelected.add(genreId);
            }
            return newSelected;
        });
    };

    const handleFileChange = (event) => {
        setPosterFile(event.target.files[0]);
    };

    const handleSubmit = async (event) => {
        event.preventDefault();

        if (!posterFile) {
            alert('Por favor, selecione uma imagem para o pôster.');
            return;
        }

        const filmeParaEnviar = {
            ...filme,
            generos: Array.from(generosSelecionados).map(id => ({ idGenero: id }))
        };

        const formData = new FormData();
        formData.append('filme', new Blob([JSON.stringify(filmeParaEnviar)], { type: 'application/json' }));
        formData.append('poster', posterFile);

        try {
            const response = await fetch('http://localhost:8080/filmes', {
                method: 'POST',
                body: formData
            });

            if (response.ok) {
                alert('Filme adicionado com sucesso!');
                navigate('/');
            } else {
                alert('Erro ao salvar os dados do filme.');
            }
        } catch (error) {
            console.error("Erro ao salvar filme:", error);
            alert('Erro de comunicação com o servidor.');
        }
    };

    return (
        <div className="add-filme-container">
            <form onSubmit={handleSubmit} className="add-filme-form">
                <h1>Adicionar Novo Filme</h1>

                <div className="form-group">
                    <label htmlFor="nome">Nome do Filme</label>
                    <input type="text" id="nome" name="nome" value={filme.nome} onChange={handleChange} required />
                </div>

                <div className="form-group">
                    <label htmlFor="anoLanc">Data de Lançamento</label>
                    <input type="date" id="anoLanc" name="anoLanc" value={filme.anoLanc} onChange={handleChange} required />
                </div>

                <div className="form-group">
                    <label htmlFor="poster">Pôster do Filme</label>
                    <input type="file" id="poster" name="poster" onChange={handleFileChange} required />
                </div>

                <div className="form-group">
                    <label>Gêneros</label>
                    <div className="genres-checkbox-group">
                        {generos.map(genre => (
                            <div key={genre.idGenero} className="genre-item">
                                <input
                                    type="checkbox"
                                    id={`genre-${genre.idGenero}`}
                                    onChange={() => handleGenreChange(genre.idGenero)}
                                />
                                <label htmlFor={`genre-${genre.idGenero}`}>{genre.nome}</label>
                            </div>
                        ))}
                    </div>
                </div>

                <div className="form-group">
                    <label htmlFor="sinopse">Sinopse</label>
                    <textarea id="sinopse" name="sinopse" value={filme.sinopse} onChange={handleChange} required></textarea>
                </div>

                <div className="form-group">
                    <label htmlFor="duracao">Duração (hh:mm:ss)</label>
                    <input type="text" id="duracao" name="duracao" value={filme.duracao} onChange={handleChange} placeholder="Ex: 02:30:00" required />
                </div>

                <button type="submit">Salvar Filme</button>
            </form>
        </div>
    );
}

export default AddFilmePage;
