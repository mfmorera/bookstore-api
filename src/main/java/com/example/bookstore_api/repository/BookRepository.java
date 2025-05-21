package com.example.bookstore_api.repository; // Asegúrate de que el paquete sea correcto

import com.example.bookstore_api.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // Anotación opcional, pero buena práctica
public interface BookRepository extends JpaRepository<Book, Long> {
    // JpaRepository ya te proporciona los métodos CRUD básicos (save, findById, findAll, deleteById)
    // No necesitas añadir código aquí para las operaciones básicas.
}