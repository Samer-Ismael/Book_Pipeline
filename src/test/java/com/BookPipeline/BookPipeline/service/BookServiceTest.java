package com.BookPipeline.BookPipeline.service;

import com.BookPipeline.BookPipeline.entity.Author;
import com.BookPipeline.BookPipeline.entity.Book;
import com.BookPipeline.BookPipeline.model.SaveBookRequest;
import com.BookPipeline.BookPipeline.model.UpdateBookRequest;
import com.BookPipeline.BookPipeline.repository.BookRepository;
import jakarta.persistence.NoResultException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class BookServiceTest {

    @Mock
    private BookRepository bookRepo;

    @Mock
    private AuthorService authorService;

    @InjectMocks
    private BookService bookService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAllBooks() {
        when(bookRepo.findAll()).thenReturn(Arrays.asList(new Book(), new Book()));
        List<Book> books = bookService.findAllBooks();
        assertNotNull(books);
        assertEquals(2, books.size());
        verify(bookRepo, times(1)).findAll();
    }

    @Test
    void findBookById() {
        Long id = 1L;
        Book book = new Book();
        when(bookRepo.findById(id)).thenReturn(Optional.of(book));
        Book foundBook = bookService.findBookById(id);
        assertNotNull(foundBook);
        verify(bookRepo, times(1)).findById(eq(id));
    }

    @Test
    void findBookByIdThrowsExceptionWhenNotFound() {
        Long id = 1L;
        when(bookRepo.findById(id)).thenReturn(Optional.empty());
        assertThrows(NoResultException.class, () -> bookService.findBookById(id));
    }

    @Test
    void saveBook() {
        SaveBookRequest request = new SaveBookRequest("Title", 1L);
        Author author = new Author();
        when(authorService.findAuthorById(1L)).thenReturn(author);
        when(bookRepo.save(any(Book.class))).thenReturn(new Book());
        Book savedBook = bookService.saveBook(request);
        assertNotNull(savedBook);
    }

    @Test
    void deleteBookById() {
        Long id = 1L;
        doNothing().when(bookRepo).deleteById(id);
        when(bookRepo.findById(id)).thenReturn(Optional.of(new Book()));
        assertDoesNotThrow(() -> bookService.deleteBookById(id));
        verify(bookRepo, times(1)).deleteById(id);
    }

    @Test
    void updateBook() {
        Long bookId = 1L;
        String newTitle = "New Title";
        Long newAuthorId = 2L;

        // Anta att vi har en konstruktor som tar emot bookId, newTitle och newAuthorId
        UpdateBookRequest request = new UpdateBookRequest(bookId, newTitle, newAuthorId);

        Book book = new Book();
        Author author = new Author();

        when(bookRepo.findById(bookId)).thenReturn(Optional.of(book));
        when(authorService.findAuthorById(newAuthorId)).thenReturn(author);
        when(bookRepo.save(any(Book.class))).thenReturn(book);

        Book updatedBook = bookService.updateBook(bookId, request);

        assertNotNull(updatedBook);
        assertEquals(newTitle, updatedBook.getTitle());
        assertEquals(author, updatedBook.getAuthor());
    }



    @Test
    void findBooksByAuthorId() {
        Long authorId = 1L;
        when(bookRepo.findAllByAuthorId(authorId)).thenReturn(Arrays.asList(new Book(), new Book()));
        List<Book> books = bookService.findBooksByAuthorId(authorId);
        assertNotNull(books);
        assertEquals(2, books.size());
        verify(bookRepo, times(1)).findAllByAuthorId(authorId);
    }

    @Test
    void findBooksByAuthorIdThrowsExceptionWhenNotFound() {
        Long authorId = 1L;
        when(bookRepo.findAllByAuthorId(authorId)).thenReturn(Arrays.asList());
        assertThrows(NoResultException.class, () -> bookService.findBooksByAuthorId(authorId));
    }
}
