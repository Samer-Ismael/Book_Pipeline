package com.BookPipeline.BookPipeline.controller;

import com.BookPipeline.BookPipeline.entity.Author;
import com.BookPipeline.BookPipeline.entity.Book;
import com.BookPipeline.BookPipeline.model.DeleteResponse;
import com.BookPipeline.BookPipeline.model.SaveAuthorRequest;
import com.BookPipeline.BookPipeline.service.AuthorService;
import com.BookPipeline.BookPipeline.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class AuthorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthorService authorService;

    @MockBean
    private BookService bookService;

    private Author author;
    private Book book;
    private List<Author> authors;
    private List<Book> books;
    private SaveAuthorRequest saveAuthorRequest;
    private DeleteResponse deleteResponse;

    @BeforeEach
    void setUp() {
        author = new Author();
        book = new Book();
        authors = Arrays.asList(author);
        books = Arrays.asList(book);
        saveAuthorRequest = new SaveAuthorRequest();
        deleteResponse = new DeleteResponse(DeleteResponse.DeleteResponseMessage.SUCCESS);

        when(authorService.findAllAuthors()).thenReturn(authors);
        when(authorService.findAuthorById(anyLong())).thenReturn(author);
        when(authorService.saveAuthor(any())).thenReturn(author);
        when(bookService.findBooksByAuthorId(anyLong())).thenReturn(books);
    }

    @Test
    void getAuthors() throws Exception {
        mockMvc.perform(get("/authors")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getAuthor() throws Exception {
        mockMvc.perform(get("/authors/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void saveAuthor() throws Exception {
        mockMvc.perform(post("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isOk());
    }

    @Test
    void deleteAuthor() throws Exception {
        mockMvc.perform(delete("/authors/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void updateAuthor() throws Exception {
        mockMvc.perform(put("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isOk());
    }

    @Test
    void getBooksByAuthor() throws Exception {
        mockMvc.perform(get("/authors/1/books")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}