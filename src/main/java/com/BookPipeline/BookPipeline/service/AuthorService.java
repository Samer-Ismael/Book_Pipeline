package com.BookPipeline.BookPipeline.service;

import com.BookPipeline.BookPipeline.entity.Author;
import com.BookPipeline.BookPipeline.repository.AuthorRepository;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        findAuthorById(id);
        authorRepo.deleteById(id);
    }

    public Author updateAuthor(Long id, Author updatedAuthor) {
        if (id == null || id < 1) {
            throw new IllegalArgumentException("No valid id was found");
        }

        if (updatedAuthor.getName() == null) {
            throw new IllegalArgumentException("Author name cannot be null");
        }
        Author authorToUpdate = findAuthorById(id);
        if (updatedAuthor.getName().isEmpty()) {
            throw new IllegalArgumentException("Author name cannot be empty");
        } else {
            authorToUpdate.setName(updatedAuthor.getName());
            return authorRepo.save(authorToUpdate);
        }
    }
}
