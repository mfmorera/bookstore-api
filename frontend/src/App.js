import React, { useEffect, useState } from 'react';

function App() {
  const [books, setBooks] = useState([]);
  const [title, setTitle] = useState('');
  const [author, setAuthor] = useState('');

  // Obtener libros existentes
  const fetchBooks = () => {
    fetch('http://localhost:8080/books')
      .then(response => response.json())
      .then(data => setBooks(data))
      .catch(error => console.error('Error al obtener libros:', error));
  };

  useEffect(() => {
    fetchBooks();
  }, []);

  // Enviar nuevo libro al backend
  const handleSubmit = (e) => {
    e.preventDefault();
    fetch('http://localhost:8080/books', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({ title, author })
    })
      .then(response => {
        if (response.ok) {
          setTitle('');
          setAuthor('');
          fetchBooks(); // Refrescar la lista
        } else {
          console.error('Error al crear libro');
        }
      })
      .catch(error => console.error('Error en POST:', error));
  };

  return (
    <div className="App">
      <h1>Lista de Libros</h1>

      <form onSubmit={handleSubmit}>
        <input
          type="text"
          placeholder="Título"
          value={title}
          onChange={(e) => setTitle(e.target.value)}
          required
        />
        <input
          type="text"
          placeholder="Autor"
          value={author}
          onChange={(e) => setAuthor(e.target.value)}
          required
        />
        <button type="submit">Agregar Libro</button>
      </form>

      <ul>
        {books.map((book, i) => (
          <li key={i}>
            <strong>{book.title}</strong> - {book.author}
          </li>
        ))}
      </ul>
    </div>
  );
}

export default App;

