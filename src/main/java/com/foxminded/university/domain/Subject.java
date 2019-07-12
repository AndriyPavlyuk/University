package com.foxminded.university.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "subjects")
@NamedQuery(name = Subject.FIND_ALL, query = "FROM Subject")
public class Subject {
	public static final String FIND_ALL = "Subject.findAll";
	@Id
	private Long subjectID;
	@Column
	private String name;
	@Transient
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
