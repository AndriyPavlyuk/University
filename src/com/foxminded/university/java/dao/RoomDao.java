package com.foxminded.university.java.dao;

import java.sql.SQLException;
import java.util.Collection;

import com.foxminded.university.java.domain.Room;

public interface RoomDao {
	
	Collection<Room> findAll() throws SQLException;

	Room findById(long id) throws SQLException;

	void addRoom(Room room) throws SQLException;

	void updateRoom(Room room) throws SQLException;

	void removeRoom(Room room) throws SQLException;
}