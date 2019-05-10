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

import com.foxminded.university.domain.Group;

public class JdbcGroupDao extends AbstractJdbcDao implements GroupDao {
	private static final Logger logger = LoggerFactory.getLogger(JdbcGroupDao.class);
	
	@Override
	public Collection<Group> findAll() throws DaoException {
		logger.debug("Searching groups");
		Collection<Group> groupsList = new ArrayList<>();
		String sql = "SELECT GROUP_ID, NAME FROM GROUPS";
		Statement statement = null;
		Connection connection = null;
		try {
			logger.trace("Open connection with database");
			connection = getConnection();
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				Group group = new Group();
				group.setGroupID(resultSet.getLong("GROUP_ID"));
				group.setName(resultSet.getString("NAME"));
				groupsList.add(group);
			}
			logger.trace("Number of groups : {}", groupsList.size());
		} catch (SQLException e) {
			logger.warn("Can not find groups : " + e);
			throw new DaoException("Can not find groups");
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
					logger.warn("Can not close connection:",e);
				}
			}
		}
		logger.info("Groups were founded");
		return groupsList;
	}

	@Override
	public Group findById(long id) throws DaoException {
		logger.debug("Searching group");
		if (id < 0) {
			logger.warn("Can not find group : "+id);
			throw new DaoException("Can not find group");
		}
		Group group = new Group();
		String sql = "SELECT GROUP_ID, NAME FROM GROUPS WHERE GROUP_ID=?";
		PreparedStatement preparedStatement = null;
		Connection connection = null;
		try {
			logger.trace("Open connection with database");
			connection = getConnection();
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				group.setGroupID(resultSet.getLong("GROUP_ID"));
				group.setName(resultSet.getString("NAME"));
			}
			if (group.getGroupID()==null) {
				logger.warn("Can not find group : "+group.getGroupID());
				throw new DaoException("Can not find group");
			}
		} catch (SQLException e) {
			logger.warn("Can not close connection:", e);
		} finally {
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException e) {
					logger.warn("Can not close preparedStatement:", e);
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
		logger.info("Group was founded");
		return group;
	}

	@Override
	public void addGroup(Group group) throws DaoException {
		logger.debug("Adding group");
		if (group.getGroupID() < 0) {
			logger.warn("Can not add group : " + group.getGroupID());
			throw new DaoException("Can not add group");
		}
		PreparedStatement preparedStatement = null;
		Connection connection = null;
		String sql = "INSERT INTO GROUPS (GROUP_ID, NAME) VALUES(?,?)";
		try {
			logger.trace("Open connection with database");
			connection = getConnection();
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, group.getGroupID());
			preparedStatement.setString(2, group.getName());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			logger.warn("Can not add group : "+e);
			throw new DaoException("Can not add group", e);
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
		logger.info("Group was added");
	}

	@Override
	public void updateGroup(Group group) throws DaoException {
		logger.debug("Updating group");
		if (group.getGroupID() < 0) {
			logger.warn("Can not update group : "+group.getGroupID());
			throw new DaoException("Can not update group");
		}
		PreparedStatement preparedStatement = null;
		String sql = "UPDATE GROUPS SET NAME=? WHERE GROUP_ID=?";
		Connection connection = null;
		try {
			logger.trace("Open connection with database");
			connection = getConnection();
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, group.getName());
			preparedStatement.setLong(2, group.getGroupID());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			logger.warn("Can not add group : "+e);
			throw new DaoException("Can not update group", e);
		} finally {
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException e) {
					logger.warn("Can not close preparedStatement:", e);
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
		logger.info("Group was updated");
	}

	@Override
	public void removeGroup(Group group) throws DaoException {
		logger.debug("Removing group");
		if (group.getGroupID() < 0) {
			logger.warn("Can not remove group : "+group.getGroupID());
			throw new DaoException("Can not remove group");
		}
		PreparedStatement preparedStatement = null;
		String sql = "DELETE FROM GROUPS WHERE GROUP_ID=?";
		Connection connection = null;
		try {
			logger.trace("Open connection with database");
			connection = getConnection();
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, group.getGroupID());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			logger.warn("Can not remove group : "+e);
			throw new DaoException("Can not remove group", e);
		} finally {
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException e) {
					logger.warn("Can not close preparedStatement:", e);
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
		logger.info("Group was removed");
	}
}

