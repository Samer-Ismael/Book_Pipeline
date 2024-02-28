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

    //TODO
    public Author updateAuthor(Long id, Author author) {
        if (id == null || id < 1) throw new IllegalArgumentException("No valid id was found");
        Author authorToUpdate = findAuthorById(id);

        authorToUpdate.setName(author.getName());

        return authorRepo.save(authorToUpdate);
    }

    // Samers sätt:
    // först hämta author med optional
    // om den inte finns returner notfound error
    // finns den- sätt nya värden på det som inte är null, annars använd samma värden som i optional author
    // sen save()

        /*if (userService.existsById(id)) {
        UserEntity newUser = userService.findById(id).get();
        // that will make it easier to update the user, if the field is null, it will not be updated
        if (updatedUser.getPassword() == null) updatedUser.setPassword(newUser.getPassword());
        if (updatedUser.getRole() == null) updatedUser.setRole(newUser.getRole());
        if (updatedUser.getUsername() == null) updatedUser.setUsername(newUser.getUsername());

        userService.updateUserById(id, updatedUser);*/
}
