package com.tahaky.promptmanagement.service;

import com.tahaky.promptmanagement.dto.PromptRequest;
import com.tahaky.promptmanagement.dto.PromptResponse;
import com.tahaky.promptmanagement.dto.UpdatePromptRequest;
import com.tahaky.promptmanagement.exception.PromptAlreadyExistsException;
import com.tahaky.promptmanagement.exception.PromptNotFoundException;
import com.tahaky.promptmanagement.model.Prompt;
import com.tahaky.promptmanagement.repository.PromptRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PromptServiceTest {

    @Mock
    private PromptRepository promptRepository;

    @InjectMocks
    private PromptService promptService;

    private Prompt testPrompt;
    private PromptRequest testRequest;

    @BeforeEach
    void setUp() {
        testPrompt = Prompt.builder()
                .id(1L)
                .name("test-prompt")
                .content("Test content")
                .category("test")
                .version(1)
                .active(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        testRequest = PromptRequest.builder()
                .name("test-prompt")
                .content("Test content")
                .category("test")
                .active(true)
                .build();
    }

    @Test
    void createPrompt_Success() {
        when(promptRepository.findByNameAndActive(anyString(), any())).thenReturn(Optional.empty());
        when(promptRepository.save(any(Prompt.class))).thenReturn(testPrompt);

        PromptResponse response = promptService.createPrompt(testRequest);

        assertNotNull(response);
        assertEquals("test-prompt", response.getName());
        assertEquals("Test content", response.getContent());
        verify(promptRepository, times(1)).save(any(Prompt.class));
    }

    @Test
    void createPrompt_AlreadyExists() {
        when(promptRepository.findByNameAndActive(anyString(), any())).thenReturn(Optional.of(testPrompt));

        assertThrows(PromptAlreadyExistsException.class, () -> {
            promptService.createPrompt(testRequest);
        });

        verify(promptRepository, never()).save(any(Prompt.class));
    }

    @Test
    void updatePrompt_Success() {
        UpdatePromptRequest updateRequest = UpdatePromptRequest.builder()
                .content("Updated content")
                .build();

        when(promptRepository.findByNameAndActive(anyString(), any())).thenReturn(Optional.of(testPrompt));
        
        Prompt updatedPrompt = Prompt.builder()
                .id(2L)
                .name("test-prompt")
                .content("Updated content")
                .category("test")
                .version(2)
                .active(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        
        when(promptRepository.save(any(Prompt.class))).thenReturn(updatedPrompt);

        PromptResponse response = promptService.updatePrompt("test-prompt", updateRequest);

        assertNotNull(response);
        assertEquals(2, response.getVersion());
        assertEquals("Updated content", response.getContent());
        verify(promptRepository, times(2)).save(any(Prompt.class));
    }

    @Test
    void getPromptByName_Success() {
        when(promptRepository.findByNameAndActive(anyString(), any())).thenReturn(Optional.of(testPrompt));

        PromptResponse response = promptService.getPromptByName("test-prompt");

        assertNotNull(response);
        assertEquals("test-prompt", response.getName());
        verify(promptRepository, times(1)).findByNameAndActive(anyString(), any());
    }

    @Test
    void getPromptByName_NotFound() {
        when(promptRepository.findByNameAndActive(anyString(), any())).thenReturn(Optional.empty());

        assertThrows(PromptNotFoundException.class, () -> {
            promptService.getPromptByName("non-existent");
        });
    }

    @Test
    void getAllActivePrompts_Success() {
        when(promptRepository.findByActive(true)).thenReturn(List.of(testPrompt));

        List<PromptResponse> responses = promptService.getAllActivePrompts();

        assertNotNull(responses);
        assertEquals(1, responses.size());
        verify(promptRepository, times(1)).findByActive(true);
    }

    @Test
    void getPromptsByCategory_Success() {
        when(promptRepository.findByCategoryAndActive(anyString(), any())).thenReturn(List.of(testPrompt));

        List<PromptResponse> responses = promptService.getPromptsByCategory("test");

        assertNotNull(responses);
        assertEquals(1, responses.size());
        verify(promptRepository, times(1)).findByCategoryAndActive(anyString(), any());
    }

    @Test
    void deletePrompt_Success() {
        when(promptRepository.findByNameAndActive(anyString(), any())).thenReturn(Optional.of(testPrompt));
        when(promptRepository.save(any(Prompt.class))).thenReturn(testPrompt);

        assertDoesNotThrow(() -> promptService.deletePrompt("test-prompt"));

        verify(promptRepository, times(1)).save(any(Prompt.class));
    }

    @Test
    void getCurrentPromptForAI_Success() {
        when(promptRepository.findByNameAndActive(anyString(), any())).thenReturn(Optional.of(testPrompt));

        PromptResponse response = promptService.getCurrentPromptForAI("test-prompt");

        assertNotNull(response);
        assertEquals("test-prompt", response.getName());
        verify(promptRepository, times(1)).findByNameAndActive(anyString(), any());
    }
}
