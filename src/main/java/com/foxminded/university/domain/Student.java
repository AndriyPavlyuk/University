package com.foxminded.university.domain;

import static java.util.Objects.isNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Entity
@Table(name = "students")
@NamedQuery(name = Student.FIND_ALL, query = "FROM Student")
public class Student extends Person {
	private static final Logger logger = LoggerFactory.getLogger(Student.class);
	public static final String FIND_ALL = "Student.findAll";
	@ManyToOne
    @JoinColumn(name = "groupid")
	private Group group;

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public Student() {
	}
	
	public Student(Long personID, Group group, String firstName, String lastName) {
		super(personID, firstName, lastName);
		this.group = group;
	}

	@Override
	public List<ScheduleRecord> getScheduleForDay(LocalDate date) throws DomainException {
		logger.debug("Getting schedule for day");
		List<ScheduleRecord> lessons = new ArrayList<>();
		if(isNull(date)) {
			logger.warn("Schedule for day was not founded");
			throw new DomainException("Schedule for day was not founded");
		}
		for (ScheduleRecord lesson : group.getLessons()) {
			if (lesson.getTime().toLocalDate().isEqual(date)) {
				lessons.add(lesson);
			}
		}
		logger.info("Schedule for day was got");
		return lessons;
	}

	@Override
	public List<ScheduleRecord> getScheduleForMonthOf(LocalDate date) throws DomainException {
		logger.debug("Getting schedule for month");
		List<ScheduleRecord> lessons = new ArrayList<>();
		if(isNull(date)) {
			logger.warn("Schedule for month was not founded");
			throw new DomainException("Schedule for month was not founded");
		}
		for (ScheduleRecord lesson : group.getLessons()) {
			if ((lesson.getTime().getMonthValue() == date.getMonthValue())
					&& (lesson.getTime().getYear() == date.getYear())) {
				lessons.add(lesson);
			}
		}
		logger.info("Schedule for month was got");
		return lessons;
	}
}
