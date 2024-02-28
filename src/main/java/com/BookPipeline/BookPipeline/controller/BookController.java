package com.BookPipeline.BookPipeline.controller;

import com.BookPipeline.BookPipeline.entity.Book;
import com.BookPipeline.BookPipeline.model.DeleteResponse;
import com.BookPipeline.BookPipeline.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;
    @Operation(summary = "Get all books")
    @GetMapping
    public ResponseEntity<List<Book>> getBooks() {
        try {
            return ResponseEntity.ok(bookService.findAllBooks());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    @Operation(summary = "Get book by id")
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBook(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(bookService.findBookById(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    @Operation(summary = "Save book")
    @PostMapping
    public ResponseEntity<Book> saveBook(@RequestBody Book book) {
        try {
            return ResponseEntity.ok(bookService.saveBook(book));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    @Operation(summary = "Delete book by id")
    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteResponse> deleteBook(@PathVariable Long id) {
        try {
            bookService.deleteBookById(id);
            return ResponseEntity.ok(new DeleteResponse(DeleteResponse.DeleteResponseMessage.SUCCESS));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    @Operation(summary = "Replace book with new book by id")
    @PutMapping("")
    public ResponseEntity<Book> updateBook(@RequestBody Book book)  {
        try {
            return ResponseEntity.ok(bookService.updateBook(book.getId(), book));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
