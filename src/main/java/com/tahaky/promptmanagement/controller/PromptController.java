package com.tahaky.promptmanagement.controller;

import com.tahaky.promptmanagement.dto.PromptRequest;
import com.tahaky.promptmanagement.dto.PromptResponse;
import com.tahaky.promptmanagement.dto.UpdatePromptRequest;
import com.tahaky.promptmanagement.service.PromptService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prompts")
@RequiredArgsConstructor
@Tag(name = "Prompt Management", description = "APIs for creating, updating, and managing AI system prompts")
public class PromptController {

    private final PromptService promptService;

    @Operation(summary = "Create a new prompt", description = "Creates a new AI system prompt")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Prompt created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request body"),
            @ApiResponse(responseCode = "409", description = "Prompt with the same name already exists")
    })
    @PostMapping
    public ResponseEntity<PromptResponse> createPrompt(@Valid @RequestBody PromptRequest request) {
        PromptResponse response = promptService.createPrompt(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "Update an existing prompt", description = "Updates the content or status of an existing prompt and increments its version")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Prompt updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request body"),
            @ApiResponse(responseCode = "404", description = "Prompt not found")
    })
    @PutMapping("/{name}")
    public ResponseEntity<PromptResponse> updatePrompt(
            @Parameter(description = "Unique name of the prompt") @PathVariable String name,
            @Valid @RequestBody UpdatePromptRequest request) {
        PromptResponse response = promptService.updatePrompt(name, request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get the active prompt", description = "Retrieves the currently active prompt")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Active prompt returned"),
            @ApiResponse(responseCode = "404", description = "No active prompt found")
    })
    @GetMapping("/active")
    public ResponseEntity<PromptResponse> getActivePrompt() {
        PromptResponse response = promptService.getActivePrompt();
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get prompt by name", description = "Retrieves a prompt by its unique name")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Prompt found"),
            @ApiResponse(responseCode = "404", description = "Prompt not found")
    })
    @GetMapping("/{name}")
    public ResponseEntity<PromptResponse> getPromptByName(
            @Parameter(description = "Unique name of the prompt") @PathVariable String name) {
        PromptResponse response = promptService.getPromptByName(name);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get prompt by ID", description = "Retrieves a prompt by its numeric ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Prompt found"),
            @ApiResponse(responseCode = "404", description = "Prompt not found")
    })
    @GetMapping("/id/{id}")
    public ResponseEntity<PromptResponse> getPromptById(
            @Parameter(description = "Numeric ID of the prompt") @PathVariable Long id) {
        PromptResponse response = promptService.getPromptById(id);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get all active prompts", description = "Returns a list of all active prompts")
    @ApiResponse(responseCode = "200", description = "List of active prompts")
    @GetMapping
    public ResponseEntity<List<PromptResponse>> getAllActivePrompts() {
        List<PromptResponse> response = promptService.getAllActivePrompts();
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get prompts by category", description = "Returns all prompts that belong to the specified category")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Prompts found"),
            @ApiResponse(responseCode = "404", description = "No prompts found for the category")
    })
    @GetMapping("/category/{category}")
    public ResponseEntity<List<PromptResponse>> getPromptsByCategory(
            @Parameter(description = "Category name") @PathVariable String category) {
        List<PromptResponse> response = promptService.getPromptsByCategory(category);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get version history of a prompt", description = "Returns all versions of the specified prompt")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Version history returned"),
            @ApiResponse(responseCode = "404", description = "Prompt not found")
    })
    @GetMapping("/{name}/versions")
    public ResponseEntity<List<PromptResponse>> getPromptVersionHistory(
            @Parameter(description = "Unique name of the prompt") @PathVariable String name) {
        List<PromptResponse> response = promptService.getPromptVersionHistory(name);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Delete a prompt", description = "Deletes the prompt with the specified name")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Prompt deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Prompt not found")
    })
    @DeleteMapping("/{name}")
    public ResponseEntity<Void> deletePrompt(
            @Parameter(description = "Unique name of the prompt") @PathVariable String name) {
        promptService.deletePrompt(name);
        return ResponseEntity.noContent().build();
    }
}
