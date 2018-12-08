package com.edhaorganics.backend.beans;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class ExpenseType {

	@Id
	@GeneratedValue
	private long id;

	private String name;

	private int maxValue;

	private boolean descRequired;

	public boolean isDescRequired() {
		return descRequired;
	}

	public void setDescRequired(boolean descRequired) {
		this.descRequired = descRequired;
	}

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

	public int getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(int maxValue) {
		this.maxValue = maxValue;
	}
}
