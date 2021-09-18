package com.labs.Lab6.providers;

import com.labs.Lab6.entities.Chair;

import java.util.List;

public interface ChairProvider {
	void addOne();
	Chair getOne();
	List<Chair> getAll();
	void deleteOne();
	void updateOne();
}
