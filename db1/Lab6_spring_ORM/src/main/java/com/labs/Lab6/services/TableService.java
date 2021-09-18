package com.labs.Lab6.services;

import com.labs.Lab6.entities.People;
import com.labs.Lab6.entities.Room;
import com.labs.Lab6.entities.Table;
import com.labs.Lab6.providers.TableProvider;
import com.labs.Lab6.repositories.PeopleRepository;
import com.labs.Lab6.repositories.RoomRepository;
import com.labs.Lab6.repositories.TableRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Scanner;


public class TableService implements TableProvider {

    @Autowired
    private TableRepository tableRepository;

    @Autowired
    private PeopleRepository peopleRepository;

    @Autowired
    private RoomRepository roomRepository;

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public void addOne() {
        System.out.println("*** add table ***");
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
        System.out.print("Enter the area : ");
        float area = in.nextFloat();
        System.out.print("Enter the folding (true | false)  : ");
        boolean folding = in.nextBoolean();
        Table table = new Table();
        table.setLocation(location);
        table.setManager(manager);
        table.setMaterial(material);
        table.setWeight(weight);
        table.setArea(area);
        table.setFolding(folding);
        em.persist(table);
    }

    @Override
    @Transactional
    public Table getOne() {
        Scanner in = new Scanner(System.in);
        this.getAll().forEach(System.out::println);
        System.out.print("Enter id of table to choose : ");
        long id = in.nextLong();
        return tableRepository.findOne(id);
    }

    @Override
    @Transactional
    public List<Table> getAll() {
        return this.tableRepository.findAll();
    }

    @Override
    @Transactional
    public void deleteOne() {
        Table table = this.getOne();
        tableRepository.delete(table.getId());
    }

    @Override
    @Transactional
    public void updateOne() {
        Scanner in = new Scanner(System.in);
        System.out.println("*** update table ***");
        Table updated = this.getOne();
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
        System.out.print("Enter new area (to skip enter '!'): ");
        String area = in.nextLine();
        if (!area.equals("!")) {
            updated.setArea(Float.parseFloat(area));
        }
        System.out.print("Enter new folding (true | false)  (to skip enter '!'): ");
        String folding = in.nextLine();
        if (!folding.equals("!")) {
            updated.setFolding(Boolean.parseBoolean(folding));
        }
        em.merge(updated);
    }
}
