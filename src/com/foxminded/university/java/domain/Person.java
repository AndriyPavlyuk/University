package com.foxminded.university.java.domain;

import java.time.LocalDate;
import java.util.List;

public abstract class Person {
	private String firstName;
	private String lastName;
	private long personID;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public long getPersonID() {
		return personID;
	}

	public void setPersonID(long personID) {
		this.personID = personID;
	}

	public abstract List<ScheduleRecord> getScheduleForDay(LocalDate date);

	public abstract List<ScheduleRecord> getScheduleForMonthOf(LocalDate date);
}
