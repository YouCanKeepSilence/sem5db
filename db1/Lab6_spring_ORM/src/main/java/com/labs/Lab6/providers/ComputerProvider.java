package com.labs.Lab6.providers;

import com.labs.Lab6.entities.Computer;

import java.util.List;

public interface ComputerProvider {
    void addOne();
    Computer getOne();
    List<Computer> getAll();
    void deleteOne();
    void updateOne();
}
