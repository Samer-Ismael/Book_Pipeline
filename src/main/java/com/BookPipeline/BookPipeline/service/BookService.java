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
        if (book.getTitle() == null || book.getAuthor() == null || book.getAuthor().getId() == null) {
            throw new IllegalArgumentException("Title or Author cannot be empty");
        }
        if (book.getTitle().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be empty");
        }

        // if the author does not exist the book cannot be saved
        Author author = authorService.findAuthorById(book.getAuthor().getId());
        book.setAuthor(author);

        return bookRepo.save(book);
    }

    public void deleteBookById(Long id) {
        findBookById(id);
        bookRepo.deleteById(id);
    }

    public Book updateBook(Long id, Book updatedBook) {
        if (id == null || id < 1) throw new IllegalArgumentException("No valid id was found");

        Book existingBook = findBookById(id);

        // if updatedbooks author is not null, check its id for updates
        if (updatedBook.getAuthor() != null && updatedBook.getAuthor().getId() != null) {
            // if the author does not exist the book cannot be saved
            Author updatedAuthor = authorService.findAuthorById(updatedBook.getAuthor().getId());
            existingBook.setAuthor(updatedAuthor);
        }

        // check if title needs to be updated
        if (updatedBook.getTitle() == null) {
            updatedBook.setTitle(existingBook.getTitle());
        } else if (updatedBook.getTitle().isEmpty()) {
            updatedBook.setTitle(existingBook.getTitle());
        }

        return bookRepo.save(updatedBook);
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
