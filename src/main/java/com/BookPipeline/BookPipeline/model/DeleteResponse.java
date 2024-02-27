package com.BookPipeline.BookPipeline.model;

import lombok.Data;

@Data
public class DeleteResponse {
    private Status message;
    enum Status {
        SUCCESS, FAILURE
    }
}
