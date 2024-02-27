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
        return authorRepo.findById(id).orElseThrow(() -> new NoResultException("Author not found"));
    }

    public Author saveAuthor(Author author) {
        return authorRepo.save(author);
    }

    public void deleteAuthorById(Long id) {
        authorRepo.deleteById(id);
    }

    public Author updateAuthor(Long id, Author author) {
        Author authorToUpdate = findAuthorById(id);

        authorToUpdate.setName(author.getName());

        return authorRepo.save(authorToUpdate);
    }
}
