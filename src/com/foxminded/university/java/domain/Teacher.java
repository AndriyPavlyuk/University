package com.foxminded.university.java.domain;

import static java.util.Objects.isNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public class Teacher extends Person {
	private Collection<Subject> subjects;
	private List<ScheduleRecord> lessons;

	public Collection<Subject> getSubjects() {
		return subjects;
	}

	public void setSubjects(List<Subject> subjects) {
		this.subjects = subjects;
	}

	public List<ScheduleRecord> getLessons() {
		return lessons;
	}

	public void setLessons(List<ScheduleRecord> lessons) {
		this.lessons = lessons;
	}

	public void addLesson(ScheduleRecord scheduleRecord) {
		if (this.lessons == null)
			this.lessons = new ArrayList<>();
		this.lessons.add(scheduleRecord);
	}

	public void addSubject(Subject subject) {
		if (isNull(subjects))
			subjects = new HashSet<>();
		subjects.add(subject);
		subject.setTeacher(this);
	}

	@Override
	public List<ScheduleRecord> getScheduleForDay(LocalDate date) {
		List<ScheduleRecord> lessons = new ArrayList<>();
		for (ScheduleRecord lesson : getLessons()) {
			if (lesson.getTime().toLocalDate().isEqual(date)) {
				lessons.add(lesson);
			}
		}
		return lessons;
	}

	@Override
	public List<ScheduleRecord> getScheduleForMonthOf(LocalDate date) {
		List<ScheduleRecord> lessons = new ArrayList<>();
		for (ScheduleRecord lesson : getLessons()) {
			if ((lesson.getTime().getMonthValue() == date.getMonthValue())
					&& (lesson.getTime().getYear() == date.getYear())) {
				lessons.add(lesson);
			}
		}
		return lessons;
	}
}