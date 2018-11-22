package com.edhaorganics.backend.beans;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
public class Customer {
	@Id
	@GeneratedValue
	private Long id;

	@NotBlank
	@NotNull
	@Size(min = 4, message = "CustomerName/Shop name should be atleast 4 charaters")
	private String customerName;

	@OneToOne(cascade = CascadeType.ALL)
	@NotNull
	private Address customerAddress;

	private String gstin;
	@NotBlank
	@NotNull
	@NotEmpty
	private String phoneNumber1;
	private String phoneNumber2;

	@CreationTimestamp
	@Column(updatable = false)
	private LocalDateTime createdOn;

	@UpdateTimestamp
	private LocalDateTime modifiedOn;

	@ManyToOne
	private EdhaUser createdBy;

	@ManyToOne
	private EdhaUser incharge;

	private String emailId;

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public LocalDateTime getCreatedOn() {
		return createdOn;
	}

	public EdhaUser getIncharge() {
		return incharge;
	}

	public void setIncharge(EdhaUser incharge) {
		this.incharge = incharge;
	}

	public Address getCustomerAddress() {
		return customerAddress;
	}

	public void setCustomerAddress(Address customerAddress) {
		this.customerAddress = customerAddress;
	}

	public String getGstin() {
		return gstin;
	}

	public void setGstin(String gstin) {
		this.gstin = gstin;
	}

	public String getPhoneNumber1() {
		return phoneNumber1;
	}

	public void setPhoneNumber1(String phoneNumber1) {
		this.phoneNumber1 = phoneNumber1;
	}

	public String getPhoneNumber2() {
		return phoneNumber2;
	}

	public void setPhoneNumber2(String phonenumber2) {
		this.phoneNumber2 = phonenumber2;
	}

	public EdhaUser getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(EdhaUser createdBy) {
		this.createdBy = createdBy;
	}

	public LocalDateTime getModifiedOn() {
		return modifiedOn;
	}

}
