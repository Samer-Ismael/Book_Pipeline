package com.BookPipeline.BookPipeline.model;

public record UpdateBookRequest(Long bookId, String title, Long authorId) {
}
