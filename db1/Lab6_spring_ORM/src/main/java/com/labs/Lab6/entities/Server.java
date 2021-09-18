package com.labs.Lab6.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "servers")
public class Server extends Equipment {

    @Column(name = "storage", nullable = false)
    private float storage;

    @Column(name = "countofthreads", nullable = false)
    private int threads;

    @Column(name = "adress" , nullable = false)
    private String adress;

    public float getStorage() {
        return storage;
    }

    public void setStorage(float storage) {
        this.storage = storage;
    }

    public int getThreads() {
        return threads;
    }

    public void setThreads(int threads) {
        this.threads = threads;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    @Override
    public String toString() {
        return String.format("%s , storage : %f, threads: %d, address: %s ", super.toString(), storage, threads, adress);
    }
}
