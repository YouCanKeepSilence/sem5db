package com.labs.Lab6.providers;

import com.labs.Lab6.entities.Table;

import java.util.List;

public interface TableProvider {
    void addOne();
    Table getOne();
    List<Table> getAll();
    void deleteOne();
    void updateOne();
}
