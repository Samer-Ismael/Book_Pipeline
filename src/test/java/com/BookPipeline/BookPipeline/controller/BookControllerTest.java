package com.BookPipeline.BookPipeline.controller;

import com.BookPipeline.BookPipeline.entity.Book;
import com.BookPipeline.BookPipeline.login.service.JWTService;
import com.BookPipeline.BookPipeline.login.service.UserService;
import com.BookPipeline.BookPipeline.model.SaveBookRequest;
import com.BookPipeline.BookPipeline.model.UpdateBookRequest;
import com.BookPipeline.BookPipeline.service.BookService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(BookController.class)
@AutoConfigureMockMvc(addFilters = false)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @MockBean
    private UserService userService;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private JWTService jwtService;

    @Test
    void getBooks() throws Exception {
        Book book = new Book(); // Ställ in egenskaper för boken här om nödvändigt.
        when(bookService.findAllBooks()).thenReturn(Arrays.asList(book));

        mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void getBook() throws Exception {
        Long bookId = 1L;
        Book book = new Book(); // Ställ in egenskaper för boken här om nödvändigt.
        when(bookService.findBookById(bookId)).thenReturn(book);

        mockMvc.perform(get("/books/{id}", bookId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void saveBook() throws Exception {
        Book book = new Book(); // Ställ in egenskaper för boken
        when(bookService.saveBook(any(SaveBookRequest.class))).thenReturn(book);

        mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}")) // Skicka en tom JSON för enkelhetens skull
                .andExpect(status().isOk());
    }

    @Test
    void deleteBook() throws Exception {
        Long bookId = 1L;
        doNothing().when(bookService).deleteBookById(bookId);

        mockMvc.perform(delete("/books/{id}", bookId))
                .andExpect(status().isOk());
    }

    @Test
    void updateBook() throws Exception {
        Book book = new Book(); // Ställ in egenskaper för boken
        when(bookService.updateBook(any(Long.class), any(UpdateBookRequest.class))).thenReturn(book);

        mockMvc.perform(put("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}")) // Skicka en tom JSON för enkelhetens skull
                .andExpect(status().isOk());
    }
}