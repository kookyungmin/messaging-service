package net.happykoo.chat.controller;

import lombok.RequiredArgsConstructor;
import net.happykoo.chat.dto.ChatRoomDto;
import net.happykoo.chat.dto.MemberDto;
import net.happykoo.chat.service.ChatService;
import net.happykoo.chat.service.CustomUserDetailsService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/consultants")
@RequiredArgsConstructor
public class ConsultantController {
    private final CustomUserDetailsService customUserDetailsService;
    private final ChatService chatService;

    @ResponseBody
    @PostMapping
    public MemberDto saveMember(@RequestBody MemberDto memberDto) {
        return customUserDetailsService.saveMember(memberDto);
    }

    @GetMapping
    public String index() {
        return "consultants/index.html";
    }

    @ResponseBody
    @GetMapping("chats")
    public Page<ChatRoomDto> getAllChatRoomList(Pageable pageable) {
        return chatService.getAllChatRoomList(pageable);
    }
}
