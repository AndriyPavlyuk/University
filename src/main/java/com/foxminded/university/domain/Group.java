package com.foxminded.university.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import static java.util.Objects.isNull;

public class Group {
	private Long groupID;
	private String name;
	private Collection<Student> students;
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
		if (isNull(scheduleRecord)) {
			throw new DomainException("Lesson was not founded");
		}
		if (this.lessons == null) {
			this.lessons = new ArrayList<>();
		}
			this.lessons.add(scheduleRecord);
	}

	public void addStudent(Student student) throws DomainException{
		if (isNull(student)) {
			throw new DomainException("Student was not founded");
		}
		if (isNull(students)) {
			students = new HashSet<>();
			students.add(student);
			student.setGroup(this);
		}
	}
}
