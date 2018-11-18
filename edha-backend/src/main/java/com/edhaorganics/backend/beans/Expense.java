package com.edhaorganics.backend.beans;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Expense {
	@Id
	@GeneratedValue
	Long id;

	String description;

	long amount;
	LocalDate date;
	
	@ManyToOne
	EdhaUser user;
	

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public long getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}

	public EdhaUser getUser() {
		return user;
	}

	public void setUser(EdhaUser user) {
		this.user = user;
	}

}
