package com.labs.Lab6.services;

import com.labs.Lab6.entities.Computer;
import com.labs.Lab6.entities.People;
import com.labs.Lab6.entities.Room;
import com.labs.Lab6.providers.ComputerProvider;
import com.labs.Lab6.repositories.ComputerRepository;
import com.labs.Lab6.repositories.PeopleRepository;
import com.labs.Lab6.repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Scanner;

public class ComputerService implements ComputerProvider {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private ComputerRepository computerRepository;
    @Autowired
    private PeopleRepository peopleRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Override
    @Transactional
    public void addOne() {
        System.out.println("*** add computer ***");
        Scanner in = new Scanner(System.in);
        Computer computer = new Computer();
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
            computer.setCountOfDetail(Integer.parseInt(details));
        }

        System.out.print("Enter the power : ");
        float power = in.nextFloat();
        //  костылим как можем...
        in.nextLine();
        System.out.print("Enter the operation system : ");
        String os = in.nextLine();
        computer.setManager(manager);
        computer.setLocation(location);
        computer.setOs(os);
        computer.setPower(power);
        em.persist(computer);
    }

    @Override
    @Transactional
    public Computer getOne() {
        Scanner in = new Scanner(System.in);
        this.getAll().forEach(System.out::println);
        System.out.print("Enter id of computer to choose : ");
        long id = in.nextLong();
        return computerRepository.findOne(id);
    }

    @Override
    @Transactional
    public List<Computer> getAll() {
        return computerRepository.findAll();
    }

    @Override
    @Transactional
    public void deleteOne() {
        Computer delete = this.getOne();
        computerRepository.delete(delete.getId());
    }

    @Override
    @Transactional
    public void updateOne() {
        Scanner in = new Scanner(System.in);
        System.out.println("*** update computer ***");
        Computer updated = this.getOne();
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
        System.out.print("Enter new power (to skip enter '!'): ");
        String power = in.nextLine();
        if (!power.equals("!")) {
            updated.setPower(Float.parseFloat(power));
        }
        System.out.print("Enter new operation system (to skip enter '!'): ");
        String os = in.nextLine();
        if (!os.equals("!")) {
            updated.setOs(os);
        }
        em.merge(updated);
    }
}
