package com.mz.mreal.controller.api.post;

public class PostResponse {
    private String message;

    public PostResponse(String message) {
        this.message = message;
    }

    public PostResponse() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
