package com.tahaky.promptmanagement.controller;

import com.tahaky.promptmanagement.dto.PromptResponse;
import com.tahaky.promptmanagement.service.PromptService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Integration endpoint for AI services to fetch current system prompts
 */
@RestController
@RequestMapping("/api/integration")
@RequiredArgsConstructor
@Tag(name = "AI Integration", description = "Endpoints for AI services to fetch current system prompts")
public class AIIntegrationController {

    private final PromptService promptService;

    /**
     * Get the current active prompt for AI services
     * This endpoint is specifically designed for AI integration services
     * to fetch the most up-to-date system prompt
     */
    @Operation(summary = "Get current prompt for AI", description = "Returns the current active version of the specified prompt for use by AI services")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Prompt returned successfully"),
            @ApiResponse(responseCode = "404", description = "Prompt not found")
    })
    @GetMapping("/prompts/{name}")
    public ResponseEntity<PromptResponse> getCurrentPrompt(
            @Parameter(description = "Unique name of the prompt") @PathVariable String name) {
        PromptResponse response = promptService.getCurrentPromptForAI(name);
        return ResponseEntity.ok(response);
    }

    /**
     * Health check endpoint for integration services
     */
    @Operation(summary = "Health check", description = "Returns the health status of the AI Integration Service")
    @ApiResponse(responseCode = "200", description = "Service is running")
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("AI Integration Service is running");
    }
}
