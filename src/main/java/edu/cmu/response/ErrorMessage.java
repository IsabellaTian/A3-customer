package edu.cmu.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ErrorMessage {
    @JsonProperty
    private String message;

    public ErrorMessage(String message) {
        this.message = message;
    }
}
