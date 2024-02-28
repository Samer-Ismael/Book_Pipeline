package com.BookPipeline.BookPipeline.service;

import com.BookPipeline.BookPipeline.entity.Author;
import com.BookPipeline.BookPipeline.repository.AuthorRepository;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorService {
    private final AuthorRepository authorRepo;

    public List<Author> findAllAuthors() {
        return authorRepo.findAll();
    }

    public Author findAuthorById(Long id) {
        if (id == null || id < 1) throw new IllegalArgumentException("No valid id was found");
        return authorRepo.findById(id).orElseThrow(() -> new NoResultException("Author not found"));
    }

    public Author saveAuthor(Author author) {
        if (author.getName() == null) {
            throw new IllegalArgumentException("Author name cannot be empty");
        }
        if (author.getName().isEmpty()) {
            throw new IllegalArgumentException("Author name cannot be empty");
        }

        return authorRepo.save(author);
    }

    public void deleteAuthorById(Long id) {
        if (id == null || id < 1) throw new IllegalArgumentException("No valid id was found");
        authorRepo.deleteById(id);
    }

    public Author updateAuthor(Long id, Author author) {
        if (id == null || id < 1) throw new IllegalArgumentException("No valid id was found");
        Author authorToUpdate = findAuthorById(id);

        authorToUpdate.setName(author.getName());

        return authorRepo.save(authorToUpdate);
    }
}
