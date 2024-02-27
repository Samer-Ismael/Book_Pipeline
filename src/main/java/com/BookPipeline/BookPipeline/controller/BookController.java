package com.BookPipeline.BookPipeline.controller;

import com.BookPipeline.BookPipeline.entity.Book;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//TODO implement the BookController
@RestController
@RequestMapping("/books")
public class BookController {
    @GetMapping
    public ResponseEntity<List<Book>> getBooks() {
        return ResponseEntity.ok().build();
    }
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBook(@PathVariable Long id) {
        return ResponseEntity.ok().build();
    }
    @PostMapping
    public ResponseEntity<String> saveBook(@RequestBody Book book) {
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable Long id) {
        return ResponseEntity.ok().build();
    }
    @PutMapping("")
    public ResponseEntity<Book> updateBook(@RequestBody Book book)  {
        return ResponseEntity.ok().build();
    }
}
