package com.BookPipeline.BookPipeline.service;

import com.BookPipeline.BookPipeline.entity.Author;
import com.BookPipeline.BookPipeline.entity.Book;
import com.BookPipeline.BookPipeline.repository.BookRepository;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

//TODO - om titel är tom eller null
//TODO - om author name är null eller tom
//TODO - om author id är tom validera author och lägg till author, sen lägg till book
@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepo;
    private final AuthorService authorService;

    public List<Book> findAllBooks() {
        return bookRepo.findAll();
    }

    public Book findBookById(Long id) {
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
        bookRepo.deleteById(id);
    }

    public Book updateBook(Long id, Book book) {
        Book bookToUpdate = findBookById(id);

        bookToUpdate.setTitle(book.getTitle());
        bookToUpdate.setAuthor(book.getAuthor());

        return bookRepo.save(bookToUpdate);
    }

    public List<Book> findBooksByAuthorId(Long id) {
        authorService.findAuthorById(id);

        List<Book> books = bookRepo.findAllByAuthorId(id);

        if (books.isEmpty()) {
            throw new NoResultException("No books found for this author");
        }

        return books;
    }
}
