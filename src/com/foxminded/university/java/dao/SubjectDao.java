package com.foxminded.university.java.dao;

import java.sql.SQLException;
import java.util.Collection;

import com.foxminded.university.java.domain.Subject;

public interface SubjectDao {
	
	Collection<Subject> findAll() throws SQLException;

	Subject findById(long id) throws SQLException;

	void addSubject(Subject subject) throws SQLException;

	void updateSubject(Subject subject) throws SQLException;

	void removeSubject(Subject subject) throws SQLException;
}