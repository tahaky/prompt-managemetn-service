package com.tahaky.promptmanagement.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request body for updating an existing prompt")
public class UpdatePromptRequest {

    @NotBlank(message = "Content is required")
    @Schema(description = "Updated system prompt content", example = "You are a highly skilled customer support assistant.")
    private String content;

    @Schema(description = "Updated category (optional)", example = "support")
    private String category;

    @Schema(description = "Updated active status (optional)", example = "true")
    private Boolean active;
}
