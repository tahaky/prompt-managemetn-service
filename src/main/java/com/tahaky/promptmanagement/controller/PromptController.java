package com.tahaky.promptmanagement.controller;

import com.tahaky.promptmanagement.dto.PromptRequest;
import com.tahaky.promptmanagement.dto.PromptResponse;
import com.tahaky.promptmanagement.dto.UpdatePromptRequest;
import com.tahaky.promptmanagement.service.PromptService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prompts")
@RequiredArgsConstructor
public class PromptController {

    private final PromptService promptService;

    @PostMapping
    public ResponseEntity<PromptResponse> createPrompt(@Valid @RequestBody PromptRequest request) {
        PromptResponse response = promptService.createPrompt(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{name}")
    public ResponseEntity<PromptResponse> updatePrompt(
            @PathVariable String name,
            @Valid @RequestBody UpdatePromptRequest request) {
        PromptResponse response = promptService.updatePrompt(name, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{name}")
    public ResponseEntity<PromptResponse> getPromptByName(@PathVariable String name) {
        PromptResponse response = promptService.getPromptByName(name);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<PromptResponse> getPromptById(@PathVariable Long id) {
        PromptResponse response = promptService.getPromptById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<PromptResponse>> getAllActivePrompts() {
        List<PromptResponse> response = promptService.getAllActivePrompts();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<PromptResponse>> getPromptsByCategory(@PathVariable String category) {
        List<PromptResponse> response = promptService.getPromptsByCategory(category);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{name}/versions")
    public ResponseEntity<List<PromptResponse>> getPromptVersionHistory(@PathVariable String name) {
        List<PromptResponse> response = promptService.getPromptVersionHistory(name);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<Void> deletePrompt(@PathVariable String name) {
        promptService.deletePrompt(name);
        return ResponseEntity.noContent().build();
    }
}
