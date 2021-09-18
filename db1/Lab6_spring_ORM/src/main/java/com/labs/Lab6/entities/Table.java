package com.labs.Lab6.entities;

import javax.persistence.*;

@Entity
@javax.persistence.Table(name = "tables")
public class Table extends Furniture {

    @Column(name = "area", nullable = false)
    private float area;

    @Column(name = "folding", nullable = false)
    private boolean folding;

    public float getArea() {
        return area;
    }

    public void setArea(float area) {
        this.area = area;
    }

    public boolean isFolding() {
        return folding;
    }

    public void setFolding(boolean folding) {
        this.folding = folding;
    }

    @Override
    public String toString() {
        return String.format("%s , area : %f, is folding : %b ", super.toString(), area, folding);
    }

}
