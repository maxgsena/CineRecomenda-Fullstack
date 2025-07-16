import React, { useState, useEffect } from 'react';
import './RelatorioPage.css';

function RelatorioPage() {
    const [dadosRelatorio, setDadosRelatorio] = useState([]);

    useEffect(() => {
        // Busca os dados do novo endpoint que criamos
        fetch('http://localhost:8080/filmes/com-notas')
            .then(response => response.json())
            .then(data => setDadosRelatorio(data))
            .catch(error => console.error("Erro ao buscar relatório:", error));
    }, []);

    return (
        <div className="relatorio-container">
            <h1>Relatório de Filmes com Nota Média</h1>
            <p>Estes dados são fornecidos por uma Visão (View) no banco de dados.</p>
            <table className="relatorio-table">
                <thead>
                    <tr>
                        <th>ID do Filme</th>
                        <th>Nome</th>
                        <th>Ano de Lançamento</th>
                        <th>Nota Média</th>
                    </tr>
                </thead>
                <tbody>
                    {dadosRelatorio.map(item => (
                        <tr key={item.id_filme}>
                            <td>{item.id_filme}</td>
                            <td>{item.nome}</td>
                            <td>{new Date(item.ano_lanc).getFullYear()}</td>
                            <td>{item.nota_media.toFixed(2)}</td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
}

export default RelatorioPage;