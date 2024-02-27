package com.BookPipeline.BookPipeline.repository;

import com.BookPipeline.BookPipeline.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
