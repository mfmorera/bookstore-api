package com.example.bookstore_api.service; // Asegúrate de que el paquete sea correcto

import com.example.bookstore_api.Book;
import com.example.bookstore_api.repository.BookRepository; // Importa desde el paquete correcto
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service // Marca esta clase como un componente de servicio de Spring
public class BookService {

    @Autowired // Inyecta la instancia de BookRepository
    private BookRepository bookRepository;

    // Obtener todos los libros con paginación
    public Page<Book> getAllBooks(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }

    // Obtener un libro por su ID
    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    // Guardar un nuevo libro
    public Book saveBook(Book book) {
        return bookRepository.save(book); // Guarda el libro en la base de datos
    }

    // Actualizar un libro existente
    public Book updateBook(Long id, Book updatedBook) {
        // Busca el libro por ID, si existe, actualiza sus campos y lo guarda
        return bookRepository.findById(id)
                .map(book -> {
                    book.setTitle(updatedBook.getTitle());
                    book.setAuthor(updatedBook.getAuthor());
                    return bookRepository.save(book);
                })
                .orElseThrow(() -> new RuntimeException("Libro no encontrado con id: " + id)); // Lanza una excepción si no se encuentra
    }

    // Eliminar un libro por su ID
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }
}