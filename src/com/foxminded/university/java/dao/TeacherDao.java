package com.foxminded.university.java.dao;

import java.sql.SQLException;
import java.util.Collection;

import com.foxminded.university.java.domain.Teacher;

public interface TeacherDao {

	Collection<Teacher> findAll() throws SQLException;

	Teacher findById(long id) throws SQLException;

	void addTeacher(Teacher teacher) throws SQLException;

	void updateTeacher(Teacher teacher) throws SQLException;

	void removeTeacher(Teacher teacher) throws SQLException;
}