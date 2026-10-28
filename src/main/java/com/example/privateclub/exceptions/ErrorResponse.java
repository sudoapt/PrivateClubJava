package com.example.privateclub.exceptions;

public record ErrorResponse(int status,
                            String message) {
}