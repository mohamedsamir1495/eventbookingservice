package com.mohamedsamir1495.eventbookingsystem.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record User(@NotBlank @Size(max = 100) @Schema(example = "string") String name,
				   @Email @NotBlank @Schema(example = "user@example.com") String email,
				   @NotBlank @Size(min = 8) @Schema(example = "stringst") String password) { }
