package com.labs.Lab6.repositories;

import com.labs.Lab6.entities.Server;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ServerRepository extends CrudRepository<Server, Long> {
    List<Server> findAll();
    Server findOne(Long id);
}
