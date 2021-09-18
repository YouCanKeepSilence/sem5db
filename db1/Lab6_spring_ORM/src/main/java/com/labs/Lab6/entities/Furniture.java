package com.labs.Lab6.entities;

import javax.persistence.*;
import javax.persistence.Table;

@MappedSuperclass
@Table(name = "furniture")
public abstract class Furniture extends Staff {
	@Column(name = "material" , nullable = false, length = 64)
	protected String material;
	
	@Column(name = "weight" , nullable = false)
	protected float weight;

	public float getWeight() {
		return weight;
	}

	public void setWeight(float weight) {
		this.weight = weight;
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}
	
	@Override
	public String toString() {
		return  String.format("%s , material: %s, weight: %f" , super.toString(), material, weight);
	}
}
