package com.foxminded.university.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

import com.foxminded.university.domain.Room;

public class JdbcRoomDao extends AbstractJdbcDao implements RoomDao {

	@Override
	public Collection<Room> findAll() throws DaoException {
		Collection<Room> roomsList = new ArrayList<>();
		String sql = "SELECT ROOM_ID, NUMBER FROM ROOMS";
		Statement statement = null;
		Connection connection = null;
		try {
			connection = getConnection();
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				Room room = new Room();
				room.setRoomID(resultSet.getLong("ROOM_ID"));
				room.setNumber(resultSet.getInt("NUMBER"));
				roomsList.add(room);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException("Can not find rooms",e);
		} finally {
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return roomsList;
	}

	@Override
	public Room findById(long id) throws DaoException {
		Room room = new Room();
		String sql = "SELECT ROOM_ID, NUMBER FROM ROOMS WHERE ROOM_ID=?";
		PreparedStatement preparedStatement = null;
		Connection connection = null;
		try {
			connection = getConnection();
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				room.setRoomID(resultSet.getLong("ROOM_ID"));
				room.setNumber(resultSet.getInt("NUMBER"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException("Can not find room",e);
		} finally {
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return room;
	}

	@Override
	public void addRoom(Room room) throws DaoException {
		PreparedStatement preparedStatement = null;
		String sql = "INSERT INTO ROOMS (ROOM_ID, NUMBER) VALUES(?,?)";
		Connection connection = null;
		try {
			connection = getConnection();
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, room.getRoomID());
			preparedStatement.setInt(2, room.getNumber());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException("Can not add room",e);
		} finally {
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
	}

	@Override
	public void updateRoom(Room room) throws DaoException {
		PreparedStatement preparedStatement = null;
		String sql = "UPDATE ROOMS SET NUMBER=? WHERE ROOM_ID=?";
		Connection connection = null;
		try {
			connection = getConnection();
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, room.getNumber());
			preparedStatement.setLong(2, room.getRoomID());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException("Can not update room",e);
		} finally {
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
	}

	@Override
	public void removeRoom(Room room) throws DaoException {
		PreparedStatement preparedStatement = null;
		String sql = "DELETE FROM ROOMS WHERE ROOM_ID=?";
		Connection connection = null;
		try {
			connection = getConnection();
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, room.getRoomID());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException("Can not remove room",e);
		} finally {
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}	
}
