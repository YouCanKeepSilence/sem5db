package com.labs.Lab6.entities;

import javax.persistence.*;
import javax.persistence.Table;

@MappedSuperclass
@Table(name = "staff")
public abstract class Staff {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	protected Long id;

	@ManyToOne(optional = false)
	@JoinColumn(name = "location", nullable=false)
	protected Room location;

	@ManyToOne(optional = false)
	@JoinColumn(name = "manager", nullable=false)
	protected People manager;

	public Room getLocation() {
		return location;
	}

	public void setLocation(Room location) {
		this.location = location;
	}

	public People getManager() {
		return manager;
	}

	public void setManager(People manager) {
		this.manager = manager;
	}

	public Long getId() { return id;	}
	
	@Override
	public String toString() {
		return String.format("id = %d , [ %s ] , [ %s ]", id, location, manager);
	}
}
