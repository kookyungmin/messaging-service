package net.happykoo.chat.dto;

import net.happykoo.chat.entity.Message;

public record ChatMessage(
    String sender,
    String message
) {
    public static ChatMessage from(Message message) {
        return new ChatMessage(message.getMember().getName(), message.getText());
    }
}
