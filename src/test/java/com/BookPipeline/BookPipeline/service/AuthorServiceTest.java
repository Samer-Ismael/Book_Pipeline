package com.BookPipeline.BookPipeline.service;

import com.BookPipeline.BookPipeline.entity.Author;
import com.BookPipeline.BookPipeline.model.SaveAuthorRequest;
import com.BookPipeline.BookPipeline.repository.AuthorRepository;
import jakarta.persistence.NoResultException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthorServiceTest {

    @Mock
    private AuthorRepository authorRepo;

    @InjectMocks
    private AuthorService authorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAllAuthors() {
        when(authorRepo.findAll()).thenReturn(Arrays.asList(new Author(), new Author()));
        List<Author> authors = authorService.findAllAuthors();
        assertNotNull(authors);
        assertEquals(2, authors.size());
        verify(authorRepo, times(1)).findAll();
    }

    @Test
    void findAuthorById() {
        Long id = 1L;
        Author author = new Author();
        when(authorRepo.findById(id)).thenReturn(Optional.of(author));
        Author foundAuthor = authorService.findAuthorById(id);
        assertNotNull(foundAuthor);
        verify(authorRepo, times(1)).findById(eq(id));
    }

    @Test
    void findAuthorByIdThrowsExceptionWhenNotFound() {
        Long id = 1L;
        when(authorRepo.findById(id)).thenReturn(Optional.empty());
        assertThrows(NoResultException.class, () -> authorService.findAuthorById(id));
    }

    @Test
    void saveAuthor() {
        SaveAuthorRequest request = new SaveAuthorRequest("Author Name");
        when(authorRepo.save(any(Author.class))).thenReturn(new Author());
        Author savedAuthor = authorService.saveAuthor(request);
        assertNotNull(savedAuthor);
    }

    @Test
    void deleteAuthorById() {
        Long id = 1L;
        doNothing().when(authorRepo).deleteById(id);
        when(authorRepo.findById(id)).thenReturn(Optional.of(new Author()));
        assertDoesNotThrow(() -> authorService.deleteAuthorById(id));
        verify(authorRepo, times(1)).deleteById(id);
    }

    @Test
    void updateAuthor() {
        Long id = 1L;
        String newName = "Updated Name";
        Author updatedAuthorInfo = new Author();
        updatedAuthorInfo.setName(newName);

        Author existingAuthor = new Author();
        existingAuthor.setName("Old Name");
        when(authorRepo.findById(id)).thenReturn(Optional.of(existingAuthor));
        when(authorRepo.save(any(Author.class))).thenReturn(existingAuthor);

        Author updatedAuthor = authorService.updateAuthor(id, updatedAuthorInfo);

        assertNotNull(updatedAuthor);
        assertEquals(newName, updatedAuthor.getName());
    }
}
