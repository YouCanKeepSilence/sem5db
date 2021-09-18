package com.labs.Lab6.services;

import com.labs.Lab6.entities.Room;
import com.labs.Lab6.providers.RoomProvider;
import com.labs.Lab6.repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Scanner;

public class RoomService implements RoomProvider {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private EntityManager em;

    @Override
    @Transactional
    public void addOne() {
        Scanner in = new Scanner(System.in);
        System.out.print("Enter the number of room : ");
        String number = in.nextLine();
        System.out.print("Enter the description (enter '!' to skip) : ");
        String descr = in.nextLine();
        if (descr.equals("!")) {
            descr = null;
        }
        Room newRoow = new Room();
        newRoow.setAdress(number);
        newRoow.setDescription(descr);
        em.persist(newRoow);
    }

    @Override
    @Transactional
    public Room getOne() {
        Scanner in = new Scanner(System.in);
        this.getAll().forEach(r -> {
            System.out.println(r);
        });
        System.out.print("Enter the id of room to choose : ");
        long id = in.nextLong();
        return roomRepository.findOne(id);
    }

    @Override
    @Transactional
    public List<Room> getAll() {
        return roomRepository.findAll();
    }

    @Override
    @Transactional
    public void deleteOne() {
        Room room = this.getOne();
        roomRepository.delete(room.getId());
    }

    @Override
    @Transactional
    public void updateOne() {
        Scanner in = new Scanner(System.in);
        Room updated = this.getOne();
        System.out.print("Enter new number of room (enter '!' to skip): ");
        String number = in.nextLine();
        if (!number.equals("!")) {
            updated.setAdress(number);
        }
        System.out.print("Enter new description (enter '!' to skip): ");
        String descr = in.nextLine();
        if (!descr.equals("!")) {
            updated.setDescription(descr);
        }
        em.merge(updated);

    }

    @Override
    @Transactional
    public void showAllStaff() {
        Room room = this.getOne();
        room.getChairs().forEach(System.out::println);
        room.getTables().forEach(System.out::println);
        room.getComputers().forEach(System.out::println);
        room.getServers().forEach(System.out::println);
    }

    @Override
    @Transactional
    public void showOneStaff() {
        Scanner in = new Scanner(System.in);
        Room room = this.getOne();
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
                room.getChairs().forEach(System.out::println);
                break;
            case 2:
                room.getTables().forEach(System.out::println);
                break;
            case 3:
                room.getComputers().forEach(System.out::println);
                break;
            case 4:
                room.getServers().forEach(System.out::println);
                break;
        }

    }

    @Override
    @Transactional
    public void search() {
        Scanner in = new Scanner(System.in);
        System.out.println("1) Find all staff for room");
        System.out.println("2) Find one staff for room");
        int subChoseFindRoom = in.nextInt();
        if (subChoseFindRoom < 1 || subChoseFindRoom > 2) {
            System.out.println("Incorrect value");
            return;
        }
        switch (subChoseFindRoom) {
            case 1:
                this.showAllStaff();
                break;
            case 2:
                this.showOneStaff();
                break;
        }
    }
}
