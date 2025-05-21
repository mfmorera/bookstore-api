import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './App.css';

function App() {
  const [titulo, setTitulo] = useState('');
  const [autor, setAutor] = useState('');
  const [libros, setLibros] = useState([]);
  const [filtro, setFiltro] = useState('');
  const [mensaje, setMensaje] = useState('');
  const [editandoId, setEditandoId] = useState(null);

 const API_BASE_URL = 'https://potential-broccoli-v6q97pr4q5gwcwvw9-8080.app.github.dev/api';
  const fetchLibros = async () => {
    try {
      // Hacemos un GET a /api/books
      const res = await axios.get(`${API_BASE_URL}/books`); //
      setLibros(res.data.content || res.data); // Soporta paginado o lista directa
    } catch (err) {
      console.error('❌ Error al cargar libros:', err);
    }
  };

  const guardarLibro = async () => {
    if (!titulo || !autor) return alert('Completa ambos campos');

    try {
      if (editandoId) {
        // Hacemos un PUT a /api/books/{id}
        await axios.put(`${API_BASE_URL}/books/${editandoId}`, { title: titulo, author: autor }); //
        setMensaje('✏️ Libro actualizado');
      } else {
        // Hacemos un POST a /api/books
        await axios.post(`${API_BASE_URL}/books`, { title: titulo, author: autor }); //
        setMensaje('✅ Libro agregado');
      }
      resetFormulario();
      fetchLibros();
    } catch (err) {
      console.error('❌ Error al guardar:', err); //
      setMensaje('Error al guardar'); //
    }
  };

  const eliminarLibro = async (id) => {
    if (!window.confirm('¿Eliminar este libro?')) return;
    try {
      // Hacemos un DELETE a /api/books/{id}
      await axios.delete(`${API_BASE_URL}/books/${id}`); //
      setMensaje('🗑️ Libro eliminado');
      fetchLibros();
    } catch (err) {
      console.error('❌ Error al eliminar:', err);
      setMensaje('Error al eliminar');
    }
  };

  const editarLibro = (libro) => {
    setTitulo(libro.title);
    setAutor(libro.author);
    setEditandoId(libro.id);
  };

  const resetFormulario = () => {
    setTitulo('');
    setAutor('');
    setEditandoId(null);
    setTimeout(() => setMensaje(''), 3000);
  };

  useEffect(() => {
    fetchLibros();
  }, []);

  const librosFiltrados = libros.filter((libro) =>
    libro.title.toLowerCase().includes(filtro.toLowerCase()) ||
    libro.author.toLowerCase().includes(filtro.toLowerCase())
  );

  return (
    <div className="app-container">
      <h1>📚 Gestor de Libros</h1>

      <div className="form-container">
        <input type="text" placeholder="Título" value={titulo} onChange={(e) => setTitulo(e.target.value)} />
        <input type="text" placeholder="Autor" value={autor} onChange={(e) => setAutor(e.target.value)} />
        <button className="add-btn" onClick={guardarLibro}>
          {editandoId ? 'Actualizar' : 'Agregar'}
        </button>
      </div>

      {mensaje && <div className="mensaje">{mensaje}</div>}

      <input
        className="buscar"
        type="text"
        placeholder="🔍 Buscar libro..."
        value={filtro}
        onChange={(e) => setFiltro(e.target.value)}
      />

      <div className="libros-lista">
        {librosFiltrados.map((libro) => (
          <div className="libro-card" key={libro.id}>
            <div>
              <strong>{libro.title}</strong><br />
              <span>{libro.author}</span>
            </div>
            <div className="acciones">
              <button onClick={() => editarLibro(libro)}>✏️</button>
              <button onClick={() => eliminarLibro(libro.id)}>🗑️</button>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
}

export default App;