package com.foxminded.university.domain;

import static java.util.Objects.isNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Entity
@Table(name = "teachers")
@NamedQuery(name = Teacher.FIND_ALL, query = "FROM Teacher")
public class Teacher extends Person {
	public static final String FIND_ALL = "Room.findAll";
	@Transient
	private Collection<Subject> subjects;
	@Transient
	private List<ScheduleRecord> lessons;
	private static final Logger logger = LoggerFactory.getLogger(Teacher.class);

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

	public void addLesson(ScheduleRecord scheduleRecord) throws DomainException {
		if (isNull(scheduleRecord)) {
			logger.error("Lesson was not founded");
			throw new DomainException("Lesson was not founded");
		}
		if (this.lessons == null) {
			this.lessons = new ArrayList<>();
		}
			this.lessons.add(scheduleRecord);				
	}

	public void addSubject(Subject subject) throws DomainException {
		logger.debug("Subject adding");
		if (isNull(subject)) {
			logger.warn("Subject was not founded");
			throw new DomainException("Subject was not founded");
		}
		if (isNull(subjects)) {
			subjects = new HashSet<>();
			subjects.add(subject);
			subject.setTeacher(this);
		}
		logger.info("Subject was added");
	}

	@Override
	public List<ScheduleRecord> getScheduleForDay(LocalDate date) throws DomainException {
		logger.debug("Getting schedule for day");
		List<ScheduleRecord> lessons = new ArrayList<>();
		if(isNull(date)) {
			logger.error("Schedule for day was not founded");
			throw new DomainException("Schedule for day was not founded");
		}
		for (ScheduleRecord lesson : getLessons()) {
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
			logger.error("Schedule for month was not founded");
			throw new DomainException("Schedule for month was not founded");
		}
		for (ScheduleRecord lesson : getLessons()) {
			if ((lesson.getTime().getMonthValue() == date.getMonthValue())
					&& (lesson.getTime().getYear() == date.getYear())) {
				lessons.add(lesson);
			}	
		}
		logger.info("Schedule for month was got");
		return lessons;
	}
}