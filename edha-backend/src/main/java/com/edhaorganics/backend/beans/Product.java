package com.edhaorganics.backend.beans;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
public class Product {

	@Id
	@GeneratedValue
	private Long id;
	@NotNull
	@NotEmpty
	@NotBlank
	@Size(min = 4, message = "ProductName should atleast 4 characters")
	private String productName;
	@Min(value = 1, message = "Invalid Product price")
	private float price;

	private String productDescription;

	@CreationTimestamp
	@Column(updatable = false)
	private LocalDateTime createdOn;

	@UpdateTimestamp
	private LocalDateTime modifiedOn;

	@ManyToOne
	private EdhaUser createdBy;

	private float inventoryQty;
	
	private boolean active = true;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductDescription() {
		return productDescription;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public EdhaUser getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(EdhaUser createdBy) {
		this.createdBy = createdBy;
	}

	public LocalDateTime getCreatedOn() {
		return createdOn;
	}

	public LocalDateTime getModifiedOn() {
		return modifiedOn;
	}

	public float getInventoryQty() {
		return inventoryQty;
	}

	public void setInventoryQty(float inventoryQty) {
		this.inventoryQty = inventoryQty;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

}
