package com.foxminded.university.java.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

import com.foxminded.university.java.domain.Group;

public class JdbcGroupDao extends AbstractJdbcDao implements GroupDao {

	@Override
	public Collection<Group> findAll() throws SQLException {
		Collection<Group> groupsList = new ArrayList<>();
		String sql = "SELECT GROUP_ID, NAME FROM GROUPS";
		Statement statement = null;
		Connection connection = null;
		try {
			connection = getConnection();
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				Group group = new Group();
				group.setGroupID(resultSet.getLong("GROUP_ID"));
				group.setName(resultSet.getString("NAME"));
				groupsList.add(group);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (statement != null) {
				statement.close();
			}
			if (connection != null) {
				connection.close();
			}
		}
		return groupsList;
	}

	@Override
	public Group findById(long id) throws SQLException {
		Group group = new Group();
		String sql = "SELECT GROUP_ID, NAME FROM GROUPS WHERE GROUP_ID=?";
		PreparedStatement preparedStatement = null;
		Connection connection = null;
		try {
			connection = getConnection();
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				group.setGroupID(resultSet.getLong("GROUP_ID"));
				group.setName(resultSet.getString("NAME"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (preparedStatement != null) {
				preparedStatement.close();
			}
			if (connection != null) {
				connection.close();
			}
		}
		return group;
	}

	@Override
	public void addGroup(Group group) throws SQLException {
		PreparedStatement preparedStatement = null;
		String sql = "INSERT INTO GROUPS (GROUP_ID, NAME) VALUES(?,?)";
		Connection connection = null;
		try {
			connection = getConnection();
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, group.getGroupID());
			preparedStatement.setString(2, group.getName());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (preparedStatement != null) {
				preparedStatement.close();
			}
			if (connection != null) {
				connection.close();
			}
		}
		
	}

	@Override
	public void updateGroup(Group group) throws SQLException {
		PreparedStatement preparedStatement = null;
		String sql = "UPDATE GROUPS SET NAME=? WHERE GROUP_ID=?";
		Connection connection = null;
		try {
			connection = getConnection();
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, group.getName());
			preparedStatement.setLong(2, group.getGroupID());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (preparedStatement != null) {
				preparedStatement.close();
			}
			if (connection != null) {
				connection.close();
			}
		}
		
	}

	@Override
	public void removeGroup(Group group) throws SQLException {
		PreparedStatement preparedStatement = null;
		String sql = "DELETE FROM GROUPS WHERE GROUP_ID=?";
		Connection connection = null;
		try {
			connection = getConnection();
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, group.getGroupID());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (preparedStatement != null) {
				preparedStatement.close();
			}
			if (connection != null) {
				connection.close();
			}
		}
	}	
}
