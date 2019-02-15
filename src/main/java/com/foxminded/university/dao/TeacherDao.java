package com.foxminded.university.dao;

import java.util.Collection;

import com.foxminded.university.domain.Teacher;

public interface TeacherDao {

	Collection<Teacher> findAll() throws DaoException;

	Teacher findById(long id) throws DaoException;

	void addTeacher(Teacher teacher) throws DaoException;

	void updateTeacher(Teacher teacher) throws DaoException;

	void removeTeacher(Teacher teacher) throws DaoException;
}