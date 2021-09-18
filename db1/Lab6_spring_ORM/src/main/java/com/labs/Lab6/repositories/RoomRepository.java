package com.labs.Lab6.repositories;

import com.labs.Lab6.entities.Room;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RoomRepository extends CrudRepository<Room, Long> {
    List<Room> findAll();
    Room findOne(Long id);
}
