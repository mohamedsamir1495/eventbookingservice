package com.mohamedsamir1495.eventbookingsystem.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record Credentials(
		@NotBlank(message = "Email is required")
		@Email(message = "Email should be valid")
		@Schema(example = "user@example.com") String email,

		@NotBlank(message = "Password is required")
		@Size(min = 8, message = "Password should have at least 8 characters")
		@Schema(example = "stringst") String password
) {}
