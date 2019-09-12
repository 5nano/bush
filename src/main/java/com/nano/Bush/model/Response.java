package com.nano.Bush.model;

public class Response {

    private String message;
    private Integer statusCode;

    public Response(String message, Integer statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public Integer getStatusCode() {
        return statusCode;
    }
}
