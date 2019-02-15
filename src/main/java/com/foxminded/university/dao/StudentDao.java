package com.foxminded.university.dao;

import java.util.Collection;


import com.foxminded.university.domain.Student;

public interface StudentDao {
	
	Collection<Student> findAll() throws DaoException;
     
	Student findById(long id) throws DaoException;
     
     void addStudent(Student student) throws DaoException;
    
     void updateStudent(Student student) throws DaoException;
     
     void removeStudent(Student student) throws DaoException;
}