package com.foxminded.university.dao;

import java.util.Collection;

import com.foxminded.university.domain.Group;

public interface GroupDao {
	
	Collection<Group> findAll() throws DaoException;
    
	Group findById(long id) throws DaoException;
     
     void addGroup(Group group) throws DaoException;
    
     void updateGroup(Group group) throws DaoException;
     
     void removeGroup(Group group) throws DaoException;
}