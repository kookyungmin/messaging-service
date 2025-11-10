package net.happykoo.chat.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "room")
@Table(name = "chat_room")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class Room {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private Long id;

    private String title;

    @OneToMany(mappedBy = "room")
    @Builder.Default
    private Set<MemberRoomMapping> memberSet = new HashSet<>();
    private LocalDateTime createdAt;

    public void addMember(Member member) {
        memberSet.add(MemberRoomMapping.builder()
                .room(this)
                .member(member)
                .build());
    }
}
