package org.isp.repository;

import org.isp.model.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatSupportRepository extends JpaRepository<ChatMessage, Long> {
    // Custom query to find messages by sender
    List<ChatMessage> findBySender(String sender);

    // Custom query to find messages containing a specific keyword
    List<ChatMessage> findByContentContaining(String keyword);
}