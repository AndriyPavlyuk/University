package com.foxminded.university.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.foxminded.university.domain.Room;
@Component
public class JdbcRoomDao implements RoomDao {
	private static final Logger logger = LoggerFactory.getLogger(JdbcRoomDao.class);
	
	@Autowired
	private DataSourceConnection dataSourceConnection;
	
	public JdbcRoomDao() {
	}

	@Override
	public Collection<Room> findAll() throws DaoException {
		logger.debug("Searching rooms");
		Collection<Room> roomsList = new ArrayList<>();
		String sql = "SELECT ROOM_ID, NUMBER FROM ROOMS";
		Statement statement = null;
		Connection connection = null;
		try {
			logger.trace("Open connection with database");
			connection = dataSourceConnection.getDataSourceConnection();
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				Room room = new Room();
				room.setRoomID(resultSet.getLong("ROOM_ID"));
				room.setNumber(resultSet.getInt("NUMBER"));
				roomsList.add(room);
			}
			logger.trace("Number of rooms : {}", roomsList.size());
		} catch (SQLException e) {
			logger.warn("Can not find rooms : " + e);
			throw new DaoException("Can not find rooms",e);
		} finally {
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					logger.warn("Can not close statement:", e);
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					logger.warn("Can not close connection:", e);
				}
			}
		}
		logger.info("Rooms were founded");
		return roomsList;
	}

	@Override
	public Room findById(long id) throws DaoException {
		logger.debug("Searching room");
		if (id < 0) {
			logger.warn("Can not find room : " + id);
			throw new DaoException("Can not find room");
		}
		Room room = new Room();
		String sql = "SELECT ROOM_ID, NUMBER FROM ROOMS WHERE ROOM_ID=?";
		PreparedStatement preparedStatement = null;
		Connection connection = null;
		try {
			logger.trace("Open connection with database");
			connection =dataSourceConnection.getDataSourceConnection();
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				room.setRoomID(resultSet.getLong("ROOM_ID"));
				room.setNumber(resultSet.getInt("NUMBER"));
			}
			if (room.getRoomID()==null) {
				logger.warn("Can not find room : " + room.getRoomID());
				throw new DaoException("Can not find room");
			}
		} catch (SQLException e) {
			logger.warn("Can not find room : " + e);
			throw new DaoException("Can not find room",e);
		} finally {
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException e) {
					logger.warn("Can not close prepared statement:", e);
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					logger.warn("Can not close connection:", e);
				}
			}
		}
		logger.info("Room was founded");
		return room;
	}

	@Override
	public void addRoom(Room room) throws DaoException {
		logger.debug("Adding room");
		if (room.getRoomID() < 0) {
			logger.warn("Can not add room : " + room.getRoomID());
			throw new DaoException("Can not add room");
		}
		PreparedStatement preparedStatement = null;
		String sql = "INSERT INTO ROOMS (ROOM_ID, NUMBER) VALUES(?,?)";
		Connection connection = null;
		try {
			logger.trace("Open connection with database");
			connection = dataSourceConnection.getDataSourceConnection();
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, room.getRoomID());
			preparedStatement.setInt(2, room.getNumber());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			logger.warn("Can not add room : " + e);
			throw new DaoException("Can not add room",e);
		} finally {
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException e) {
					logger.warn("Can not close prepared statement:", e);
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					logger.warn("Can not close connection:", e);
				}
			}
		}
		logger.info("Room was added");
	}

	@Override
	public void updateRoom(Room room) throws DaoException {
		logger.debug("Updating room");
		if (room.getRoomID() < 0) {
			logger.warn("Can not update room : " + room.getRoomID());
			throw new DaoException("Can not update room");
		}
		PreparedStatement preparedStatement = null;
		String sql = "UPDATE ROOMS SET NUMBER=? WHERE ROOM_ID=?";
		Connection connection = null;
		try {
			logger.trace("Open connection with database");
			connection = dataSourceConnection.getDataSourceConnection();
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, room.getNumber());
			preparedStatement.setLong(2, room.getRoomID());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			logger.warn("Can not update room : " + e);
			throw new DaoException("Can not update room",e);
		} finally {
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException e) {
					logger.warn("Can not close prepared statement:", e);
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					logger.warn("Can not close connection:", e);
				}
			}
		}
		logger.info("Room was updated");
	}

	@Override
	public void removeRoom(Room room) throws DaoException {
		logger.debug("Removing room");
		if (room.getRoomID() < 0) {
			logger.warn("Can not remove room : " + room.getRoomID());
			throw new DaoException("Can not remove room");
		}
		PreparedStatement preparedStatement = null;
		String sql = "DELETE FROM ROOMS WHERE ROOM_ID=?";
		Connection connection = null;
		try {
			logger.trace("Open connection with database");
			connection = dataSourceConnection.getDataSourceConnection();
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, room.getRoomID());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			logger.error("Can not find room : " + e);
			throw new DaoException("Can not remove room",e);
		} finally {
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException e) {
					logger.warn("Can not close prepared statement:", e);
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					logger.warn("Can not close connection:", e);
				}
			}
		}
		logger.info("Room was removed");
	}	
}
