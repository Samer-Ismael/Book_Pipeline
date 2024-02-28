package com.BookPipeline.BookPipeline.service;

import com.BookPipeline.BookPipeline.entity.Author;
import com.BookPipeline.BookPipeline.entity.Book;
import com.BookPipeline.BookPipeline.repository.BookRepository;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public Book saveBook(Book book) {
        if (book.getTitle() == null || book.getAuthor() == null || book.getAuthor().getName() == null || book.getAuthor().getId() == null) {
            throw new IllegalArgumentException("Title or Author cannot be empty");
        }
        if (book.getTitle().isEmpty() || book.getAuthor().getName().isEmpty()) {
            throw new IllegalArgumentException("Title or Author cannot be empty");
        }

        // Check if the author exists
        authorService.findAuthorById(book.getAuthor().getId());

        return bookRepo.save(book);
    }

    public void deleteBookById(Long id) {
        if (id == null || id < 1) throw new IllegalArgumentException("No valid id was found");
        bookRepo.deleteById(id);
    }

    public Optional<Book> updateBook(Long id, Book updatedBook) {
        if (id == null || id < 1) throw new IllegalArgumentException("No valid id was found");

        Optional<Book> existingBook = bookRepo.findById(id);
        if (existingBook.isPresent()) {
            Book bookToUpdate = existingBook.get();

            // if updatedbooks author is not null, check it id for updates
            if (updatedBook.getAuthor() != null && updatedBook.getAuthor().getId() != null) {
                Author updatedAuthor = authorService.findAuthorById(updatedBook.getAuthor().getId());
                bookToUpdate.setAuthor(updatedAuthor);
            }

            // check if title needs to be updated
            if (updatedBook.getTitle() == null) {
                updatedBook.setTitle(bookToUpdate.getTitle());
            } else if (updatedBook.getTitle().isEmpty()) {
                updatedBook.setTitle(bookToUpdate.getTitle());
            }

            return Optional.of(bookRepo.save(updatedBook));
        } else {
            return Optional.empty();
        }
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
