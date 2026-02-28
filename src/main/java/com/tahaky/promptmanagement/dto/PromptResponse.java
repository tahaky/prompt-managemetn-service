package com.tahaky.promptmanagement.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Prompt response object")
public class PromptResponse {

    @Schema(description = "Unique numeric identifier", example = "1")
    private Long id;

    @Schema(description = "Unique name of the prompt", example = "customer-support")
    private String name;

    @Schema(description = "The system prompt content", example = "You are a helpful customer support assistant.")
    private String content;

    @Schema(description = "Category of the prompt", example = "support")
    private String category;

    @Schema(description = "Version number of this prompt", example = "3")
    private Integer version;

    @Schema(description = "Whether this prompt is active", example = "true")
    private Boolean active;

    @Schema(description = "Timestamp when the prompt was created")
    private LocalDateTime createdAt;

    @Schema(description = "Timestamp when the prompt was last updated")
    private LocalDateTime updatedAt;
}
