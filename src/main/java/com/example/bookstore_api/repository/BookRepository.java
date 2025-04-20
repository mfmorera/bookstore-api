package com.example.bookstore_api.repository;

import com.example.bookstore_api.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
