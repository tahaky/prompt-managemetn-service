package com.tahaky.promptmanagement.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request body for creating a new prompt")
public class PromptRequest {

    @NotBlank(message = "Name is required")
    @Schema(description = "Unique name of the prompt", example = "customer-support")
    private String name;

    @NotBlank(message = "Content is required")
    @Schema(description = "The system prompt content", example = "You are a helpful customer support assistant.")
    private String content;

    @NotBlank(message = "Category is required")
    @Schema(description = "Category of the prompt", example = "support")
    private String category;

    @NotNull(message = "Active status is required")
    @Schema(description = "Whether this prompt is active", example = "true")
    private Boolean active;
}
