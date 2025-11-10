package net.happykoo.chat.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.happykoo.chat.dto.ChatMessage;
import net.happykoo.chat.service.ChatService;
import net.happykoo.chat.vos.CustomOAuth2User;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
public class StompChatController {
    private final ChatService chatService;

    @MessageMapping("/chats/{roomId}")
    @SendTo("/sub/chats/{roomId}")
    public ChatMessage handleMessage(Principal principal, @DestinationVariable Long roomId, @Payload Map<String, String> payload) {
        CustomOAuth2User user = (CustomOAuth2User) ((OAuth2AuthenticationToken) principal).getPrincipal();
        String text = payload.get("message");
        log.info("{}: {} sent {}", roomId, user.getName(), text);
        chatService.createMessage(user.getMember(), roomId,text);
        return new ChatMessage(user.getMember().getName(), text);
    }
}
