package com.foxminded.university.domain;

import static java.util.Objects.isNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Student extends Person {
	
	private Group group;

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	@Override
	public List<ScheduleRecord> getScheduleForDay(LocalDate date) throws DomainException {
		List<ScheduleRecord> lessons = new ArrayList<>();
		if(isNull(date)) {
			throw new DomainException("Schedule for day was not founded");
		}
		for (ScheduleRecord lesson : group.getLessons()) {
			if (lesson.getTime().toLocalDate().isEqual(date)) {
				lessons.add(lesson);
			}
		}
		return lessons;
	}

	@Override
	public List<ScheduleRecord> getScheduleForMonthOf(LocalDate date) throws DomainException {
		List<ScheduleRecord> lessons = new ArrayList<>();
		if(isNull(date)) {
			throw new DomainException("Schedule for month was not founded");
		}
		for (ScheduleRecord lesson : group.getLessons()) {
			if ((lesson.getTime().getMonthValue() == date.getMonthValue())
					&& (lesson.getTime().getYear() == date.getYear())) {
				lessons.add(lesson);
			}
		}
		return lessons;
	}
}
