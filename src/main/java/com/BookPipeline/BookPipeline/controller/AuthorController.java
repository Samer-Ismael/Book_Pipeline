package com.BookPipeline.BookPipeline.controller;

import com.BookPipeline.BookPipeline.entity.Author;
import com.BookPipeline.BookPipeline.entity.Book;
import com.BookPipeline.BookPipeline.model.DeleteResponse;
import com.BookPipeline.BookPipeline.service.AuthorService;
import com.BookPipeline.BookPipeline.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/authors")
@RequiredArgsConstructor
public class AuthorController {
    private final AuthorService authorService;
    private final BookService bookService;
    @Operation(summary = "Get all authors")
    @GetMapping("")
    public ResponseEntity<List<Author>> getAuthors() {
        try {
            return ResponseEntity.ok(authorService.findAllAuthors());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    @Operation(summary = "Get author by id")
    @GetMapping("/{id}")
    public ResponseEntity<Author> getAuthor(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(authorService.findAuthorById(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    @Operation(summary = "Save author")
    @PostMapping("")
    public ResponseEntity<Author> saveAuthor(@RequestBody Author author) {
        try {
            return ResponseEntity.ok(authorService.saveAuthor(author));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    @Operation(summary = "Delete author by id")
    @DeleteMapping("{id}")
    public ResponseEntity<DeleteResponse> deleteAuthor(@PathVariable Long id) {
        try {
            authorService.deleteAuthorById(id);
            return ResponseEntity.ok(new DeleteResponse(DeleteResponse.DeleteResponseMessage.SUCCESS));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    @Operation(summary = "Replace author with new author by id")
    @PutMapping("")
    public ResponseEntity<Author> updateAuthor(@RequestBody Author author)  {
        try {
            return ResponseEntity.ok(authorService.updateAuthor(author.getId(), author));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    @Operation(summary = "Get all books by author id")
    @GetMapping("/{id}/books")
    public ResponseEntity<List<Book>> getBooksByAuthor(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(bookService.findBooksByAuthorId(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}