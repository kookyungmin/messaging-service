package net.happykoo.chat.repository;

import net.happykoo.chat.entity.MemberRoomMapping;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MemberRoomMappingRepository extends CrudRepository<MemberRoomMapping, Long> {
    Boolean existsByMemberIdAndRoomId(Long memberId, Long roomId);
    void deleteByMemberIdAndRoomId(Long memberid, Long roomId);

    @Query("select mr from memberRoomMapping mr join fetch mr.room r where mr.member.id = :memberId")
    List<MemberRoomMapping> findAllByMemberId(Long memberId);
}
