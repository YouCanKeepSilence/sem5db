package com.labs.Lab6.repositories;

import com.labs.Lab6.entities.Table;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TableRepository extends CrudRepository<Table, Long> {
    List<Table> findAll();
    Table findOne(Long id);
}
