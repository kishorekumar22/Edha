package com.edhaorganics.backend.beans;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "EDHA_USER")
public class EdhaUser {

	@Id
	@NotNull
	@NotEmpty
	@NotBlank
	private String username;
	 @JsonIgnore
	private String password;
	@NotNull
	@NotEmpty
	@NotBlank
	@Size(min = 10, max = 10, message = "Phone number should be 10 digits")
	@Column(unique = true)
	private String phoneNumber;
	private String fullName;
	@Column(unique = true)
	private String emailId;
	@Enumerated(EnumType.STRING)
	private UserRole role;

	@CreationTimestamp
	@Column(updatable = false)
	private LocalDateTime createdOn;

	@UpdateTimestamp
	private LocalDateTime modifiedOn;
	
	@ManyToOne
	private EdhaUser createdBy;
	
	

	public EdhaUser getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(EdhaUser createdBy) {
		this.createdBy = createdBy;
	}

	private boolean active;
	
	@OneToOne(cascade = CascadeType.ALL)
	@NotNull
	private Address userAddress;


	public LocalDateTime getCreatedOn() {
		return createdOn;
	}

	public LocalDateTime getModifiedOn() {
		return modifiedOn;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}

	public Address getUserAddress() {
		return userAddress;
	}

	public void setUserAddress(Address userAddress) {
		this.userAddress = userAddress;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

}
