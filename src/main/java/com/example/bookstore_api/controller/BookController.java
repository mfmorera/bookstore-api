package com.example.bookstore_api.controller; // Asegúrate de que el paquete sea correcto

import com.example.bookstore_api.Book; // Importa tu clase Book
import com.example.bookstore_api.service.BookService; // Importa tu servicio
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity; // Para construir respuestas HTTP
import org.springframework.web.bind.annotation.*; // Anotaciones REST
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.validation.Valid; // ¡MUY IMPORTANTE! Para usar @NotBlank en el Book
import java.net.URI;
import java.util.Optional;

@CrossOrigin(origins = "*") // Permite peticiones desde cualquier origen (para tu frontend)
@RestController // Marca esta clase como un controlador REST
@RequestMapping("/api/books") // Define la ruta base para todos los endpoints de este controlador
public class BookController {

    @Autowired // Inyecta la instancia de BookService
    private BookService bookService;

    // Endpoint para obtener todos los libros (con paginación)
    // GET /api/books
    @GetMapping
    public Page<Book> getBooks(Pageable pageable) {
        return bookService.getAllBooks(pageable);
    }

    // Endpoint para obtener un libro por su ID
    // GET /api/books/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        Optional<Book> book = bookService.getBookById(id);
        return book.map(ResponseEntity::ok) // Si el libro existe, devuelve 200 OK
                   .orElse(ResponseEntity.notFound().build()); // Si no, devuelve 404 Not Found
    }

    // Endpoint para crear un nuevo libro
    // POST /api/books
    @PostMapping
    public ResponseEntity<Book> createBook(@Valid @RequestBody Book book) {
        // @Valid: Activa las validaciones definidas en la clase Book (ej. @NotBlank)
        // @RequestBody: Indica que el cuerpo de la petición HTTP es un objeto Book en JSON
        Book savedBook = bookService.saveBook(book);

        // Construye la URI del nuevo recurso creado (ej. /api/books/1)
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                                                  .path("/{id}")
                                                  .buildAndExpand(savedBook.getId())
                                                  .toUri();
        // Devuelve 201 Created con la ubicación del nuevo recurso en la cabecera Location
        return ResponseEntity.created(location).body(savedBook);
    }

    // Endpoint para actualizar un libro existente
    // PUT /api/books/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @Valid @RequestBody Book updatedBook) {
        // Verifica si el libro existe antes de intentar actualizar
        Optional<Book> optionalBook = bookService.getBookById(id);
        if (optionalBook.isEmpty()) {
            return ResponseEntity.notFound().build(); // Si no existe, devuelve 404 Not Found
        }
        // Actualiza el libro y devuelve 200 OK
        Book book = bookService.updateBook(id, updatedBook);
        return ResponseEntity.ok(book);
    }

    // Endpoint para eliminar un libro
    // DELETE /api/books/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        // Verifica si el libro existe antes de intentar eliminar
        Optional<Book> optionalBook = bookService.getBookById(id);
        if (optionalBook.isEmpty()) {
            return ResponseEntity.notFound().build(); // Si no existe, devuelve 404 Not Found
        }
        // Elimina el libro y devuelve 204 No Content
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
}