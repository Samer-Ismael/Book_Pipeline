package com.BookPipeline.BookPipeline.model;

public record DeleteResponse(DeleteResponseMessage message) {
    public enum DeleteResponseMessage {
        SUCCESS, FAILURE
    }
}
