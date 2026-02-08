package com.tahaky.promptmanagement.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePromptRequest {

    @NotBlank(message = "Content is required")
    private String content;

    private String category;

    private Boolean active;
}
