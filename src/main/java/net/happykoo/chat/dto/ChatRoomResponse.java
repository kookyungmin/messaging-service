package net.happykoo.chat.dto;

import net.happykoo.chat.entity.Room;

import java.time.LocalDateTime;

public record ChatRoomResponse(
    Long id,
    String title,
    Integer memberCount,
    LocalDateTime createdAt
) {
    public static ChatRoomResponse from(Room room) {
        return new ChatRoomResponse(room.getId(), room.getTitle(), room.getMemberSet().size(), room.getCreatedAt());
    }
}
