package com.BookPipeline.BookPipeline.model;

public class SaveAuthorRequest {
    private String name;

    public SaveAuthorRequest(String name) {
        this.name = name;
    }

    public SaveAuthorRequest() {

    }

    public String getName() {
        return this.name;
    }

    public void setName(String testAuthor) {
    }
}
