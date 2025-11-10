package net.happykoo.chat.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.happykoo.chat.dto.ChatMessage;
import net.happykoo.chat.dto.ChatRoomResponse;
import net.happykoo.chat.entity.Member;
import net.happykoo.chat.entity.MemberRoomMapping;
import net.happykoo.chat.entity.Message;
import net.happykoo.chat.entity.Room;
import net.happykoo.chat.repository.MemberRoomMappingRepository;
import net.happykoo.chat.repository.MessageRepository;
import net.happykoo.chat.repository.RoomRepository;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatService {
    private final RoomRepository roomRepository;
    private final MemberRoomMappingRepository memberRoomMappingRepository;
    private final MessageRepository messageRepository;

    public ChatRoomResponse createChatRoom(Member member, String title) {
        Room room = Room.builder()
                .title(title)
                .createdAt(LocalDateTime.now())
                .build();

        roomRepository.save(room);

        createChatRoom(member, room);
        room.addMember(member);

        return ChatRoomResponse.from(room);
    }

    public Boolean joinChatRoom(Member member, Long roomId) {
        if (memberRoomMappingRepository.existsByMemberIdAndRoomId(member.getId(), roomId)) {
            log.info("이미 참여한 채팅방입니다.");
            MemberRoomMapping mapping = memberRoomMappingRepository.findByMemberIdAndRoomId(member.getId(), roomId)
                    .orElseThrow(IllegalArgumentException::new);

            mapping.updateLastCheckedAt();
            memberRoomMappingRepository.save(mapping);
            return false;
        }

        Room room = roomRepository.findById(roomId)
                .orElseThrow(IllegalArgumentException::new);
        createChatRoom(member, room);
        return true;
    }

    @Transactional
    public Boolean leaveChatRoom(Member member, Long roomId) {
        if (!memberRoomMappingRepository.existsByMemberIdAndRoomId(member.getId(), roomId)) {
            log.info("참여하지 않은 방입니다.");
            return false;
        }
        memberRoomMappingRepository.deleteByMemberIdAndRoomId(member.getId(), roomId);
        return true;
    }

    @Transactional
    public List<ChatRoomResponse> getChatRoomList(Member member) {
        List<MemberRoomMapping> memberRoomMappings = memberRoomMappingRepository.findAllByMemberId(member.getId());
        return memberRoomMappings.stream()
                .map(mapping -> {
                    Room room = mapping.getRoom();
                    boolean hasNewMessage = messageRepository.existsByRoomIdAndCreatedAtAfter(mapping.getRoom().getId(), mapping.getLastCheckedAt());
                    return ChatRoomResponse.from(room, hasNewMessage);
                })
                .toList();
    }

    public ChatMessage createMessage(Member member, Long roomId, String text) {
        Room room = roomRepository.findById(roomId).orElseThrow(IllegalArgumentException::new);
        Message message = Message.builder()
                .member(member)
                .room(room)
                .text(text)
                .createdAt(LocalDateTime.now())
                .build();
        messageRepository.save(message);
        return ChatMessage.from(message);
    }

    public List<ChatMessage> getMassageByRoomId(Long roomId) {
        return messageRepository.findAllByRoomId(roomId)
                .stream()
                .map(ChatMessage::from)
                .toList();
    }

    private void createChatRoom(Member member, Room room) {
        MemberRoomMapping mapping = MemberRoomMapping.builder()
                .room(room)
                .member(member)
                .lastCheckedAt(LocalDateTime.now())
                .build();
        memberRoomMappingRepository.save(mapping);
    }
}
