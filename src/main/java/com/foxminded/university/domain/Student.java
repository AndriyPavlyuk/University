package com.foxminded.university.domain;

import static java.util.Objects.isNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Student extends Person {
	private static final Logger logger = LoggerFactory.getLogger(Student.class);
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
