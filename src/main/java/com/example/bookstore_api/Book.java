package com.example.bookstore_api;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank; // ¡NUEVO IMPORT NECESARIO!

@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El título es obligatorio") // ¡NUEVA ANOTACIÓN!
    private String title;

    @NotBlank(message = "El autor es obligatorio") // ¡NUEVA ANOTACIÓN!
    private String author;

    // Constructores
    public Book() {}

    public Book(String title, String author) {
        this.title = title;
        this.author = author;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
}