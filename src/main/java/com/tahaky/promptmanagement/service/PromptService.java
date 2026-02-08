package com.tahaky.promptmanagement.service;

import com.tahaky.promptmanagement.dto.PromptRequest;
import com.tahaky.promptmanagement.dto.PromptResponse;
import com.tahaky.promptmanagement.dto.UpdatePromptRequest;
import com.tahaky.promptmanagement.exception.PromptAlreadyExistsException;
import com.tahaky.promptmanagement.exception.PromptNotFoundException;
import com.tahaky.promptmanagement.model.Prompt;
import com.tahaky.promptmanagement.repository.PromptRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PromptService {

    private final PromptRepository promptRepository;

    @Transactional
    public PromptResponse createPrompt(PromptRequest request) {
        log.info("Creating new prompt with name: {}", request.getName());

        // Check if prompt with same name already exists
        if (promptRepository.findByNameAndActive(request.getName(), true).isPresent()) {
            throw new PromptAlreadyExistsException("Prompt with name '" + request.getName() + "' already exists");
        }

        Prompt prompt = Prompt.builder()
                .name(request.getName())
                .content(request.getContent())
                .category(request.getCategory())
                .active(request.getActive())
                .version(1)
                .build();

        Prompt savedPrompt = promptRepository.save(prompt);
        log.info("Prompt created successfully with id: {}", savedPrompt.getId());

        return convertToResponse(savedPrompt);
    }

    @Transactional
    public PromptResponse updatePrompt(String name, UpdatePromptRequest request) {
        log.info("Updating prompt with name: {}", name);

        Prompt existingPrompt = promptRepository.findByNameAndActive(name, true)
                .orElseThrow(() -> new PromptNotFoundException("Prompt with name '" + name + "' not found"));

        // Deactivate the old version
        existingPrompt.setActive(false);
        promptRepository.save(existingPrompt);

        // Create new version
        Prompt newVersion = Prompt.builder()
                .name(name)
                .content(request.getContent())
                .category(request.getCategory() != null ? request.getCategory() : existingPrompt.getCategory())
                .active(request.getActive() != null ? request.getActive() : true)
                .version(existingPrompt.getVersion() + 1)
                .build();

        Prompt savedPrompt = promptRepository.save(newVersion);
        log.info("Prompt updated successfully. New version: {}", savedPrompt.getVersion());

        return convertToResponse(savedPrompt);
    }

    @Transactional(readOnly = true)
    public PromptResponse getPromptByName(String name) {
        log.info("Fetching prompt with name: {}", name);

        Prompt prompt = promptRepository.findByNameAndActive(name, true)
                .orElseThrow(() -> new PromptNotFoundException("Prompt with name '" + name + "' not found"));

        return convertToResponse(prompt);
    }

    @Transactional(readOnly = true)
    public PromptResponse getPromptById(Long id) {
        log.info("Fetching prompt with id: {}", id);

        Prompt prompt = promptRepository.findById(id)
                .orElseThrow(() -> new PromptNotFoundException("Prompt with id '" + id + "' not found"));

        return convertToResponse(prompt);
    }

    @Transactional(readOnly = true)
    public List<PromptResponse> getAllActivePrompts() {
        log.info("Fetching all active prompts");

        return promptRepository.findByActive(true).stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PromptResponse> getPromptsByCategory(String category) {
        log.info("Fetching prompts by category: {}", category);

        return promptRepository.findByCategoryAndActive(category, true).stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PromptResponse> getPromptVersionHistory(String name) {
        log.info("Fetching version history for prompt: {}", name);

        List<Prompt> versions = promptRepository.findAllVersionsByName(name);
        if (versions.isEmpty()) {
            throw new PromptNotFoundException("No versions found for prompt with name '" + name + "'");
        }

        return versions.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deletePrompt(String name) {
        log.info("Deactivating prompt with name: {}", name);

        Prompt prompt = promptRepository.findByNameAndActive(name, true)
                .orElseThrow(() -> new PromptNotFoundException("Prompt with name '" + name + "' not found"));

        prompt.setActive(false);
        promptRepository.save(prompt);

        log.info("Prompt deactivated successfully");
    }

    // Helper method for AI Integration Service to get current active prompt
    @Transactional(readOnly = true)
    public PromptResponse getCurrentPromptForAI(String name) {
        log.info("AI Service fetching current prompt: {}", name);
        return getPromptByName(name);
    }

    private PromptResponse convertToResponse(Prompt prompt) {
        return PromptResponse.builder()
                .id(prompt.getId())
                .name(prompt.getName())
                .content(prompt.getContent())
                .category(prompt.getCategory())
                .version(prompt.getVersion())
                .active(prompt.getActive())
                .createdAt(prompt.getCreatedAt())
                .updatedAt(prompt.getUpdatedAt())
                .build();
    }
}
