package com.labs.Lab6.providers;

import com.labs.Lab6.entities.Server;

import java.util.List;

public interface ServerProvider {
    void addOne();
    Server getOne();
    List<Server> getAll();
    void deleteOne();
    void updateOne();
}
