package com.foxminded.university.dao;

import java.util.Collection;

import com.foxminded.university.domain.Room;

public interface RoomDao {
	
	Collection<Room> findAll() throws DaoException;

	Room findById(long id) throws DaoException;

	void addRoom(Room room) throws DaoException;

	void updateRoom(Room room) throws DaoException;

	void removeRoom(Room room) throws DaoException;
}