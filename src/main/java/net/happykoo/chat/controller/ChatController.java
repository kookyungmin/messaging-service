package net.happykoo.chat.controller;

import lombok.RequiredArgsConstructor;
import net.happykoo.chat.dto.ChatRoomResponse;
import net.happykoo.chat.entity.Room;
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
    public ChatRoomResponse createChatRoom(@AuthenticationPrincipal CustomOAuth2User user, @RequestParam String title) {
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
    public List<ChatRoomResponse> getChatRoomList(@AuthenticationPrincipal CustomOAuth2User user) {
        return chatService.getChatRoomList(user.getMember());
    }
}
