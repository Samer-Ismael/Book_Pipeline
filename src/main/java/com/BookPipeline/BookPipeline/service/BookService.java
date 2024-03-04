package com.BookPipeline.BookPipeline.service;

import com.BookPipeline.BookPipeline.entity.Author;
import com.BookPipeline.BookPipeline.entity.Book;
import com.BookPipeline.BookPipeline.model.SaveBookRequest;
import com.BookPipeline.BookPipeline.model.UpdateBookRequest;
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

    public Book saveBook(SaveBookRequest book) {
        if (book.title() == null || book.authorId() == null) {
            throw new IllegalArgumentException("Title or Author cannot be empty");
        }
        if (book.title().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be empty");
        }

        // if the author does not exist the book cannot be saved
        Author author = authorService.findAuthorById(book.authorId());
        Book bookToSave = Book.builder()
                .title(book.title())
                .author(author)
                .build();

        return bookRepo.save(bookToSave);
    }

    public void deleteBookById(Long id) {
        findBookById(id);
        bookRepo.deleteById(id);
    }

    public Book updateBook(Long id, UpdateBookRequest book) {
        if (id == null || id < 1) throw new IllegalArgumentException("No valid id was found");

        Book bookToUpdate = findBookById(id);

        if (book.authorId() != null) {
            // if the author does not exist the book cannot be saved
            Author updatedAuthor = authorService.findAuthorById(book.authorId());
            bookToUpdate.setAuthor(updatedAuthor);
        }

        // check if title needs to be updated
        if (book.title() != null && !book.title().isEmpty()) {
            bookToUpdate.setTitle(book.title());
        }

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
