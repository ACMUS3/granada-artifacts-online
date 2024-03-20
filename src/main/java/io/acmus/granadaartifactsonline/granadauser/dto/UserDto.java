package io.acmus.granadaartifactsonline.granadauser.dto;

import jakarta.validation.constraints.NotEmpty;

public record UserDto(
        Integer id,
        @NotEmpty(message = "userName is required.")
        String username,
        boolean enabled,
        @NotEmpty(message = "roles are required.")
        String roles

) {
}
