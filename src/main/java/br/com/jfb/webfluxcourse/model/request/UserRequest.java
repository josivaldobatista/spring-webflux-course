package br.com.jfb.webfluxcourse.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRequest(
    @Size(min = 3, max = 50, message = "must be between 3 and 50 character")
    @NotBlank(message = "must not be null or empty")
    String name,
    @Email(message = "invalid e-mail")
    @NotBlank(message = "must not be null or empty")
    String email,
    @Size(min = 3, max = 50, message = "must be between 3 and 20 character")
    @NotBlank(message = "must not be null or empty")
    String password
) {
}
