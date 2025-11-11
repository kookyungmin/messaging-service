package net.happykoo.chat.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.happykoo.chat.dto.ChatMessageDto;
import net.happykoo.chat.dto.ChatRoomDto;
import net.happykoo.chat.service.ChatService;
import net.happykoo.chat.vos.CustomOAuth2User;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
public class StompChatController {
    private final ChatService chatService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/chats/{roomId}")
    @SendTo("/sub/chats/{roomId}")
    public ChatMessageDto handleMessage(Principal principal, @DestinationVariable Long roomId, @Payload Map<String, String> payload) {
        CustomOAuth2User user = (CustomOAuth2User) ((AbstractAuthenticationToken) principal).getPrincipal();
        String text = payload.get("message");
        log.info("{}: {} sent {}", roomId, user.getName(), text);
        chatService.createMessage(user.getMember(), roomId,text);
        ChatRoomDto chatRoomDto = chatService.getChatRoom(roomId);
        simpMessagingTemplate.convertAndSend("/sub/chats/updates", chatRoomDto);
        return new ChatMessageDto(user.getMember().getName(), text);
    }
}
