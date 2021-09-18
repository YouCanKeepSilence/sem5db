package com.labs.Lab6.repositories;

import com.labs.Lab6.entities.Computer;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ComputerRepository extends CrudRepository<Computer, Long> {
    List<Computer> findAll();
    Computer findOne(Long id);
}
