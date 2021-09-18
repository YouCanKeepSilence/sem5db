package com.labs.Lab6.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.labs.Lab6.entities.Chair;

import java.util.List;

@Repository
public interface ChairRepository extends CrudRepository<Chair, Long> {
	List<Chair> findAll();
	Chair findOne(Long id);
}
