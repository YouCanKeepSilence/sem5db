package com.labs.Lab6.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "computers")
public class Computer extends Equipment {
    @Column(name = "power", nullable = false)
    private float power;

    @Column(name = "os" , length = 64)
    private String os;

    public float getPower() {
        return power;
    }

    public void setPower(float power) {
        this.power = power;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    @Override
    public String toString() {
        return String.format("%s , power : %f, operation system : %s ", super.toString(), power, os);
    }
}
