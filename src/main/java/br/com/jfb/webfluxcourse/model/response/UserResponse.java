package br.com.jfb.webfluxcourse.model.response;

public record UserResponse(
    String id,
    String name,
    String email,
    String password
) {
}
