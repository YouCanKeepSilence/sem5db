package com.labs.Lab6.providers;

import com.labs.Lab6.entities.Room;

import java.util.List;

public interface RoomProvider {
    void addOne();
    Room getOne();
    List<Room> getAll();
    void deleteOne();
    void updateOne();
    void showAllStaff();
    void showOneStaff();
    void search();
}
