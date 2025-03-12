package org.isp.controller;

import org.isp.model.ChatMessage;
import org.isp.service.ChatSupportService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/chat-support")
public class ChatSupportController {

    private final ChatSupportService chatSupportService;

    public ChatSupportController(ChatSupportService chatSupportService) {
        this.chatSupportService = chatSupportService;
    }

    // Get all chat messages
    @GetMapping
    public ResponseEntity<List<ChatMessage>> getAllMessages() {
        List<ChatMessage> messages = chatSupportService.getAllMessages();
        return ResponseEntity.ok(messages);
    }

    // Get a chat message by ID
    @GetMapping("/{id}")
    public ResponseEntity<ChatMessage> getMessageById(@PathVariable Long id) {
        ChatMessage message = chatSupportService.getMessageById(id);
        return ResponseEntity.ok(message);
    }

    // Create a new chat message
    @PostMapping
    public ResponseEntity<ChatMessage> createMessage(@Valid @RequestBody ChatMessage message) {
        ChatMessage savedMessage = chatSupportService.saveMessage(message);
        return new ResponseEntity<>(savedMessage, HttpStatus.CREATED);
    }

    // Delete a chat message by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMessage(@PathVariable Long id) {
        chatSupportService.deleteMessage(id);
        return ResponseEntity.noContent().build();
    }

    // Search messages by sender
    @GetMapping("/sender/{sender}")
    public ResponseEntity<List<ChatMessage>> getMessagesBySender(@PathVariable String sender) {
        List<ChatMessage> messages = chatSupportService.findMessagesBySender(sender);
        return ResponseEntity.ok(messages);
    }

    // Search messages by content containing a keyword
    @GetMapping("/search")
    public ResponseEntity<List<ChatMessage>> searchMessages(@RequestParam String keyword) {
        List<ChatMessage> messages = chatSupportService.findMessagesByContentContaining(keyword);
        return ResponseEntity.ok(messages);
    }
}