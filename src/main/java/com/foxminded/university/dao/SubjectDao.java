package com.foxminded.university.dao;

import java.util.Collection;

import com.foxminded.university.domain.Subject;

public interface SubjectDao {
	
	Collection<Subject> findAll() throws DaoException;

	Subject findById(long id) throws DaoException;

	void addSubject(Subject subject) throws DaoException;

	void updateSubject(Subject subject) throws DaoException;

	void removeSubject(Subject subject) throws DaoException;
}