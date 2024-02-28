package com.BookPipeline.BookPipeline.service;

import com.BookPipeline.BookPipeline.entity.Book;
import com.BookPipeline.BookPipeline.repository.BookRepository;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepo;
    private final AuthorService authorService;

    public List<Book> findAllBooks() {
        return bookRepo.findAll();
    }

    public Book findBookById(Long id) {
        if (id == null || id < 1) throw new IllegalArgumentException("No valid id was found");
        return bookRepo.findById(id).orElseThrow(() -> new NoResultException("Book not found"));
    }

    public ResponseEntity<String> saveBook(Book book) {
        if (book.getTitle() == null || book.getAuthor() == null || book.getAuthor().getName() == null || book.getAuthor().getId() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Title or Author cannot be empty");
        }
        if (book.getTitle().isEmpty() || book.getAuthor().getName().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Title or Author cannot be empty");
        }
        // Check if the author exists
        authorService.findAuthorById(book.getAuthor().getId());

        bookRepo.save(book);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    public void deleteBookById(Long id) {
        if (id == null || id < 1) throw new IllegalArgumentException("No valid id was found");
        bookRepo.deleteById(id);
    }

    public Book updateBook(Long id, Book book) {
        if (id == null || id < 1) throw new IllegalArgumentException("No valid id was found");
        Book bookToUpdate = findBookById(id);

        bookToUpdate.setTitle(book.getTitle());
        bookToUpdate.setAuthor(book.getAuthor());

        return bookRepo.save(bookToUpdate);
    }

    public List<Book> findBooksByAuthorId(Long id) {
        if (id == null || id < 1) throw new IllegalArgumentException("No valid id was found");
        authorService.findAuthorById(id);

        List<Book> books = bookRepo.findAllByAuthorId(id);

        if (books.isEmpty()) {
            throw new NoResultException("No books found for this author");
        }

        return books;
    }
}
