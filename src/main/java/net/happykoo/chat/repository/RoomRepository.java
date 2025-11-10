package net.happykoo.chat.repository;

import net.happykoo.chat.entity.Room;
import org.springframework.data.repository.CrudRepository;

public interface RoomRepository extends CrudRepository<Room, Long> {
}
