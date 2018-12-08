package com.edhaorganics.backend.beans;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class DiscountType {

	@Id
	@GeneratedValue
	private long id;

	private String name;

	private int value;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String type) {
		this.name = type;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int maxValue) {
		this.value = maxValue;
	}
}
