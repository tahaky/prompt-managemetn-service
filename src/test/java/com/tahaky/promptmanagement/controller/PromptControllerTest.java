package com.tahaky.promptmanagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tahaky.promptmanagement.dto.PromptRequest;
import com.tahaky.promptmanagement.dto.PromptResponse;
import com.tahaky.promptmanagement.dto.UpdatePromptRequest;
import com.tahaky.promptmanagement.service.PromptService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PromptController.class)
class PromptControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PromptService promptService;

    private PromptResponse testResponse;
    private PromptRequest testRequest;

    @BeforeEach
    void setUp() {
        testResponse = PromptResponse.builder()
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
    void createPrompt_Success() throws Exception {
        when(promptService.createPrompt(any(PromptRequest.class))).thenReturn(testResponse);

        mockMvc.perform(post("/api/prompts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("test-prompt"))
                .andExpect(jsonPath("$.content").value("Test content"));
    }

    @Test
    void getPromptByName_Success() throws Exception {
        when(promptService.getPromptByName(anyString())).thenReturn(testResponse);

        mockMvc.perform(get("/api/prompts/test-prompt"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("test-prompt"))
                .andExpect(jsonPath("$.content").value("Test content"));
    }

    @Test
    void updatePrompt_Success() throws Exception {
        UpdatePromptRequest updateRequest = UpdatePromptRequest.builder()
                .content("Updated content")
                .build();

        PromptResponse updatedResponse = PromptResponse.builder()
                .id(1L)
                .name("test-prompt")
                .content("Updated content")
                .category("test")
                .version(2)
                .active(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        when(promptService.updatePrompt(anyString(), any(UpdatePromptRequest.class)))
                .thenReturn(updatedResponse);

        mockMvc.perform(put("/api/prompts/test-prompt")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.version").value(2))
                .andExpect(jsonPath("$.content").value("Updated content"));
    }

    @Test
    void getAllActivePrompts_Success() throws Exception {
        when(promptService.getAllActivePrompts()).thenReturn(List.of(testResponse));

        mockMvc.perform(get("/api/prompts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("test-prompt"));
    }

    @Test
    void deletePrompt_Success() throws Exception {
        mockMvc.perform(delete("/api/prompts/test-prompt"))
                .andExpect(status().isNoContent());
    }
}
