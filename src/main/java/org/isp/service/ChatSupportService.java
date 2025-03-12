package org.isp.service;

import org.isp.exception.ResourceNotFoundException;
import org.isp.model.ChatMessage;
import org.isp.repository.ChatSupportRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatSupportService {

    private final ChatSupportRepository chatMessageRepository;

    public ChatSupportService(ChatSupportRepository chatMessageRepository) {
        this.chatMessageRepository = chatMessageRepository;
    }

    // Get all chat messages
    public List<ChatMessage> getAllMessages() {
        return chatMessageRepository.findAll();
    }

    // Get a chat message by ID
    public ChatMessage getMessageById(Long id) {
        return chatMessageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Message not found with id: " + id));
    }

    // Save a new chat message
    public ChatMessage saveMessage(ChatMessage message) {
        return chatMessageRepository.save(message);
    }

    // Delete a chat message by ID
    public void deleteMessage(Long id) {
        chatMessageRepository.deleteById(id);
    }

    // Custom query: Find messages by sender
    public List<ChatMessage> findMessagesBySender(String sender) {
        return chatMessageRepository.findBySender(sender);
    }

    // Custom query: Find messages containing a keyword
    public List<ChatMessage> findMessagesByContentContaining(String keyword) {
        return chatMessageRepository.findByContentContaining(keyword);
    }
}