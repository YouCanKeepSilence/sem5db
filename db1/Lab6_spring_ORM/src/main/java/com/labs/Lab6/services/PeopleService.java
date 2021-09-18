package com.labs.Lab6.services;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import com.labs.Lab6.providers.PeopleProvider;
import com.labs.Lab6.repositories.PeopleRepository;

import com.labs.Lab6.entities.People;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Scanner;

public class PeopleService implements PeopleProvider {

	@Autowired
	private PeopleRepository peopleRepository;

	@PersistenceContext
	private EntityManager em;

	@Override
	@Transactional
	public void addOne() {
		Scanner in = new Scanner(System.in);
		People newMan = new People();
		System.out.print("Enter the first name : ");
		String firstName = in.nextLine();
		System.out.print("Enter the last name : ");
		String lastName = in.nextLine();
		System.out.print("Enter the patronymic (to skip enter '!'): ");
		String patr = in.nextLine();
		if (patr.equals("!")) {
			patr = null;
		}
		System.out.print("Enter the phone : ");
		String phone = in.nextLine();
		System.out.print("Enter the comment (to skip enter '!'): ");
		String comment = in.nextLine();
		if (comment.equals("!")) {
			comment = null;
		}
		newMan.setFirstName(firstName);
		newMan.setLastName(lastName);
		newMan.setPhone(phone);
		newMan.setPatronymic(patr);
		newMan.setComment(comment);
		em.persist(newMan);
	}

	@Override
	@Transactional
	public List<People> getAll() {
		return peopleRepository.findAll();
	}

	@Override
	@Transactional
	public People getOne() {
		Scanner in = new Scanner(System.in);
		this.getAll().forEach(System.out::println);
		System.out.print("Enter id of manager to choose : ");
		long id = in.nextLong();
		return peopleRepository.findOne(id);
	}

	@Override
	@Transactional
	public void deleteOne() {
		People man = this.getOne();
		peopleRepository.delete(man.getId());
	}

	@Override
	@Transactional
	public void updateOne() {
		Scanner in = new Scanner(System.in);
		People updated = this.getOne();
		System.out.print("Enter new first name (to skip enter '!'): ");
		String firstName = in.nextLine();
		if (!firstName.equals("!")) {
			updated.setFirstName(firstName);
		}
		System.out.print("Enter new last name (to skip enter '!'): ");
		String lastName = in.nextLine();
		if (!lastName.equals("!")) {
			updated.setLastName(lastName);
		}
		System.out.print("Enter new patronymic (to skip enter '!'): ");
		String patr = in.nextLine();
		if (!patr.equals("!")) {
			updated.setPatronymic(patr);
		}
		System.out.print("Enter new phone (to skip enter '!'): ");
		String phone = in.nextLine();
		if (!phone.equals("!")) {
			updated.setPhone(phone);
		}
		System.out.print("Enter new comment (to skip enter '!'): ");
		String comment = in.nextLine();
		if (!comment.equals("!")) {
			updated.setComment(comment);
		}
		em.merge(updated);
	}

	@Override
	@Transactional
	public void showAllStaff() {
		People man = this.getOne();
		man.getChairs().forEach(System.out::println);
		man.getTables().forEach(System.out::println);
		man.getComputers().forEach(System.out::println);
		man.getServers().forEach(System.out::println);
	}

	@Override
	@Transactional
	public void showOneStaff() {
		Scanner in = new Scanner(System.in);
		People man = this.getOne();
		System.out.println("1) Show all chairs");
		System.out.println("2) Show all tables");
		System.out.println("3) Show all computers");
		System.out.println("4) Show all servers");
		int sub = in.nextInt();
		if (sub < 0 || sub > 4) {
			System.out.println("Incorrect value");
			return;
		}
		switch (sub) {
			case 1:
				man.getChairs().forEach(System.out::println);
				break;
			case 2:
				man.getTables().forEach(System.out::println);
				break;
			case 3:
				man.getComputers().forEach(System.out::println);
				break;
			case 4:
				man.getServers().forEach(System.out::println);
				break;
		}

	}

	@Override
	@Transactional
	public void search() {
		Scanner in = new Scanner(System.in);
		System.out.println("1) Find all staff for manager");
		System.out.println("2) Find one staff for manager");
		int subChoseFindManager = in.nextInt();
		if (subChoseFindManager < 1 || subChoseFindManager > 2) {
			System.out.println("Incorrect value");
			return;
		}
		switch (subChoseFindManager) {
			case 1:
				this.showAllStaff();
				break;
			case 2:
				this.showOneStaff();
				break;
		}
	}
}
