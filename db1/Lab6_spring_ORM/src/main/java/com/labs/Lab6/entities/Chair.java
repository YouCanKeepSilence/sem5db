package com.labs.Lab6.entities;

import javax.persistence.*;
import javax.persistence.Table;

@Entity
@Table(name = "chair")
public class Chair extends Furniture {
	
	@Column(name = "armrest")
	private boolean armrest;
	
	@Column(name = "heightofback")
	private float height_of_back;
	
	public boolean isHasArmrest() {
		return armrest;
	}
	public void setArmrest(boolean armrest) {
		this.armrest = armrest;
	}
	public float getHeightOfBack() {
		return height_of_back;
	}
	public void setHeightOfBack(float height_of_back) {
		this.height_of_back = height_of_back;
	}
	
	@Override
	public String toString() {
		return String.format("%s , height of back : %f , have armrest : %b", super.toString() , height_of_back, armrest);
	}
}
