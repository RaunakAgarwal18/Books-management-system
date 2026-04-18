package com.raunak.library.book_service.service;


import com.raunak.library.book_service.dto.AddBookRequest;
import com.raunak.library.book_service.dto.UpdateBookRequest;
import com.raunak.library.book_service.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookService {

    // Add, update, delete a book
    public Book addBook(AddBookRequest dto);
    public Book updateBook(Long id, UpdateBookRequest dto);
    public void deleteBook(Long id);

    // Search functionality
    public Page<Book> getAllBooks(Integer pageNumber, Integer pageSize);
    public Page<Book> searchBooks(String title, String author, String category, String isbn, Integer pageNumber, Integer pageSize);
    public Page<Book> getBooksByAuthor(String author, Pageable pageable);
    public Page<Book> getBooksByTitle(String title, Pageable pageable);
    public Page<Book> getBooksByCategory(String category, Pageable pageable);
    public Page<Book> getBookByIsbn(String isbn,  Pageable pageable);

}
