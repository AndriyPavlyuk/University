package com.foxminded.university.java.domain;

public class Subject {
	private String name;
	private long subjectID;
	private Teacher teacher;

	public Subject() {

	}

	public Subject(String name) {
		this.name = name;
	}

	public Subject(long subjectID) {
		this.subjectID= subjectID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public long getSubjectID() {
		return subjectID;
	}

	public void setSubjectID(long subjectID) {
		this.subjectID = subjectID;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}
}
