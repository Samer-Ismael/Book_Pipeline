package com.BookPipeline.BookPipeline.service;

import com.BookPipeline.BookPipeline.entity.Author;
import com.BookPipeline.BookPipeline.model.SaveAuthorRequest;
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

    public Author saveAuthor(SaveAuthorRequest request) {
        if (request.getName() == null || request.getName().isEmpty()) {
            throw new IllegalArgumentException("Author name cannot be empty");
        }

        Author authorToSave = new Author();
        authorToSave.setName(request.getName());

        return authorRepo.save(authorToSave);
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
