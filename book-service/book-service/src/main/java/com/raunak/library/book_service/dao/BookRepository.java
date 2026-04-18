package com.raunak.library.book_service.dao;

import com.raunak.library.book_service.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book,Long> {

    Page<Book> findByAuthorContainingIgnoreCase(String author, Pageable pageable);
    Page<Book> findByTitleContainingIgnoreCase(String title, Pageable pageable);
    Page<Book> findByCategoryContainingIgnoreCase(String category, Pageable pageable);
    Page<Book> findByIsbnContainingIgnoreCase(String isbn, Pageable pageable);

    Optional<Book> findByIsbn(String isbn);
    boolean existsByIsbn(String isbn);
}
