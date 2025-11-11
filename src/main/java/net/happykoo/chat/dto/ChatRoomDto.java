package net.happykoo.chat.dto;

import net.happykoo.chat.entity.Room;

import java.time.LocalDateTime;

public record ChatRoomDto(
    Long id,
    String title,
    Integer memberCount,
    boolean hasNewMessage,
    LocalDateTime createdAt
) {
    public static ChatRoomDto from(Room room) {
        return from(room, false);
    }

    public static ChatRoomDto from(Room room, boolean hasNewMessage) {
        return new ChatRoomDto(room.getId(), room.getTitle(), room.getMemberSet().size(), hasNewMessage, room.getCreatedAt());
    }
}
