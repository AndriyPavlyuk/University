package com.foxminded.university.domain;

import java.time.LocalDate;
import java.util.List;

public abstract class Person {
	private String firstName;
	private String lastName;
	private Long personID;

	public Person() {
		
	}
	public Person(Long personID, String firstName, String lastName) {
		this.personID=personID;
		this.firstName=firstName;
		this.lastName=lastName;
	}

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

	public Long getPersonID() {
		return personID;
	}

	public void setPersonID(Long personID) {
		this.personID = personID;
	}

	public abstract List<ScheduleRecord> getScheduleForDay(LocalDate date) throws DomainException;

	public abstract List<ScheduleRecord> getScheduleForMonthOf(LocalDate date) throws DomainException;
}
