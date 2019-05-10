package com.foxminded.university.domain;

public class Subject {
	private String name;
	private Long subjectID;
	private Teacher teacher;

	public Subject() {

	}

	public Subject(String name) {
		this.name = name;
	}

	public Subject(Long subjectID) {
		this.subjectID= subjectID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Long getSubjectID() {
		return subjectID;
	}

	public void setSubjectID(Long subjectID) {
		this.subjectID = subjectID;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}
}
