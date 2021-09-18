package com.labs.Lab6.entities;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;

@MappedSuperclass
@Table(name = "equipment")
public abstract class Equipment extends Staff{
    @Column(name = "countofdetails")
    protected Integer countOfDetail;

    public Integer getCountOfDetail() {
        return countOfDetail;
    }

    public void setCountOfDetail(Integer countOfDetail) {
        this.countOfDetail = countOfDetail;
    }

    @Override
    public String toString() {
        return  String.format("%s , count of details: %d" , super.toString(),  countOfDetail);
    }
}
