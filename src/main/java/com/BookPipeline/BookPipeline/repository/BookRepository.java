package com.BookPipeline.BookPipeline.repository;

import com.BookPipeline.BookPipeline.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findAllByAuthorId(Long id);
}
