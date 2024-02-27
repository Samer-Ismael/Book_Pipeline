package com.BookPipeline.BookPipeline.service;

import com.BookPipeline.BookPipeline.entity.Author;
import com.BookPipeline.BookPipeline.model.AuthorPutRequest;
import com.BookPipeline.BookPipeline.model.DeleteResponse;
import com.BookPipeline.BookPipeline.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorService {
    private final AuthorRepository authorRepo;

//    @GetMapping("")
//    public ResponseEntity<List<Author>> getAuthors() {
//        return ResponseEntity.ok().build();
//    }
//    @GetMapping("/{id}")
//    public ResponseEntity<Author> getAuthor(@PathVariable Long id) {
//        return ResponseEntity.ok().build();
//    }
//    @PostMapping("")
//    public ResponseEntity<Author> saveAuthor(@RequestBody Author author) {
//        return ResponseEntity.ok().build();
//    }
//    @DeleteMapping("{id}")
//    public ResponseEntity<DeleteResponse> deleteAuthor(@PathVariable Long id) {
//        return ResponseEntity.ok().build();
//    }
//    @PutMapping("")
//    public ResponseEntity<Author> updateAuthor(@RequestBody AuthorPutRequest authorPutRequest)  {
//        return ResponseEntity.ok().build();
//    }
}
