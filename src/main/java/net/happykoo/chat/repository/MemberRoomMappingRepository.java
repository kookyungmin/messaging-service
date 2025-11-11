package net.happykoo.chat.repository;

import net.happykoo.chat.entity.MemberRoomMapping;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface MemberRoomMappingRepository extends CrudRepository<MemberRoomMapping, Long> {
    Boolean existsByMemberIdAndRoomId(Long memberId, Long roomId);
    Optional<MemberRoomMapping> findByMemberIdAndRoomId(Long memberId, Long roomId);
    void deleteByMemberIdAndRoomId(Long memberid, Long roomId);

    @Query("select mr from memberRoomMapping mr join fetch mr.room r where mr.member.id = :memberId")
    List<MemberRoomMapping> findAllByMemberId(Long memberId);
}
