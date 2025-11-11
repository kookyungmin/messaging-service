package net.happykoo.chat.dto;

import net.happykoo.chat.entity.Message;

public record ChatMessageDto(
    String sender,
    String message
) {
    public static ChatMessageDto from(Message message) {
        return new ChatMessageDto(message.getMember().getName(), message.getText());
    }
}
