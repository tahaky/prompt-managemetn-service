package com.tahaky.promptmanagement.controller;

import com.tahaky.promptmanagement.dto.PromptResponse;
import com.tahaky.promptmanagement.service.PromptService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Integration endpoint for AI services to fetch current system prompts
 */
@RestController
@RequestMapping("/api/integration")
@RequiredArgsConstructor
public class AIIntegrationController {

    private final PromptService promptService;

    /**
     * Get the current active prompt for AI services
     * This endpoint is specifically designed for AI integration services
     * to fetch the most up-to-date system prompt
     */
    @GetMapping("/prompts/{name}")
    public ResponseEntity<PromptResponse> getCurrentPrompt(@PathVariable String name) {
        PromptResponse response = promptService.getCurrentPromptForAI(name);
        return ResponseEntity.ok(response);
    }

    /**
     * Health check endpoint for integration services
     */
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("AI Integration Service is running");
    }
}
