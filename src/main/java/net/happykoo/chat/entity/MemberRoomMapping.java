package net.happykoo.chat.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "memberRoomMapping")
@Table(name = "chat_member_room_mapping")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class MemberRoomMapping {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_room_mapping_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;
}
