package com.labs.Lab6.entities;

import javax.persistence.*;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "rooms")
public class Room {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id" , insertable = false, updatable = false)
    private Long id;

    @Column(name="adress", nullable = false, length = 32)
    private String adress;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy="location", targetEntity=Chair.class, fetch=FetchType.LAZY)
    private List<Chair> chairs = new ArrayList<>();

    @OneToMany(mappedBy="location", targetEntity= com.labs.Lab6.entities.Table.class, fetch=FetchType.LAZY)
    private	List<com.labs.Lab6.entities.Table> tables = new ArrayList<>();

    @OneToMany(mappedBy="location", targetEntity= Computer.class, fetch=FetchType.LAZY)
    private	List<Computer> computers = new ArrayList<>();

    @OneToMany(mappedBy="location", targetEntity= Server.class, fetch=FetchType.LAZY)
    private	List<Server> servers = new ArrayList<>();

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return String.format("Room id (%d), %s, description: %s", id, getAdress(), getDescription());
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Chair> getChairs() {
        return chairs;
    }

    public void setChairs(List<Chair> chairs) {
        this.chairs = chairs;
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
