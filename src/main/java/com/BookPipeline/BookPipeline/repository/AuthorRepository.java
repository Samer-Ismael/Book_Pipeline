package com.BookPipeline.BookPipeline.repository;

import com.BookPipeline.BookPipeline.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
