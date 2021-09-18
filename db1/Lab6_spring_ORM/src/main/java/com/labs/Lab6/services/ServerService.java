package com.labs.Lab6.services;

import com.labs.Lab6.entities.People;
import com.labs.Lab6.entities.Room;
import com.labs.Lab6.entities.Server;
import com.labs.Lab6.providers.ServerProvider;
import com.labs.Lab6.repositories.PeopleRepository;
import com.labs.Lab6.repositories.RoomRepository;
import com.labs.Lab6.repositories.ServerRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Scanner;

public class ServerService implements ServerProvider {
    @PersistenceContext
    private EntityManager em;

    @Autowired
    private ServerRepository serverRepository;

    @Autowired
    private PeopleRepository peopleRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Override
    @Transactional
    public void addOne() {
        System.out.println("*** add server ***");
        Scanner in = new Scanner(System.in);
        Server server = new Server();
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
        System.out.print("Enter the count of details (to skip enter '!'): ");
        String details = in.nextLine();
        if (!details.equals("!")) {
            server.setCountOfDetail(Integer.parseInt(details));
        }

        System.out.print("Enter the storage capacity : ");
        float storage = in.nextFloat();
        //  костылим как можем...
        System.out.print("Enter the count of threads : ");
        int threads = in.nextInt();

        in.nextLine();
        System.out.print("Enter the address : ");
        String address = in.nextLine();
        server.setManager(manager);
        server.setLocation(location);
        server.setStorage(storage);
        server.setThreads(threads);
        server.setAdress(address);
        em.persist(server);
    }

    @Override
    @Transactional
    public Server getOne() {
        Scanner in = new Scanner(System.in);
        this.getAll().forEach(System.out::println);
        System.out.print("Enter id of server to choose : ");
        long id = in.nextLong();
        return serverRepository.findOne(id);
    }

    @Override
    @Transactional
    public List<Server> getAll() {
        return serverRepository.findAll();
    }

    @Override
    @Transactional
    public void deleteOne() {
        Server delete = this.getOne();
        serverRepository.delete(delete.getId());
    }

    @Override
    @Transactional
    public void updateOne() {
        Scanner in = new Scanner(System.in);
        System.out.println("*** update server ***");
        Server updated = this.getOne();
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

        System.out.print("Enter new count of details (to skip enter '!'): ");
        String count = in.nextLine();
        if (!count.equals("!")) {
            updated.setCountOfDetail(Integer.parseInt(count));
        }
        System.out.print("Enter new storage capacity (to skip enter '!'): ");
        String storage = in.nextLine();
        if (!storage.equals("!")) {
            updated.setStorage(Float.parseFloat(storage));
        }
        System.out.print("Enter new count of threads (to skip enter '!'): ");
        String threads = in.nextLine();
        if (!threads.equals("!")) {
            updated.setThreads(Integer.parseInt(threads));
        }

        System.out.print("Enter new address (to skip enter '!'): ");
        String address = in.nextLine();
        if (!address.equals("!")) {
            updated.setAdress(address);
        }
        em.merge(updated);
    }
}
