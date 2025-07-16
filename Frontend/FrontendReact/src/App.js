import React from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import { useAuth } from './context/AuthContext';

import Navbar from './Navbar';
import HomePage from './HomePage';
import Login from './Login';
import RegistroPage from './RegistroPage';
import FilmeDetalhe from './FilmeDetalhe';
import MinhaLista from './MinhaLista';
import RelatorioPage from './RelatorioPage';
import './App.css';

function App() {
  const { currentUser } = useAuth();

  return (
    <Router>
      {currentUser && <Navbar />}
      <div className="App">
        <Routes>
          <Route path="/login" element={<Login />} />
          <Route path="/registro" element={<RegistroPage />} />
          <Route path="/" element={currentUser ? <HomePage /> : <Navigate to="/login" />} />
          <Route path="/filmes/:id" element={currentUser ? <FilmeDetalhe /> : <Navigate to="/login" />} />
          <Route path="/minha-lista" element={currentUser ? <MinhaLista /> : <Navigate to="/login" />} />
          <Route path="/relatorio" element={currentUser ? <RelatorioPage /> : <Navigate to="/login" />} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;