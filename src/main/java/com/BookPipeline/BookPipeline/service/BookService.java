package com.BookPipeline.BookPipeline.service;

import com.BookPipeline.BookPipeline.entity.Book;
import com.BookPipeline.BookPipeline.repository.BookRepository;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
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

    public Book saveBook(Book book) {
        return bookRepo.save(book);
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
