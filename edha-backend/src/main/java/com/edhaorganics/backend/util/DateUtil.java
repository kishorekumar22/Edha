package com.edhaorganics.backend.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {

	static String DATE_FORMAT = "dd/MM/yyyy";

	public static LocalDateTime getStartDate(String date) {
		String dateString = date;
		if(date.contains("-")){
			dateString = date.substring(0,date.indexOf('-'));
		}
		LocalDate temp = LocalDate.parse(dateString, DateTimeFormatter.ofPattern(DATE_FORMAT));
		return LocalDateTime.of(temp, LocalTime.of(0, 0));
	}

	public static LocalDateTime getToDate(String date) {
		String dateString = date;
		if(date.contains("-")){
			dateString = date.substring(date.indexOf('-') + 1);
		}
		LocalDate temp = LocalDate.parse(dateString, DateTimeFormatter.ofPattern(DATE_FORMAT));
		return LocalDateTime.of(temp.plusDays(1), LocalTime.of(0, 0));
		
	}

}
