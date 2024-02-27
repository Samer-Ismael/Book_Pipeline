package com.BookPipeline.BookPipeline.controller;

import com.BookPipeline.BookPipeline.entity.Author;
import com.BookPipeline.BookPipeline.model.AuthorPutRequest;
import com.BookPipeline.BookPipeline.model.DeleteResponse;
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
    public ResponseEntity<Author> saveAuthor(@RequestBody Author author) {
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("{id}")
    public ResponseEntity<DeleteResponse> deleteAuthor(@PathVariable Long id) {
        return ResponseEntity.ok().build();
    }
    @PutMapping("")
    public ResponseEntity<Author> updateAuthor(@RequestBody AuthorPutRequest authorPutRequest)  {
        return ResponseEntity.ok().build();
    }
}