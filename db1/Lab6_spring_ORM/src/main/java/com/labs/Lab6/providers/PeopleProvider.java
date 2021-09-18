package com.labs.Lab6.providers;

import com.labs.Lab6.entities.People;

import java.util.List;

public interface PeopleProvider {
	void addOne();
	People getOne();
	List<People> getAll();
	void deleteOne();
	void updateOne();
	void showAllStaff();
	void showOneStaff();
	void search();
}
