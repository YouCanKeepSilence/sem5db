package com.labs.Lab6.entities;


import javax.persistence.*;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "peoples")
public class People {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id" , insertable = false, updatable = false)
	private Long id;

	@Column(name="firstname", nullable = false, length = 64)
	private String firstName;
	
	@Column(name = "lastname", nullable = false, length = 64)
	private String lastName;
	
	@Column(name = "patronymic" , length = 64)
	private String patronymic;
	
	@Column(name = "comment" , length = 256)
	private String comment;
	
	@Column(name = "phone" , nullable = false , length = 32)
	private String phone;

	@OneToMany(mappedBy="manager", targetEntity=Chair.class, fetch=FetchType.LAZY)
	private	List<Chair> chairs = new ArrayList<>();

	@OneToMany(mappedBy="manager", targetEntity= com.labs.Lab6.entities.Table.class, fetch=FetchType.LAZY)
	private	List<com.labs.Lab6.entities.Table> tables = new ArrayList<>();

	@OneToMany(mappedBy="manager", targetEntity= Computer.class, fetch=FetchType.LAZY)
	private	List<Computer> computers = new ArrayList<>();

	@OneToMany(mappedBy="manager", targetEntity= Server.class, fetch=FetchType.LAZY)
	private	List<Server> servers = new ArrayList<>();
	
	public String getFirstName() {
		return this.firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPatronymic() {
		return patronymic;
	}

	public void setPatronymic(String patronymic) {
		this.patronymic = patronymic;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@Override
	public String toString() {
		return String.format("Manager id (%d), %s %s %s, %s ,%s", getId(), lastName, firstName, patronymic, phone, comment);
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public List<Chair> getChairs() {
		return chairs;
	}

	public void setChairs(List<Chair> chairs) {
		this.chairs = chairs;
	}

	public Long getId() {
		return id;
	}

	public List<com.labs.Lab6.entities.Table> getTables() {
		return tables;
	}

	public void setTables(List<com.labs.Lab6.entities.Table> tables) {
		this.tables = tables;
	}

	public List<Computer> getComputers() {
		return computers;
	}

	public void setComputers(List<Computer> computers) {
		this.computers = computers;
	}

	public List<Server> getServers() {
		return servers;
	}

	public void setServers(List<Server> servers) {
		this.servers = servers;
	}
}
