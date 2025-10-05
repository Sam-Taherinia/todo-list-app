package com.tromaya.todo_list.domain.dto;

// because it's a java record we automatically get constructions built-in getters,
// equals hash code, toString, and it's immutable by default (final)
public record ErrorResponse(
        int status,
        String message,
        String details
) {
}
