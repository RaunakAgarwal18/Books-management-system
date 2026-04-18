package com.raunak.library.book_service.service.impl;

import com.raunak.library.book_service.dao.BookRepository;
import com.raunak.library.book_service.dto.AddBookRequest;
import com.raunak.library.book_service.dto.UpdateBookRequest;
import com.raunak.library.book_service.entity.Book;
import com.raunak.library.book_service.service.BookService;
import com.raunak.library.book_service.util.AppUtil;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Transactional
    @Override
    public Book addBook(AddBookRequest dto) throws RuntimeException {
        Optional<Book> existing = bookRepository.findByIsbn(normalizeIsbn(dto.getIsbn()));
        if (existing.isPresent()) {
            Book book = existing.get();
            if (!book.getTitle().equalsIgnoreCase(dto.getTitle()) ||
                    !book.getAuthor().equalsIgnoreCase(dto.getAuthor())) {
                throw new RuntimeException("ISBN already exists with different Author or Title");
            }
            book.setCopies(book.getCopies() + dto.getCopies());
            book.setCopiesAvailable(book.getCopiesAvailable() + dto.getCopies());
            book.setImg(dto.getImg());
            book.setDescription(dto.getDescription());
            book.setCategory(dto.getCategory());
            return book;
        }
        return bookRepository.save(addBookRequestMapper(dto));
    }

    @Transactional
    @Override
    public Book updateBook(Long id, UpdateBookRequest dto) throws RuntimeException {

        Book existing = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        if (dto.getTitle() != null) {
            existing.setTitle(dto.getTitle());
        }
        if (dto.getAuthor() != null) {
            existing.setAuthor(dto.getAuthor());
        }
        if (dto.getDescription() != null) {
            existing.setDescription(dto.getDescription());
        }
        if (dto.getCategory() != null) {
            existing.setCategory(dto.getCategory());
        }
        if (dto.getImg() != null) {
            existing.setImg(dto.getImg());
        }
        if (dto.getIsbn() != null && !normalizeIsbn(dto.getIsbn()).equals(normalizeIsbn(existing.getIsbn()))) {
            if (bookRepository.existsByIsbn(normalizeIsbn(dto.getIsbn()))) {
                throw new RuntimeException("ISBN already exists");
            }
            existing.setIsbn(normalizeIsbn(dto.getIsbn()));
        }
        if (dto.getCopies() != null) {
            int issued = existing.getCopies() - existing.getCopiesAvailable();
            if (dto.getCopies() < issued) {
                throw new RuntimeException("Cannot reduce below issued copies");
            }
            existing.setCopies(dto.getCopies());
            existing.setCopiesAvailable(dto.getCopies() - issued);
        }
        return existing;
    }

    @Transactional
    @Override
    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new RuntimeException("Book not found");
        }
        bookRepository.deleteById(id);
    }

    @Override
    public Page<Book> getAllBooks(Integer pageNumber, Integer pageSize) {
        Pageable pageable = AppUtil.createPageable(pageNumber,pageSize);
        return bookRepository.findAll(pageable);
    }

    public Page<Book> searchBooks(String title, String author, String category, String isbn, Integer pageNumber, Integer pageSize) {
        Pageable pageable = AppUtil.createPageable(pageNumber,pageSize);
        if (title != null) {
            return getBooksByTitle(title, pageable);
        }
        if (author != null) {
            return getBooksByAuthor(author, pageable);
        }
        if (category != null) {
            return  getBooksByCategory(category, pageable);
        }
        if(isbn != null) {
            return getBookByIsbn(isbn, pageable);
        }
        return getAllBooks(pageNumber, pageSize);
    }

    @Override
    public Page<Book> getBooksByAuthor(String author, Pageable pageable) {
        return bookRepository.findByAuthorContainingIgnoreCase(author, pageable);
    }

    @Override
    public Page<Book> getBooksByTitle(String title, Pageable pageable) {
        return bookRepository.findByTitleContainingIgnoreCase(title, pageable);
    }

    @Override
    public Page<Book> getBooksByCategory(String category,Pageable pageable) {
        return bookRepository.findByCategoryContainingIgnoreCase(category, pageable);
    }

    @Override
    public Page<Book> getBookByIsbn(String isbn, Pageable pageable) {
        return bookRepository.findByIsbnContainingIgnoreCase(normalizeIsbn(isbn), pageable);
    }

    public Book addBookRequestMapper(AddBookRequest dto) {
        Book book = new Book();
        book.setTitle(dto.getTitle());
        book.setAuthor(dto.getAuthor());
        book.setIsbn(normalizeIsbn(dto.getIsbn()));
        book.setCopies(dto.getCopies());
        book.setCopiesAvailable(dto.getCopies());
        book.setImg(dto.getImg());
        book.setDescription(dto.getDescription());
        book.setCategory(dto.getCategory());
        return book;
    }

    public String normalizeIsbn(String isbn){
        return isbn.replaceAll("[^0-9Xx]", "").trim();
    }
}
