package com.BookPipeline.BookPipeline.controller;

import com.BookPipeline.BookPipeline.model.Author;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//TODO implement the AuthorController
@RestController
@RequestMapping("/authors")
@RequiredArgsConstructor
public class AuthorController {
    @GetMapping("")
    public ResponseEntity<List<Author>> getAuthors() {
        return ResponseEntity.ok().build();
    }
    @GetMapping("/{id}")
    public ResponseEntity<Author> getAuthor(@PathVariable Long id) {
        return ResponseEntity.ok().build();
    }
    @PostMapping("")
    public ResponseEntity<String> saveAuthor(@RequestBody Author author) {
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteAuthor(@PathVariable Long id) {
        return ResponseEntity.ok().build();
    }
}