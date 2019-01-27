package com.foxminded.university.java.dao;

import java.sql.SQLException;
import java.util.Collection;

import com.foxminded.university.java.domain.Group;

public interface GroupDao {
	
	Collection<Group> findAll() throws SQLException;
    
	Group findById(long id) throws SQLException;
     
     void addGroup(Group group) throws SQLException;
    
     void updateGroup(Group group) throws SQLException;
     
     void removeGroup(Group group) throws SQLException;
}