package com.BookPipeline.BookPipeline.service;

import com.BookPipeline.BookPipeline.entity.Book;
import com.BookPipeline.BookPipeline.login.model.UserEntity;
import com.BookPipeline.BookPipeline.repository.BookRepository;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
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

    //TODO
    public Book updateBook(Long id, Book book) {
        if (id == null || id < 1) throw new IllegalArgumentException("No valid id was found");
        Book bookToUpdate = findBookById(id);

        bookToUpdate.setTitle(book.getTitle());
        bookToUpdate.setAuthor(book.getAuthor());

        return bookRepo.save(bookToUpdate);
    }

    // Samers sätt:
    // först hämta book med optional
    // om den inte finns returner notfound error
    // finns den- sätt nya värden på det som inte är null, annars använd samma värden som i optional book
    // sen save()

        /*if (userService.existsById(id)) {
        UserEntity newUser = userService.findById(id).get();
        // that will make it easier to update the user, if the field is null, it will not be updated
        if (updatedUser.getPassword() == null) updatedUser.setPassword(newUser.getPassword());
        if (updatedUser.getRole() == null) updatedUser.setRole(newUser.getRole());
        if (updatedUser.getUsername() == null) updatedUser.setUsername(newUser.getUsername());

        userService.updateUserById(id, updatedUser);*/

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
