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
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class AuthorControllerTest {

    @Mock
    private AuthorService authorService;

    @Mock
    private BookService bookService;

    @InjectMocks
    private AuthorController authorController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAuthors() {
        List<Author> authors = new ArrayList<>();
        when(authorService.findAllAuthors()).thenReturn(authors);

        ResponseEntity<List<Author>> response = authorController.getAuthors();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(authors, response.getBody());
    }

    @Test
    public void testSaveAuthor() {
        SaveAuthorRequest saveAuthorRequest = new SaveAuthorRequest(); // Initialize with necessary data
        Author savedAuthor = new Author(); // Initialize with necessary data
        when(authorService.saveAuthor(saveAuthorRequest)).thenReturn(savedAuthor);

        ResponseEntity<Author> response = authorController.saveAuthor(saveAuthorRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(savedAuthor, response.getBody());
    }

    @Test
    public void testDeleteAuthor() {
        Long authorId = 1L;
        doNothing().when(authorService).deleteAuthorById(authorId);

        ResponseEntity<DeleteResponse> response = authorController.deleteAuthor(authorId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(DeleteResponse.DeleteResponseMessage.SUCCESS, response.getBody().message());
    }



    @Test
    public void testUpdateAuthor() {
        Author author = new Author(); // Initialize with necessary data
        when(authorService.updateAuthor(author.getId(), author)).thenReturn(author);

        ResponseEntity<Author> response = authorController.updateAuthor(author);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(author, response.getBody());
    }

    @Test
    public void testGetBooksByAuthor() {
        Long authorId = 1L;
        List<Book> books = new ArrayList<>(); // Initialize with necessary data
        when(bookService.findBooksByAuthorId(authorId)).thenReturn(books);

        ResponseEntity<List<Book>> response = authorController.getBooksByAuthor(authorId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(books, response.getBody());
    }
}
