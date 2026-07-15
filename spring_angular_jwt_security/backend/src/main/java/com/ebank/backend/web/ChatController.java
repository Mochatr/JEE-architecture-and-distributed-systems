package com.ebank.backend.web;

import com.ebank.backend.chatbot.ChatService;
import com.ebank.backend.dtos.ChatRequestDTO;
import com.ebank.backend.dtos.ChatResponseDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Tag(name = "Chatbot", description = "Assistant bancaire basé sur RAG")
public class ChatController {

    private final ChatService chatService;

    @PostMapping("/chat")
    public ChatResponseDTO chat(@Valid @RequestBody ChatRequestDTO request) {
        return new ChatResponseDTO(chatService.ask(request.getQuestion()));
    }
}
