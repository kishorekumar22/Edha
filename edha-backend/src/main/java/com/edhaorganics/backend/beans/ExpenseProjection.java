package com.edhaorganics.backend.beans;

import java.time.LocalDate;

public interface ExpenseProjection {
	public LocalDate getDate();

	public Long getId();

	public String getDescription();

	public long getAmount();

}
