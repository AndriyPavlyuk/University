package com.foxminded.university.java.dao;

import java.sql.SQLException;
import java.util.Collection;


import com.foxminded.university.java.domain.Student;

public interface StudentDao {
	
	Collection<Student> findAll() throws SQLException;
     
	Student findById(long id) throws SQLException;
     
     void addStudent(Student student) throws SQLException;
    
     void updateStudent(Student student) throws SQLException;
     
     void removeStudent(Student student) throws SQLException;
}