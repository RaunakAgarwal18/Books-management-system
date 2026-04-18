package com.raunak.library.book_service.controller;

import com.raunak.library.book_service.dto.AddBookRequest;
import com.raunak.library.book_service.dto.UpdateBookRequest;
import com.raunak.library.book_service.entity.Book;
import com.raunak.library.book_service.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@AllArgsConstructor
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    @GetMapping("")
    public ResponseEntity<Page<Book>> getAllBooks(@RequestParam(required = false) Integer pageNumber, @RequestParam(required = false) Integer pageSize) {
        Page<Book> list = bookService.getAllBooks(pageNumber,pageSize);
        return ResponseEntity.ok(list);
    }

    @PostMapping("")
    public ResponseEntity<Book> addBook(@RequestBody AddBookRequest book) {
        Book newBook = bookService.addBook(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(newBook);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody UpdateBookRequest updateBookRequest){
        Book book = bookService.updateBook(id, updateBookRequest);
        return ResponseEntity.ok(book);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Book> deleteBook(@PathVariable Long id){
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<Page<Book>> searchBooks(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String author,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String isbn,
            @RequestParam(required = false) Integer PageNumber,
            @RequestParam(required = false) Integer PageSize) {
        return ResponseEntity.ok(bookService.searchBooks(title, author, category, isbn, PageNumber,PageSize));
    }
}
