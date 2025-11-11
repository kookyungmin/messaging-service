package net.happykoo.chat.controller;

import lombok.RequiredArgsConstructor;
import net.happykoo.chat.dto.ChatMessageDto;
import net.happykoo.chat.dto.ChatRoomDto;
import net.happykoo.chat.service.ChatService;
import net.happykoo.chat.vos.CustomOAuth2User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chats")
@RequiredArgsConstructor
public class ChatController {
    private final ChatService chatService;

    @PostMapping
    public ChatRoomDto createChatRoom(@AuthenticationPrincipal CustomOAuth2User user, @RequestParam String title) {
        return chatService.createChatRoom(user.getMember(), title);
    }

    @PostMapping("{roomId}")
    public Boolean joinChatRoom(@AuthenticationPrincipal CustomOAuth2User user, @PathVariable Long roomId) {
        return chatService.joinChatRoom(user.getMember(), roomId);
    }

    @DeleteMapping("/{roomId}")
    public Boolean leaveChatRoom(@AuthenticationPrincipal CustomOAuth2User user, @PathVariable Long roomId) {
        return chatService.leaveChatRoom(user.getMember(), roomId);
    }

    @GetMapping
    public List<ChatRoomDto> getChatRoomList(@AuthenticationPrincipal CustomOAuth2User user) {
        return chatService.getChatRoomListByMember(user.getMember());
    }

    @GetMapping("/{roomId}/messages")
    public List<ChatMessageDto> getMessageList(@PathVariable Long roomId) {
        return chatService.getMassageByRoomId(roomId);
    }
}
