package com.foxminded.university.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.util.Objects.isNull;

@Entity
@Table(name = "groups")
@NamedQuery(name = Group.FIND_ALL, query = "FROM Group")
public class Group {
	public static final String FIND_ALL = "Group.findAll";
	private static final Logger logger = LoggerFactory.getLogger(Group.class);
	@Id
	private Long groupID;
	@Column
	private String name;
	@Transient
	private Collection<Student> students;
	@Transient
	private List<ScheduleRecord> lessons;
	
	public Group() {
	}
	
	public Group(Long groupID) {
		this.groupID = groupID;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Long getGroupID() {
		return groupID;
	}

	public void setGroupID(Long groupID) {
		this.groupID = groupID;
	}

	public Collection<Student> getStudents() {
		return students;
	}

	public void setStudents(List<Student> students) {
		this.students = students;
	}

	public List<ScheduleRecord> getLessons() {
		return lessons;
	}

	public void setLessons(List<ScheduleRecord> lessons) {
		this.lessons = lessons;
	}

	public void addLesson(ScheduleRecord scheduleRecord) throws DomainException{
		logger.debug("Lesson adding");
		if (isNull(scheduleRecord)) {
			logger.warn("Lesson was not founded");
			throw new DomainException("Lesson was not founded");
		}
		if (this.lessons == null) {
			this.lessons = new ArrayList<>();
		}
			this.lessons.add(scheduleRecord);
			logger.info("Lesson was added");
	}

	public void addStudent(Student student) throws DomainException{
		logger.debug("Student adding");
		if (isNull(student)) {
			logger.warn("Student was not founded");
			throw new DomainException("Student was not founded");
		}
		if (isNull(students)) {
			students = new HashSet<>();
			students.add(student);
			student.setGroup(this);
		}
		logger.debug("Student was added");
	}
}
