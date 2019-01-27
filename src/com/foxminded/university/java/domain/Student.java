package com.foxminded.university.java.domain;

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
	public List<ScheduleRecord> getScheduleForDay(LocalDate date) {
		List<ScheduleRecord> lessons = new ArrayList<>();
		for (ScheduleRecord lesson : group.getLessons()) {
			if (lesson.getTime().toLocalDate().isEqual(date)) {
				lessons.add(lesson);
			}
		}
		return lessons;
	}

	@Override
	public List<ScheduleRecord> getScheduleForMonthOf(LocalDate date) {
		List<ScheduleRecord> lessons = new ArrayList<>();
		for (ScheduleRecord lesson : group.getLessons()) {
			if ((lesson.getTime().getMonthValue() == date.getMonthValue())
					&& (lesson.getTime().getYear() == date.getYear())) {
				lessons.add(lesson);
			}
		}
		return lessons;
	}
}
