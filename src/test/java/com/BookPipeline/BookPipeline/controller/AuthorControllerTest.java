package com.BookPipeline.BookPipeline.controller;

import com.BookPipeline.BookPipeline.entity.Author;
import com.BookPipeline.BookPipeline.entity.Book;
import com.BookPipeline.BookPipeline.model.DeleteResponse;
import com.BookPipeline.BookPipeline.model.SaveAuthorRequest;
import com.BookPipeline.BookPipeline.service.AuthorService;
import com.BookPipeline.BookPipeline.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class AuthorControllerTest {

    @Mock
    private AuthorService authorService;

    @Mock
    private BookService bookService;

    @InjectMocks
    private AuthorController authorController;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    void getAuthorsSuccess() {
        List<Author> authors = new ArrayList<>();
        authors.add(new Author());
        authors.add(new Author());

        when(authorService.findAllAuthors()).thenReturn(authors);

        ResponseEntity<List<Author>> response = authorController.getAuthors();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().size());
        verify(authorService, times(1)).findAllAuthors();
    }

    @Test
    void getAuthorSuccess() {
        Author author = new Author();
        when(authorService.findAuthorById(1L)).thenReturn(author);

        ResponseEntity<Author> response = authorController.getAuthor(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(author, response.getBody());
        verify(authorService, times(1)).findAuthorById(1L);
    }

    @Test
    void saveAuthorSuccess() {
        SaveAuthorRequest request = new SaveAuthorRequest();
        Author author = new Author();

        when(authorService.saveAuthor(request)).thenReturn(author);

        ResponseEntity<Author> response = authorController.saveAuthor(request);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(author, response.getBody());
        verify(authorService, times(1)).saveAuthor(request);
    }

    @Test
    void deleteAuthorSuccess() {
        doNothing().when(authorService).deleteAuthorById(1L);

        ResponseEntity<DeleteResponse> response = authorController.deleteAuthor(1L);

        assertEquals(200, response.getStatusCodeValue());
        verify(authorService, times(1)).deleteAuthorById(1L);
    }


    @Test
    void updateAuthorSuccess() {
        Author author = new Author();
        author.setId(1L);

        when(authorService.updateAuthor(eq(1L), any(Author.class))).thenReturn(author);

        ResponseEntity<Author> response = authorController.updateAuthor(author);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(author, response.getBody());
        verify(authorService, times(1)).updateAuthor(eq(1L), any(Author.class));
    }

    @Test
    void getBooksByAuthorSuccess() {
        List<Book> books = new ArrayList<>();
        books.add(new Book());
        books.add(new Book());

        when(bookService.findBooksByAuthorId(1L)).thenReturn(books);

        ResponseEntity<List<Book>> response = authorController.getBooksByAuthor(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().size());
        verify(bookService, times(1)).findBooksByAuthorId(1L);
    }
}
