package com.labs.Lab6.services;

import java.util.List;
import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import com.labs.Lab6.entities.People;
import com.labs.Lab6.entities.Room;
import com.labs.Lab6.providers.ChairProvider;
import com.labs.Lab6.repositories.PeopleRepository;
import com.labs.Lab6.repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;

import com.labs.Lab6.entities.Chair;
import com.labs.Lab6.repositories.ChairRepository;

public class ChairService implements ChairProvider {

	@Autowired
	private ChairRepository chairRepository;

	@Autowired
	private PeopleRepository peopleRepository;

	@Autowired
	private RoomRepository roomRepository;

//	TODO how use it??
//	private final PeopleProvider peopleProvider = new PeopleService();

//	private final RoomProvider roomProvider = new RoomService();

	@PersistenceContext
	private EntityManager em;
	
	@Override
	public List<Chair> getAll() {
		return chairRepository.findAll();
	}

	@Override
	public Chair getOne() {
		Scanner in = new Scanner(System.in);
		this.getAll().forEach(System.out::println);
		System.out.print("Enter id of chair to choose : ");
		long id = in.nextLong();
		return chairRepository.findOne(id);
	}

	@Override
	@Transactional
	public void addOne() {
		System.out.println("*** add chair ***");
		Scanner in = new Scanner(System.in);
		System.out.println("Choosing room : ");
		roomRepository.findAll().forEach(System.out::println);
		System.out.println("Choose id of room : ");
		long roomId = in.nextLong();
		// иначе не работает
		in.nextLine();
		Room location = roomRepository.findOne(roomId);

		System.out.println("Choosing manager : ");
		peopleRepository.findAll().forEach(System.out::println);
		System.out.println("Choose id of manager : ");
		long manId = in.nextLong();
		// иначе не работает
		in.nextLine();
		People manager = peopleRepository.findOne(manId);

		System.out.print("Enter the material : ");
		String material = in.nextLine();
		System.out.print("Enter the weight : ");
		float weight = in.nextFloat();
		System.out.print("Enter the height : ");
		float height = in.nextFloat();
		System.out.print("Enter the armrest (true | false)  : ");
		boolean armrest = in.nextBoolean();
		Chair chair = new Chair();
		chair.setLocation(location);
		chair.setManager(manager);
		chair.setMaterial(material);
		chair.setWeight(weight);
		chair.setHeightOfBack(height);
		chair.setArmrest(armrest);
		em.persist(chair);
	}

	@Override
	@Transactional
	public void deleteOne() {
		System.out.println("*** delete chair ***");
		Chair deleted = this.getOne();
		chairRepository.delete(deleted.getId());
	}

	@Override
	@Transactional
	public void updateOne() {
		Scanner in = new Scanner(System.in);
		System.out.println("*** update chair ***");
		Chair updated = this.getOne();
		peopleRepository.findAll().forEach(System.out::println);
		System.out.print("Enter new  manager (to skip enter '!'): ");
		String manId = in.nextLine();
		if (!manId.equals("!")) {
			long id = Long.parseLong(manId);
			updated.setManager(peopleRepository.findOne(id));
		}
		roomRepository.findAll().forEach(System.out::println);
		System.out.print("Enter new  room (to skip enter '!'): ");
		String roomId = in.nextLine();
		if (!roomId.equals("!")) {
			long id = Long.parseLong(roomId);
			updated.setLocation(roomRepository.findOne(id));
		}
		System.out.print("Enter new material (to skip enter '!'): ");
		String material = in.nextLine();
		if (!material.equals("!")) {
			updated.setMaterial(material);
		}
		System.out.print("Enter new weight (to skip enter '!'): ");
		String weight = in.nextLine();
		if (!weight.equals("!")) {
			updated.setWeight(Float.parseFloat(weight));
		}
		System.out.print("Enter new height (to skip enter '!'): ");
		String height = in.nextLine();
		if (!height.equals("!")) {
			updated.setHeightOfBack(Float.parseFloat(height));
		}
		System.out.print("Enter new armrest (true | false) (to skip enter '!'): ");
		String armrest = in.nextLine();
		if (!armrest.equals("!")) {
			updated.setArmrest(Boolean.parseBoolean(armrest));
		}
		em.merge(updated);
	}

}
