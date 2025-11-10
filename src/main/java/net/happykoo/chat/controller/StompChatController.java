package net.happykoo.chat.controller;

import lombok.extern.slf4j.Slf4j;
import net.happykoo.chat.dto.ChatMessage;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.Map;

@Slf4j
@Controller
public class StompChatController {

    @MessageMapping("/chats/{roomId}")
    @SendTo("/sub/chats/{roomId}")
    public ChatMessage handleMessage(@AuthenticationPrincipal Principal principal, @DestinationVariable Long roomId, @Payload Map<String, String> payload) {
        log.info("{}: {} sent {}", roomId, principal.getName(), payload.get("message"));
        return new ChatMessage(principal.getName(), payload.get("message"));
    }
}
