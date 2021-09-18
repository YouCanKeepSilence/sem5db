package com.labs.Lab6.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.labs.Lab6.entities.People;

import java.util.List;


@Repository
public interface PeopleRepository extends CrudRepository<People, Long> {

	People findOne(Long id);
//	People findOneByFirstName(String name);
	List<People> findAll();

}
